package dev.jamal.projetotcc.DTO.Profile;
import dev.jamal.projetotcc.Enum.TipoSocializacao;
import dev.jamal.projetotcc.DTO.User.UserSummaryDTO;
import dev.jamal.projetotcc.Enum.*;
import lombok.*;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserProfileResponseDTO {
 private Long id; private Double tempoDisponivelSemanal; private Double orcamentoInicial;
 private TipoSocializacao tipoSocializacao; private NivelAtividadeFisica nivelAtividadeFisicaDesejada;
 private AmbientePreferido ambientePreferido;
 private boolean questionarioConcluido; private UserSummaryDTO user;
}
