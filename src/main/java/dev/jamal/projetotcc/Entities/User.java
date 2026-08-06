package dev.jamal.projetotcc.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    // Column -> serve para configurar a coluna
    // unique = true -> cada valor é unico, não pode repetir
    // nullable = false -> obrigatório, não deve ser um valor nulo
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    private LocalDate dataCadastro;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @PrePersist
    public void prePersist(){
        this.dataCadastro = LocalDate.now();
    }


    // relacionamento de um para um
    // um usuário tem um perfil
    // lado com "mappedBy" é o lado inverso da relação
    // cascade = CascadeType.ALL -> quando salvar o pai, salva o filho junto
    // fetch = FetchType.LAZY -> evita carregar tudo sempre
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // para evitar loop de serialização
    private RecommendationProfile profile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
