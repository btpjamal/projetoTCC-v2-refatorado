package dev.jamal.projetotcc.Service;

import dev.jamal.projetotcc.DTO.RecommendationProfile.RecommendationProfileCreateRequestDTO;
import dev.jamal.projetotcc.DTO.RecommendationProfile.RecommendationProfileResponseDTO;
import dev.jamal.projetotcc.Entities.RecommendationProfile;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Exception.BusinessException;
import dev.jamal.projetotcc.Mapper.RecommendationProfileMapper;
import dev.jamal.projetotcc.Repository.RecommendationProfileRepository;
import dev.jamal.projetotcc.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendationProfileService {

    private final RecommendationProfileRepository
            recommendationProfileRepository;

    private final UserRepository userRepository;

    private final RecommendationProfileMapper
            recommendationProfileMapper;

    @Transactional
    public RecommendationProfileResponseDTO salvarQuestionario(
            Long userId,
            RecommendationProfileCreateRequestDTO dto
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado.")
                );

        RecommendationProfile profile =
                recommendationProfileRepository
                        .findByUserId(userId)
                        .orElseGet(() -> {
                            RecommendationProfile novo =
                                    recommendationProfileMapper
                                            .toEntity(dto);

                            novo.setUser(user);

                            return novo;
                        });

        if (profile.getId() != null) {
            recommendationProfileMapper.updateEntity(profile, dto);
        }

        profile.setQuestionarioConcluido(true);

        RecommendationProfile salvo =
                recommendationProfileRepository.save(profile);

        return recommendationProfileMapper.toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public RecommendationProfileResponseDTO buscarPorUsuario(
            Long userId
    ) {
        RecommendationProfile profile =
                recommendationProfileRepository
                        .findByUserId(userId)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Perfil de recomendação não encontrado."
                                )
                        );

        return recommendationProfileMapper.toResponseDTO(profile);
    }

    @Transactional(readOnly = true)
    public boolean questionarioConcluido(Long userId) {
        return recommendationProfileRepository
                .findByUserId(userId)
                .map(RecommendationProfile::getQuestionarioConcluido)
                .orElse(false);
    }
}