package dev.jamal.projetotcc.Mapper;

import dev.jamal.projetotcc.DTO.RecommendationProfile.RecommendationProfileCreateRequestDTO;
import dev.jamal.projetotcc.DTO.RecommendationProfile.RecommendationProfileResponseDTO;
import dev.jamal.projetotcc.Entities.RecommendationProfile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationProfileMapper {

    public RecommendationProfile toEntity(
            RecommendationProfileCreateRequestDTO dto
    ) {
        RecommendationProfile profile = new RecommendationProfile();

        profile.setTempoDisponivelSemanal(
                dto.getTempoDisponivelSemanal()
        );

        profile.setOrcamentoInicial(
                dto.getOrcamentoInicial()
        );

        profile.setNivelSocial(
                dto.getNivelSocial()
        );

        profile.setNivelExperiencia(
                dto.getNivelExperiencia()
        );

        profile.setNivelAtividadeFisicaDesejada(
                dto.getNivelAtividadeFisicaDesejada()
        );

        profile.setAmbientePreferido(
                dto.getAmbientePreferido()
        );

        profile.setFormatoPreferido(
                dto.getFormatoPreferido()
        );

        return profile;
    }

    public void updateEntity(
            RecommendationProfile profile,
            RecommendationProfileCreateRequestDTO dto
    ) {
        profile.setTempoDisponivelSemanal(
                dto.getTempoDisponivelSemanal()
        );

        profile.setOrcamentoInicial(
                dto.getOrcamentoInicial()
        );

        profile.setNivelSocial(
                dto.getNivelSocial()
        );

        profile.setNivelExperiencia(
                dto.getNivelExperiencia()
        );

        profile.setNivelAtividadeFisicaDesejada(
                dto.getNivelAtividadeFisicaDesejada()
        );

        profile.setAmbientePreferido(
                dto.getAmbientePreferido()
        );

        profile.setFormatoPreferido(
                dto.getFormatoPreferido()
        );
    }

    public RecommendationProfileResponseDTO toResponseDTO(
            RecommendationProfile profile,
            List<Long> interestIds,
            List<Long> objectiveIds
    ) {
        return new RecommendationProfileResponseDTO(
                profile.getId(),
                profile.getUser().getId(),
                profile.getTempoDisponivelSemanal(),
                profile.getOrcamentoInicial(),
                profile.getNivelSocial(),
                profile.getNivelExperiencia(),
                profile.getNivelAtividadeFisicaDesejada(),
                profile.getAmbientePreferido(),
                profile.getFormatoPreferido(),
                profile.getQuestionarioConcluido(),
                interestIds,
                objectiveIds
        );
    }
}