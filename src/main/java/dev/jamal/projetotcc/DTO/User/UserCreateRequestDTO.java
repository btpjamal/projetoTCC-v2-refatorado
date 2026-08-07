package dev.jamal.projetotcc.DTO.User;
import jakarta.validation.constraints.*; import lombok.Getter; import lombok.Setter; import java.time.LocalDate;
@Getter @Setter public class UserCreateRequestDTO {
 @NotBlank(message="O nome é obrigatório") private String nome;
 @NotBlank(message="O email é obrigatório") @Email(message="Email inválido") private String email;
 @NotBlank(message="A senha é obrigatória")  private String senha;
 @NotNull(message="A data de nascimento é obrigatória") @Past(message="A data de nascimento deve estar no passado") private LocalDate dataNascimento;
}
