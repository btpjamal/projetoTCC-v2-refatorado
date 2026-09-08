package dev.jamal.projetotcc.Service;

import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        System.out.println(">>> REQUEST: " + request.getMethod() + " " + request.getRequestURI());

        System.out.println(">>> AUTH HEADER PRESENTE: " + (authHeader != null));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(
                    ">>> ANTES FILTER CHAIN: "
                            + request.getMethod()
                            + " "
                            + request.getRequestURI()
                            + " | STATUS: "
                            + response.getStatus()
                            + " | COMMITTED: "
                            + response.isCommitted()
            );
            filterChain.doFilter(request, response);
            System.out.println(
                    ">>> DEPOIS FILTER CHAIN: "
                            + request.getMethod()
                            + " "
                            + request.getRequestURI()
                            + " | STATUS: "
                            + response.getStatus()
                            + " | COMMITTED: "
                            + response.isCommitted()
            );
            return;
        }

        String token = authHeader.substring(7);

        try {

            String email = jwtService.extrairEmail(token);

            System.out.println(">>> EMAIL EXTRAÍDO: " + email);

            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = userRepository
                        .findByEmail(email)
                        .orElse(null);

                System.out.println(">>> USUÁRIO ENCONTRADO: " + (user != null));

                if (user != null) {

                    boolean tokenValido =
                            jwtService.tokenValido(token, user);

                    System.out.println(">>> TOKEN VÁLIDO: " + tokenValido);

                    if (tokenValido) {

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        user.getAuthorities()
                                );

                        authToken.setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(request)
                        );

                        SecurityContextHolder.getContext()
                                .setAuthentication(authToken);

                        System.out.println(
                                ">>> AUTENTICAÇÃO DEFINIDA: "
                                        + SecurityContextHolder
                                        .getContext()
                                        .getAuthentication()
                                        .isAuthenticated()
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(">>> ERRO NO JWT FILTER:");
            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}



