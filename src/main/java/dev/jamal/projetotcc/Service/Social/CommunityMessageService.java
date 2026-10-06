package dev.jamal.projetotcc.Service.Social;

import dev.jamal.projetotcc.DTO.Social.CommunityMessageDTO;
import dev.jamal.projetotcc.DTO.Social.CreateCommunityMessageDTO;
import dev.jamal.projetotcc.Entities.Community;
import dev.jamal.projetotcc.Entities.CommunityMessage;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Repository.CommunityMemberRepository;
import dev.jamal.projetotcc.Repository.CommunityMessageRepository;
import dev.jamal.projetotcc.Repository.CommunityRepository;
import dev.jamal.projetotcc.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityMessageService {

    private static final int TAMANHO_MAXIMO = 1000;

    private final CommunityMessageRepository messageRepository;
    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository memberRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<CommunityMessageDTO> listar(
            Long communityId,
            Long userId
    ) {

        validarMembro(
                communityId,
                userId
        );

        return messageRepository
                .findByCommunity_IdOrderByDataEnvioAsc(
                        communityId
                )
                .stream()
                .map(message ->
                        converterParaDTO(
                                message,
                                userId
                        )
                )
                .toList();
    }


    @Transactional
    public CommunityMessageDTO enviar(
            Long communityId,
            Long userId,
            CreateCommunityMessageDTO request
    ) {

        validarMembro(
                communityId,
                userId
        );

        String conteudo =
                validarConteudo(request);


        Community community =
                communityRepository
                        .findById(communityId)
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Comunidade não encontrada."
                                )
                        );


        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Usuário não encontrado."
                                )
                        );


        CommunityMessage message =
                new CommunityMessage();

        message.setCommunity(community);
        message.setUser(user);
        message.setConteudo(conteudo);


        CommunityMessage salva =
                messageRepository.save(message);


        return converterParaDTO(
                salva,
                userId
        );
    }


    private void validarMembro(
            Long communityId,
            Long userId
    ) {

        boolean participa =
                memberRepository
                        .existsByCommunity_IdAndUser_Id(
                                communityId,
                                userId
                        );

        if (!participa) {
            throw new IllegalStateException(
                    "Você precisa participar da comunidade para acessar as mensagens."
            );
        }
    }


    private String validarConteudo(
            CreateCommunityMessageDTO request
    ) {

        if (
                request == null ||
                        request.conteudo() == null
        ) {
            throw new IllegalArgumentException(
                    "A mensagem não pode estar vazia."
            );
        }


        String conteudo =
                request.conteudo().trim();


        if (conteudo.isBlank()) {
            throw new IllegalArgumentException(
                    "A mensagem não pode estar vazia."
            );
        }


        if (conteudo.length() > TAMANHO_MAXIMO) {
            throw new IllegalArgumentException(
                    "A mensagem deve possuir no máximo 1000 caracteres."
            );
        }


        return conteudo;
    }


    private CommunityMessageDTO converterParaDTO(
            CommunityMessage message,
            Long userId
    ) {

        return new CommunityMessageDTO(
                message.getId(),
                message.getUser().getId(),
                message.getUser().getNome(),
                message.getConteudo(),
                message.getDataEnvio(),
                message.getUser().getId().equals(userId)
        );
    }
}