package dev.jamal.projetotcc.config;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.AmbientePreferido;
import dev.jamal.projetotcc.Enum.FormatoPreferido;
import dev.jamal.projetotcc.Enum.NivelAtividadeFisica;
import dev.jamal.projetotcc.Enum.TipoSocializacao;

import dev.jamal.projetotcc.Repository.HobbyCategoryRepository;
import dev.jamal.projetotcc.Repository.HobbyObjectiveRepository;
import dev.jamal.projetotcc.Repository.HobbyRepository;
import dev.jamal.projetotcc.Repository.ObjectiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class DevDataSeeder {

    private final HobbyCategoryRepository categoryRepository;
    private final HobbyRepository hobbyRepository;
    private final ObjectiveRepository objectiveRepository;
    private final HobbyObjectiveRepository hobbyObjectiveRepository;

    @Bean
    CommandLineRunner seedDatabase(){
        return args -> {

// =====================================================
// CATEGORIAS
// =====================================================

            HobbyCategory esporte =
                    criarCategoriaSeNaoExistir("Esporte");

            HobbyCategory criatividade =
                    criarCategoriaSeNaoExistir("Criatividade");

            HobbyCategory intelectual =
                    criarCategoriaSeNaoExistir("Intelectual");

            HobbyCategory social =
                    criarCategoriaSeNaoExistir("Social");

            HobbyCategory tecnologia =
                    criarCategoriaSeNaoExistir("Tecnologia");

            HobbyCategory relaxamento =
                    criarCategoriaSeNaoExistir("Relaxamento");


// =====================================================
// OBJETIVOS
// =====================================================

            Objective relaxar = obterOuCriarObjetivo(
                    "Relaxar",
                    "Atividades voltadas ao descanso, tranquilidade e bem-estar."
            );

            Objective conhecerPessoas = obterOuCriarObjetivo(
                    "Conhecer pessoas",
                    "Atividades que favorecem interação social e criação de vínculos."
            );

            Objective aprender = obterOuCriarObjetivo(
                    "Aprender algo novo",
                    "Atividades focadas em aprendizado e desenvolvimento de novas habilidades."
            );

            Objective condicionamento = obterOuCriarObjetivo(
                    "Condicionamento",
                    "Atividades que promovem esforço físico e condicionamento."
            );

            Objective criatividadeObjective = obterOuCriarObjetivo(
                    "Desenvolver criatividade",
                    "Atividades que estimulam imaginação, expressão e criação."
            );

            Objective disciplina = obterOuCriarObjetivo(
                    "Criar disciplina",
                    "Atividades que favorecem consistência, rotina e desenvolvimento de hábitos."
            );

            Objective reduzirEstresse = obterOuCriarObjetivo(
                    "Reduzir estresse",
                    "Atividades associadas ao relaxamento e redução de tensão."
            );

            Objective diversao = obterOuCriarObjetivo(
                    "Se divertir",
                    "Atividades voltadas principalmente ao entretenimento e lazer."
            );

            Objective produzir = obterOuCriarObjetivo(
                    "Produzir algo",
                    "Atividades que resultam na criação de algo concreto ou compartilhável."
            );

            Objective competir = obterOuCriarObjetivo(
                    "Competir",
                    "Atividades que possuem elemento competitivo ou desafios contra outras pessoas."
            );


// =====================================================
// ESPORTES
// =====================================================

            Hobby corrida = criarOuAtualizarHobby(
                    "Corrida",
                    "Atividade física ao ar livre.",
                    0.0,
                    2,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    esporte,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(corrida, condicionamento, 3);
            adicionarObjetivoAoHobby(corrida, disciplina, 2);
            adicionarObjetivoAoHobby(corrida, reduzirEstresse, 2);
            adicionarObjetivoAoHobby(corrida, competir, 1);


            Hobby futebol = criarOuAtualizarHobby(
                    "Futebol",
                    "Esporte coletivo praticado em grupo.",
                    20.0,
                    3,
                    2.0,
                    TipoSocializacao.SOCIAL,
                    esporte,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(futebol, conhecerPessoas, 3);
            adicionarObjetivoAoHobby(futebol, competir, 3);
            adicionarObjetivoAoHobby(futebol, condicionamento, 2);
            adicionarObjetivoAoHobby(futebol, diversao, 2);


            Hobby ciclismo = criarOuAtualizarHobby(
                    "Ciclismo",
                    "Prática de pedalar por lazer ou exercício.",
                    200.0,
                    3,
                    2.0,
                    TipoSocializacao.INDIVIDUAL,
                    esporte,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(ciclismo, condicionamento, 3);
            adicionarObjetivoAoHobby(ciclismo, reduzirEstresse, 2);
            adicionarObjetivoAoHobby(ciclismo, diversao, 2);
            adicionarObjetivoAoHobby(ciclismo, disciplina, 1);


            Hobby natacao = criarOuAtualizarHobby(
                    "Natação",
                    "Atividade física praticada em piscina.",
                    80.0,
                    3,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    esporte,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AMBIENTE_FECHADO,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(natacao, condicionamento, 3);
            adicionarObjetivoAoHobby(natacao, disciplina, 2);
            adicionarObjetivoAoHobby(natacao, reduzirEstresse, 2);


// =====================================================
// CRIATIVIDADE
// =====================================================

            Hobby fotografia = criarOuAtualizarHobby(
                    "Fotografia",
                    "Registrar momentos, paisagens e cenas criativas.",
                    50.0,
                    3,
                    1.5,
                    TipoSocializacao.INDIVIDUAL,
                    criatividade,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.INDIFERENTE,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(fotografia, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(fotografia, produzir, 3);
            adicionarObjetivoAoHobby(fotografia, aprender, 2);
            adicionarObjetivoAoHobby(fotografia, relaxar, 1);


            Hobby desenho = criarOuAtualizarHobby(
                    "Desenho",
                    "Prática artística utilizando lápis, papel ou ferramentas digitais.",
                    20.0,
                    2,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    criatividade,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(desenho, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(desenho, produzir, 3);
            adicionarObjetivoAoHobby(desenho, relaxar, 2);
            adicionarObjetivoAoHobby(desenho, aprender, 1);


            Hobby pintura = criarOuAtualizarHobby(
                    "Pintura",
                    "Expressão artística utilizando tintas e diferentes superfícies.",
                    60.0,
                    3,
                    1.5,
                    TipoSocializacao.INDIVIDUAL,
                    criatividade,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(pintura, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(pintura, produzir, 3);
            adicionarObjetivoAoHobby(pintura, relaxar, 2);
            adicionarObjetivoAoHobby(pintura, reduzirEstresse, 2);


            Hobby violao = criarOuAtualizarHobby(
                    "Violão",
                    "Aprendizado musical utilizando instrumento de cordas.",
                    150.0,
                    4,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    criatividade,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(violao, aprender, 3);
            adicionarObjetivoAoHobby(violao, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(violao, produzir, 2);
            adicionarObjetivoAoHobby(violao, relaxar, 1);


// =====================================================
// INTELECTUAL
// =====================================================

            Hobby xadrez = criarOuAtualizarHobby(
                    "Xadrez",
                    "Jogo estratégico que estimula raciocínio lógico.",
                    0.0,
                    4,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    intelectual,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(xadrez, aprender, 3);
            adicionarObjetivoAoHobby(xadrez, competir, 2);
            adicionarObjetivoAoHobby(xadrez, disciplina, 2);
            adicionarObjetivoAoHobby(xadrez, diversao, 1);


            Hobby leitura = criarOuAtualizarHobby(
                    "Leitura",
                    "Hábito de ler livros, artigos e outros conteúdos.",
                    30.0,
                    2,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    intelectual,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(leitura, aprender, 3);
            adicionarObjetivoAoHobby(leitura, relaxar, 2);
            adicionarObjetivoAoHobby(leitura, reduzirEstresse, 1);
            adicionarObjetivoAoHobby(leitura, disciplina, 1);


            Hobby escrita = criarOuAtualizarHobby(
                    "Escrita",
                    "Produção de textos, histórias, ideias ou reflexões.",
                    0.0,
                    3,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    intelectual,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(escrita, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(escrita, produzir, 3);
            adicionarObjetivoAoHobby(escrita, aprender, 2);
            adicionarObjetivoAoHobby(escrita, relaxar, 1);


            Hobby idiomas = criarOuAtualizarHobby(
                    "Estudo de idiomas",
                    "Aprendizado e prática de uma nova língua.",
                    50.0,
                    4,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    intelectual,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(idiomas, aprender, 3);
            adicionarObjetivoAoHobby(idiomas, disciplina, 2);
            adicionarObjetivoAoHobby(idiomas, conhecerPessoas, 1);
            adicionarObjetivoAoHobby(idiomas, produzir, 1);


// =====================================================
// SOCIAL
// =====================================================

            Hobby teatro = criarOuAtualizarHobby(
                    "Teatro",
                    "Atividade artística em grupo voltada à expressão e interpretação.",
                    30.0,
                    4,
                    2.0,
                    TipoSocializacao.SOCIAL,
                    social,
                    NivelAtividadeFisica.MODERADO,
                    AmbientePreferido.AMBIENTE_FECHADO,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(teatro, conhecerPessoas, 3);
            adicionarObjetivoAoHobby(teatro, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(teatro, produzir, 2);
            adicionarObjetivoAoHobby(teatro, aprender, 2);


            Hobby danca = criarOuAtualizarHobby(
                    "Dança",
                    "Atividade corporal, musical e social.",
                    50.0,
                    3,
                    1.5,
                    TipoSocializacao.SOCIAL,
                    social,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AMBIENTE_FECHADO,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(danca, diversao, 3);
            adicionarObjetivoAoHobby(danca, conhecerPessoas, 3);
            adicionarObjetivoAoHobby(danca, condicionamento, 2);
            adicionarObjetivoAoHobby(danca, criatividadeObjective, 2);


            Hobby voluntariado = criarOuAtualizarHobby(
                    "Voluntariado",
                    "Participação em ações sociais e comunitárias.",
                    0.0,
                    2,
                    2.0,
                    TipoSocializacao.SOCIAL,
                    social,
                    NivelAtividadeFisica.MODERADO,
                    AmbientePreferido.INDIFERENTE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(voluntariado, conhecerPessoas, 3);
            adicionarObjetivoAoHobby(voluntariado, aprender, 2);
            adicionarObjetivoAoHobby(voluntariado, disciplina, 1);


            Hobby clubeJogos = criarOuAtualizarHobby(
                    "Clube de jogos",
                    "Encontros para jogos de tabuleiro, cartas e jogos sociais.",
                    20.0,
                    2,
                    2.0,
                    TipoSocializacao.SOCIAL,
                    social,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.AMBIENTE_FECHADO,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(clubeJogos, conhecerPessoas, 3);
            adicionarObjetivoAoHobby(clubeJogos, diversao, 3);
            adicionarObjetivoAoHobby(clubeJogos, competir, 2);


// =====================================================
// TECNOLOGIA
// =====================================================

            Hobby programacaoCriativa = criarOuAtualizarHobby(
                    "Programação criativa",
                    "Criação de pequenos projetos, automações e experimentos utilizando programação.",
                    0.0,
                    4,
                    1.5,
                    TipoSocializacao.INDIVIDUAL,
                    tecnologia,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.REMOTO
            );

            adicionarObjetivoAoHobby(programacaoCriativa, aprender, 3);
            adicionarObjetivoAoHobby(programacaoCriativa, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(programacaoCriativa, produzir, 3);
            adicionarObjetivoAoHobby(programacaoCriativa, disciplina, 1);


            Hobby edicaoVideo = criarOuAtualizarHobby(
                    "Edição de vídeo",
                    "Produção e edição de conteúdos audiovisuais.",
                    0.0,
                    3,
                    1.5,
                    TipoSocializacao.INDIVIDUAL,
                    tecnologia,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.REMOTO
            );

            adicionarObjetivoAoHobby(edicaoVideo, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(edicaoVideo, produzir, 3);
            adicionarObjetivoAoHobby(edicaoVideo, aprender, 2);


            Hobby robotica = criarOuAtualizarHobby(
                    "Robótica básica",
                    "Montagem e programação de pequenos circuitos e dispositivos.",
                    150.0,
                    5,
                    2.0,
                    TipoSocializacao.INDIVIDUAL,
                    tecnologia,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(robotica, aprender, 3);
            adicionarObjetivoAoHobby(robotica, produzir, 3);
            adicionarObjetivoAoHobby(robotica, criatividadeObjective, 2);
            adicionarObjetivoAoHobby(robotica, disciplina, 2);


            Hobby criacaoJogos = criarOuAtualizarHobby(
                    "Criação de jogos",
                    "Desenvolvimento de jogos simples e interativos.",
                    0.0,
                    4,
                    2.0,
                    TipoSocializacao.INDIVIDUAL,
                    tecnologia,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.REMOTO
            );

            adicionarObjetivoAoHobby(criacaoJogos, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(criacaoJogos, produzir, 3);
            adicionarObjetivoAoHobby(criacaoJogos, aprender, 3);
            adicionarObjetivoAoHobby(criacaoJogos, diversao, 2);


// =====================================================
// RELAXAMENTO
// =====================================================

            Hobby meditacao = criarOuAtualizarHobby(
                    "Meditação",
                    "Prática de atenção plena, respiração e relaxamento.",
                    0.0,
                    1,
                    0.5,
                    TipoSocializacao.INDIVIDUAL,
                    relaxamento,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(meditacao, relaxar, 3);
            adicionarObjetivoAoHobby(meditacao, reduzirEstresse, 3);
            adicionarObjetivoAoHobby(meditacao, disciplina, 1);


            Hobby jardinagem = criarOuAtualizarHobby(
                    "Jardinagem",
                    "Cultivo e cuidado de plantas, flores e pequenos jardins.",
                    40.0,
                    2,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    relaxamento,
                    NivelAtividadeFisica.MODERADO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(jardinagem, relaxar, 3);
            adicionarObjetivoAoHobby(jardinagem, produzir, 2);
            adicionarObjetivoAoHobby(jardinagem, reduzirEstresse, 2);
            adicionarObjetivoAoHobby(jardinagem, aprender, 1);


            Hobby culinaria = criarOuAtualizarHobby(
                    "Culinária",
                    "Preparação de receitas e experimentação gastronômica.",
                    50.0,
                    3,
                    1.5,
                    TipoSocializacao.INDIVIDUAL,
                    relaxamento,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(culinaria, produzir, 3);
            adicionarObjetivoAoHobby(culinaria, criatividadeObjective, 2);
            adicionarObjetivoAoHobby(culinaria, aprender, 2);
            adicionarObjetivoAoHobby(culinaria, diversao, 2);
            adicionarObjetivoAoHobby(culinaria, relaxar, 1);


            Hobby caminhada = criarOuAtualizarHobby(
                    "Caminhada",
                    "Atividade física leve praticada principalmente ao ar livre.",
                    0.0,
                    1,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    relaxamento,
                    NivelAtividadeFisica.MODERADO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(caminhada, relaxar, 3);
            adicionarObjetivoAoHobby(caminhada, reduzirEstresse, 3);
            adicionarObjetivoAoHobby(caminhada, condicionamento, 2);


            System.out.println("Seed finalizado");
        };
    }


    private HobbyCategory criarCategoriaSeNaoExistir(String nome) {
        return categoryRepository.findByNomeIgnoreCase(nome)
                .orElseGet(() -> {
                    HobbyCategory category = new HobbyCategory();
                    category.setNome(nome);
                    return categoryRepository.save(category);
                });
    }


    private Hobby criarOuAtualizarHobby(String nome, String descricao, Double custoEstimado, Integer nivelDificuldade,
    Double tempoNecessario, TipoSocializacao tipoSocializacao, HobbyCategory category,
    NivelAtividadeFisica atividadeFisica, AmbientePreferido ambiente, FormatoPreferido formato
    ) {
        Hobby hobby = hobbyRepository.findByNomeIgnoreCase(nome)
                .orElseGet(Hobby::new);

        hobby.setNome(nome);
        hobby.setDescricao(descricao);
        hobby.setCustoEstimado(custoEstimado);
        hobby.setNivelDificuldade(nivelDificuldade);
        hobby.setTempoNecessario(tempoNecessario);
        hobby.setTipoSocializacao(tipoSocializacao);
        hobby.setCategory(category);
        hobby.setNivelAtividadeFisica(atividadeFisica);
        hobby.setAmbiente(ambiente);
        hobby.setFormato(formato);
        hobby.setRequerEquipamento(hobby.getCustoEstimado() != null && hobby.getCustoEstimado() > 0);

        return hobbyRepository.save(hobby);
    }

    private Objective obterOuCriarObjetivo(
        String nome,
        String descricao
    ){
        return objectiveRepository
                .findByNomeIgnoreCase(nome)
                .orElseGet(() -> {
                    Objective objective = new Objective();
                    objective.setNome(nome);
                    objective.setDescricao(descricao);

                    return objectiveRepository.save(objective);
                });
    }

    private void adicionarObjetivoAoHobby(
        Hobby hobby,
        Objective objective,
        Integer peso
    ){
        HobbyObjectiveId id = new HobbyObjectiveId();
        id.setHobbyId(hobby.getId());
        id.setObjectiveId(objective.getId());

        HobbyObjective hobbyObjective =
                hobbyObjectiveRepository
                        .findById(id)
                        .orElseGet(HobbyObjective::new);

        hobbyObjective.setId(id);
        hobbyObjective.setHobby(hobby);
        hobbyObjective.setObjective(objective);
        hobbyObjective.setPeso(peso);

        hobbyObjectiveRepository.save(hobbyObjective);
    }

}
