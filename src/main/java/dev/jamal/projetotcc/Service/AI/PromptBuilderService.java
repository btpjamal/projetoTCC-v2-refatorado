package dev.jamal.projetotcc.Service.AI;

import dev.jamal.projetotcc.DTO.AI.AIUserContext;
import org.springframework.stereotype.Service;

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
}