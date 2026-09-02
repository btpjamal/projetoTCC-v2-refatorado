package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {

    public String construirPromptPlanoInicial(AIUserContext context) {

        return """
                Você é um assistente especializado em ajudar pessoas a iniciar e desenvolver hobbies.

                Sua tarefa é criar um plano inicial personalizado para o hobby informado.

                REGRAS:
                - Considere somente as informações fornecidas no contexto.
                - Respeite o orçamento inicial do usuário.
                - Respeite o tempo semanal disponível.
                - O tempo disponível semanal representa um limite de disponibilidade, não uma quantidade que precisa ser totalmente utilizada.
                - Não preencha artificialmente o tempo restante com outras atividades.
                - Use apenas o tempo necessário para um plano realista do hobby.
                - Adapte o plano ao nível atual do usuário nesse hobby.
                - Considere os objetivos e interesses do usuário.
                - Considere as preferências de ambiente, socialização e atividade física.
                - Considere os alertas de compatibilidade apresentados pelo sistema.
                - Não trate o score como porcentagem. Ele é apenas um valor interno de ranking.
                - Não invente estabelecimentos, eventos, preços atuais ou locais específicos.
                - Quando a localização for relevante, utilize apenas cidade e estado como contexto geral.
                - Evite compras ou equipamentos desnecessários para um iniciante.
                - O plano deve ser prático, realista e possível de iniciar.
                - Quando houver incompatibilidades entre o perfil do usuário e o hobby, adapte o plano quando isso for razoável, mas não descaracterize o hobby.

                USUÁRIO:
                Idade: %s
                Cidade: %s
                Estado: %s

                PERFIL:
                Tempo disponível por semana: %s horas
                Orçamento inicial: R$ %s
                Preferência de socialização: %s
                Atividade física desejada: %s
                Ambiente preferido: %s
                Interesses: %s
                Objetivos: %s

                HOBBY:
                Nome: %s
                Descrição: %s
                Categoria: %s
                Custo estimado: R$ %s
                Tempo estimado: %s horas
                Nível de dificuldade: %s
                Tipo de socialização: %s
                Nível de atividade física: %s
                Ambiente: %s

                COMPATIBILIDADE CALCULADA PELO SISTEMA:
                Score interno: %s
                Motivos: %s
                Alertas: %s

                RELAÇÃO ATUAL DO USUÁRIO COM O HOBBY:
                Nível atual: %s
                Status atual: %s

                Crie um plano inicial contendo:
                1. Uma breve introdução personalizada.
                2. Uma meta inicial realista.
                3. Um plano para as primeiras 4 semanas.
                4. Uma sugestão de distribuição do tempo semanal.
                5. Materiais ou recursos necessários para começar.
                6. Uma estimativa de custo inicial respeitando o orçamento informado.
                7. Dicas relacionadas aos objetivos do usuário.
                8. Cuidados ou dificuldades relevantes.
                9. Um próximo passo concreto que o usuário possa executar imediatamente.

                Não explique o processo de raciocínio utilizado para criar o plano.
                Retorne apenas o plano personalizado.
                """
                .formatted(
                        context.usuario().idade(),
                        context.usuario().cidade(),
                        context.usuario().estado(),

                        context.perfil().tempoDisponivelSemanal(),
                        context.perfil().orcamentoInicial(),
                        context.perfil().tipoSocializacao(),
                        context.perfil().nivelAtividadeFisicaDesejada(),
                        context.perfil().ambientePreferido(),
                        context.perfil().interesses(),
                        context.perfil().objetivos(),

                        context.hobby().nome(),
                        context.hobby().descricao(),
                        context.hobby().categoria(),
                        context.hobby().custoEstimado(),
                        context.hobby().tempoNecessario(),
                        context.hobby().nivelDificuldade(),
                        context.hobby().tipoSocializacao(),
                        context.hobby().nivelAtividadeFisica(),
                        context.hobby().ambiente(),

                        context.hobby().score(),
                        context.hobby().motivos(),
                        context.hobby().alertas(),

                        context.relacaoComHobby().nivelAtual(),
                        context.relacaoComHobby().statusAtual()
                );
    }
}