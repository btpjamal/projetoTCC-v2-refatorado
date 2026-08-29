package dev.jamal.projetotcc.Service;
import dev.jamal.projetotcc.Enum.TipoSocializacao;

import dev.jamal.projetotcc.Entities.Hobby;
import dev.jamal.projetotcc.Entities.User;
import dev.jamal.projetotcc.Service.AI.context.InitialPlanContext;
import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {

    public String buildInitialPlanPrompt(InitialPlanContext context) {

        var user = context.user();
        var profile = context.profile();
        var hobby = context.hobby();

        return """
                Você é um especialista em orientar pessoas que desejam começar novos hobbies.

                Crie um plano inicial personalizado de 7 dias.

                Dados do usuário:
                - Nome: %s
                - Tempo disponível: %.1f horas
                - Orçamento disponível: R$ %.2f
                - Perfil de socialização: %s

                Hobby recomendado:
                - Nome: %s
                - Descrição: %s
                - Custo estimado: R$ %.2f
                - Tempo necessário: %.1f horas
                - Nível de dificuldade: %s
                - Tipo de socialização: %s

                Regras para o plano:
                - Divida o plano em sete dias.
                - Respeite o orçamento informado.
                - Considere o tempo disponível do usuário.
                - Pressuponha que o usuário é iniciante.
                - Sugira materiais gratuitos ou baratos quando possível.
                - Apresente atividades práticas e objetivas.
                - Não invente informações pessoais.
                - Use linguagem simples e motivadora.
                """
                .formatted(
                        user.getNome(),
                        profile.getTempoDisponivel(),
                        profile.getOrcamento(),
                        profile.getTipoSocializacao(),
                        hobby.getNome(),
                        hobby.getDescricao(),
                        hobby.getCustoEstimado(),
                        hobby.getTempoNecessario(),
                        hobby.getNivelDificuldade(),
                        hobby.getTipoSocializacao()
                );
    }
}
