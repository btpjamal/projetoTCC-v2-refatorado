package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIGeneralPlanContext;
import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PromptBuilderService {

    public String construirPromptPlanoInicial(AIUserContext context) {

        return """
                Você é um assistente especializado em criar planos personalizados para hobbies.
                
                Crie um plano inicial para o hobby abaixo usando somente o contexto fornecido.
                
                REGRAS:
                - Respeite orçamento e tempo semanal disponível.
                - O tempo semanal é um limite máximo, não uma meta a preencher.
                - Adapte ao nível atual do usuário no hobby.
                - Considere objetivos, interesses, ambiente, socialização, atividade física e alertas.
                - O score é apenas um ranking interno, não uma porcentagem.
                - Não invente locais específicos, eventos, preços atuais ou estabelecimentos.
                - Use cidade/estado apenas como contexto geral.
                - Evite compras desnecessárias.
                - Adapte incompatibilidades quando possível sem descaracterizar o hobby.
                - Seja prático e realista.
                
                USUÁRIO:
                Idade: %s
                Localização: %s - %s
                
                PERFIL:
                Tempo semanal máximo: %s h
                Orçamento inicial: R$ %s
                Socialização: %s
                Atividade física: %s
                Ambiente: %s
                Interesses: %s
                Objetivos: %s
                
                HOBBY:
                Nome: %s
                Descrição: %s
                Categoria: %s
                Custo estimado: R$ %s
                Tempo estimado: %s h
                Dificuldade: %s
                Socialização: %s
                Atividade física: %s
                Ambiente: %s
                
                COMPATIBILIDADE:
                Score interno: %s
                Motivos: %s
                Alertas: %s
                
                RELAÇÃO COM O HOBBY:
                Nível: %s
                Status: %s
                
                Retorne somente o plano em Markdown nesta estrutura:
                
                ## Introdução
                
                ## Meta inicial
                
                ## Plano para as primeiras 4 semanas
                
                ### Semana 1
                - ...
                
                ### Semana 2
                - ...
                
                ### Semana 3
                - ...
                
                ### Semana 4
                - ...
                
                ## Distribuição do tempo semanal
                - ...
                
                ## Materiais e recursos necessários
                - ...
                
                ## Estimativa de custo inicial
                - ...
                
                ## Dicas para seus objetivos
                - ...
                
                ## Cuidados e dificuldades
                - ...
                
                ## Próximo passo concreto
                
                Use parágrafos curtos e listas. Separe as seções com linhas em branco.
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

    public String construirPromptPlanoGeral(
            AIGeneralPlanContext context
    ) {

        String hobbies = context.hobbies()
                .stream()
                .map(hobby -> """
                    - %s
                      categoria: %s
                      tempo estimado: %s h
                      atividade física: %s
                      ambiente: %s
                      score: %s
                      nível: %s
                      status: %s
                    """.formatted(
                        hobby.nome(),
                        hobby.categoria(),
                        hobby.tempoNecessario(),
                        hobby.nivelAtividadeFisica(),
                        hobby.ambiente(),
                        hobby.score(),
                        hobby.nivelAtual(),
                        hobby.statusAtual()
                ))
                .collect(Collectors.joining("\n"));

        return """
            Você é um assistente especializado em organizar hobbies na rotina.

            Crie um plano geral personalizado usando somente o contexto fornecido.

            REGRAS:
            - O tempo semanal é um limite máximo, não uma meta a preencher.
            - Considere o orçamento como limite global.
            - O orçamento global não precisa ser totalmente utilizado e deve considerar apenas gastos adicionais necessários para a rotina proposta.
            - Não é necessário incluir todos os hobbies na rotina.
            - Não crie um plano individual detalhado para cada hobby; o objetivo é organizar os hobbies em conjunto dentro da rotina.
            - Priorize uma rotina sustentável e realista.
            - Considere nível, status e compatibilidade de cada hobby.
            - Hobbies PRATICANDO têm prioridade de continuidade.
            - Hobbies INTERESSADO podem ser introduzidos quando houver espaço.
            - Hobbies PAUSADO não devem ser retomados automaticamente; sugira retorno apenas quando fizer sentido.
            - O tempo estimado de cada hobby é apenas referência, não uma quantidade obrigatória.
            - O score é um ranking interno, não uma porcentagem.
            - Não altere nível ou status do usuário.
            - Não invente locais, eventos ou preços atuais.
            - Evite uma rotina excessivamente fragmentada.
            - Não estime preços ou custos específicos quando eles não estiverem presentes no contexto.
            - Não presuma que o usuário possui ou não possui equipamentos.
            - O orçamento deve ser usado apenas como restrição geral da rotina.
            - Quando algum hobby puder exigir gasto, mencione apenas que pode haver custo adicional, sem inventar valores.

            USUÁRIO:
            Idade: %s
            Localização: %s - %s

            PERFIL:
            Tempo semanal máximo: %s h
            Orçamento inicial: R$ %s
            Socialização: %s
            Atividade física: %s
            Ambiente: %s
            Interesses: %s
            Objetivos: %s

            HOBBIES CANDIDATOS:
            %s

            Retorne somente o plano em Markdown nesta estrutura:

            ## Visão geral

            ## Hobbies priorizados
            - ...

            ## Rotina semanal sugerida
            - ...

            ## Distribuição do tempo
            - Informe o tempo semanal total sugerido.
            - Indique apenas considerações gerais de orçamento, sem inventar preços.

            ## Hobbies em pausa ou baixa prioridade
            - ...

            ## Orientações para manter a rotina
            - ...

            ## Próximo passo
            """.formatted(
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

                hobbies
        );
    }
}