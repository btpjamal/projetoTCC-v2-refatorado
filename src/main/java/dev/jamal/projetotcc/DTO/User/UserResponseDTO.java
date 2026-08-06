package dev.jamal.projetotcc.DTO.User;
import lombok.*; import java.time.LocalDate;
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataCadastro;
    private LocalDate dataNascimento;

    public UserResponseDTO(Long id, String nome, String email, LocalDate dataCadastro, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.dataNascimento = dataNascimento;
    }
}
