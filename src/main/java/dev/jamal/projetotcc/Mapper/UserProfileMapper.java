package dev.jamal.projetotcc.Mapper;
import dev.jamal.projetotcc.DTO.Profile.*; import dev.jamal.projetotcc.Entities.*;
import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Component;
@Component @RequiredArgsConstructor
public class UserProfileMapper {
 private final UserMapper userMapper;
 public UserProfileResponseDTO toResponseDTO(RecommendationProfile p){ return new UserProfileResponseDTO(p.getId(),p.getTempoDisponivelSemanal(),p.getOrcamentoInicial(),p.getTipoSocializacao(),p.getNivelAtividadeFisicaDesejada(),p.getAmbientePreferido(),p.getQuestionarioConcluido(),userMapper.toSummaryDTO(p.getUser())); }
}
