package dev.jamal.projetotcc.config;
import dev.jamal.projetotcc.Entities.*;
import dev.jamal.projetotcc.Enum.AmbientePreferido;
import dev.jamal.projetotcc.Enum.FormatoPreferido;
import dev.jamal.projetotcc.Enum.NivelAtividadeFisica;
import dev.jamal.projetotcc.Enum.TipoSocializacao;

import dev.jamal.projetotcc.Repository.*;
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
    private final InterestRepository interestRepository;
    private final HobbyInterestRepository hobbyInterestRepository;

    @Bean
    CommandLineRunner seedDatabase(){
        return args -> {
// =====================================================
// INTERESSES
// =====================================================
            Interest esportesInterest = obterOuCriarInteresse("Esportes");
            Interest musicaInterest = obterOuCriarInteresse("Música");
            Interest artesVisuaisInterest = obterOuCriarInteresse("Artes visuais");
            Interest tecnologiaInterest = obterOuCriarInteresse("Tecnologia");
            Interest jogosInterest = obterOuCriarInteresse("Jogos");
            Interest naturezaInterest = obterOuCriarInteresse("Natureza");
            Interest literaturaInterest = obterOuCriarInteresse("Leitura e literatura");
            Interest culinariaInterest = obterOuCriarInteresse("Culinária");
            Interest aprendizadoInterest = obterOuCriarInteresse("Aprendizado");
            Interest criacaoInterest = obterOuCriarInteresse("Criação");
            Interest culturaInterest = obterOuCriarInteresse("Cultura e expressão");
            Interest socializacaoInterest = obterOuCriarInteresse("Atividades sociais");
            Interest relaxarInterest = obterOuCriarInteresse("Relaxar");


// =====================================================
// CATEGORIAS
// =====================================================

            HobbyCategory esporteCategory =
                    criarCategoriaSeNaoExistir("Esporte");

            HobbyCategory criatividadeCategory =
                    criarCategoriaSeNaoExistir("Criatividade");

            HobbyCategory intelectualCategory =
                    criarCategoriaSeNaoExistir("Intelectual");

            HobbyCategory socialCategory =
                    criarCategoriaSeNaoExistir("Social");

            HobbyCategory tecnologiaCategory =
                    criarCategoriaSeNaoExistir("Tecnologia");

            HobbyCategory relaxamentoCategory =
                    criarCategoriaSeNaoExistir("Relaxamento");


// =====================================================
// OBJETIVOS
// =====================================================

            Objective relaxarObjective = obterOuCriarObjetivo(
                    "Relaxar",
                    "Atividades voltadas ao descanso, tranquilidade e bem-estar."
            );

            Objective conhecerPessoasObjective = obterOuCriarObjetivo(
                    "Conhecer pessoas",
                    "Atividades que favorecem interação social e criação de vínculos."
            );

            Objective aprenderObjective = obterOuCriarObjetivo(
                    "Aprender algo novo",
                    "Atividades focadas em aprendizado e desenvolvimento de novas habilidades."
            );

            Objective condicionamentoObjective = obterOuCriarObjetivo(
                    "Condicionamento",
                    "Atividades que promovem esforço físico e condicionamento."
            );

            Objective criatividadeObjective = obterOuCriarObjetivo(
                    "Desenvolver criatividade",
                    "Atividades que estimulam imaginação, expressão e criação."
            );

            Objective disciplinaObjective = obterOuCriarObjetivo(
                    "Criar disciplina",
                    "Atividades que favorecem consistência, rotina e desenvolvimento de hábitos."
            );

            Objective reduzirEstresseObjective = obterOuCriarObjetivo(
                    "Reduzir estresse",
                    "Atividades associadas ao relaxamento e redução de tensão."
            );

            Objective diversaoObjective = obterOuCriarObjetivo(
                    "Se divertir",
                    "Atividades voltadas principalmente ao entretenimento e lazer."
            );

            Objective produzirObjective = obterOuCriarObjetivo(
                    "Produzir algo",
                    "Atividades que resultam na criação de algo concreto ou compartilhável."
            );

            Objective competirObjective = obterOuCriarObjetivo(
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
                    esporteCategory,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(corrida, condicionamentoObjective, 3);
            adicionarObjetivoAoHobby(corrida, disciplinaObjective, 2);
            adicionarObjetivoAoHobby(corrida, reduzirEstresseObjective, 2);
            adicionarObjetivoAoHobby(corrida, competirObjective, 1);
            adicionarInteresseAoHobby(corrida, esportesInterest, 3);
            adicionarInteresseAoHobby(corrida, naturezaInterest, 2);


            Hobby futebol = criarOuAtualizarHobby(
                    "Futebol",
                    "Esporte coletivo praticado em grupo.",
                    20.0,
                    3,
                    2.0,
                    TipoSocializacao.SOCIAL,
                    esporteCategory,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(futebol, conhecerPessoasObjective, 3);
            adicionarObjetivoAoHobby(futebol, competirObjective, 3);
            adicionarObjetivoAoHobby(futebol, condicionamentoObjective, 2);
            adicionarObjetivoAoHobby(futebol, diversaoObjective, 2);
            adicionarInteresseAoHobby(futebol, esportesInterest, 3);
            adicionarInteresseAoHobby(futebol, socializacaoInterest, 2);
            adicionarInteresseAoHobby(futebol, jogosInterest, 1);


            Hobby ciclismo = criarOuAtualizarHobby(
                    "Ciclismo",
                    "Prática de pedalar por lazer ou exercício.",
                    200.0,
                    3,
                    2.0,
                    TipoSocializacao.INDIVIDUAL,
                    esporteCategory,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(ciclismo, condicionamentoObjective, 3);
            adicionarObjetivoAoHobby(ciclismo, reduzirEstresseObjective, 2);
            adicionarObjetivoAoHobby(ciclismo, diversaoObjective, 2);
            adicionarObjetivoAoHobby(ciclismo, disciplinaObjective, 1);


            Hobby natacao = criarOuAtualizarHobby(
                    "Natação",
                    "Atividade física praticada em piscina.",
                    80.0,
                    3,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    esporteCategory,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AMBIENTE_FECHADO,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(natacao, condicionamentoObjective, 3);
            adicionarObjetivoAoHobby(natacao, disciplinaObjective, 2);
            adicionarObjetivoAoHobby(natacao, reduzirEstresseObjective, 2);


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
                    criatividadeCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.INDIFERENTE,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(fotografia, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(fotografia, produzirObjective, 3);
            adicionarObjetivoAoHobby(fotografia, aprenderObjective, 2);
            adicionarObjetivoAoHobby(fotografia, relaxarObjective, 1);
            adicionarInteresseAoHobby(fotografia, artesVisuaisInterest, 3);
            adicionarInteresseAoHobby(fotografia, naturezaInterest, 2);
            adicionarInteresseAoHobby(fotografia, criacaoInterest, 3);


            Hobby desenho = criarOuAtualizarHobby(
                    "Desenho",
                    "Prática artística utilizando lápis, papel ou ferramentas digitais.",
                    20.0,
                    2,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    criatividadeCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(desenho, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(desenho, produzirObjective, 3);
            adicionarObjetivoAoHobby(desenho, relaxarObjective, 2);
            adicionarObjetivoAoHobby(desenho, aprenderObjective, 1);


            Hobby pintura = criarOuAtualizarHobby(
                    "Pintura",
                    "Expressão artística utilizando tintas e diferentes superfícies.",
                    60.0,
                    3,
                    1.5,
                    TipoSocializacao.INDIVIDUAL,
                    criatividadeCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(pintura, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(pintura, produzirObjective, 3);
            adicionarObjetivoAoHobby(pintura, relaxarObjective, 2);
            adicionarObjetivoAoHobby(pintura, reduzirEstresseObjective, 2);


            Hobby violao = criarOuAtualizarHobby(
                    "Violão",
                    "Aprendizado musical utilizando instrumento de cordas.",
                    150.0,
                    4,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    criatividadeCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(violao, aprenderObjective, 3);
            adicionarObjetivoAoHobby(violao, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(violao, produzirObjective, 2);
            adicionarObjetivoAoHobby(violao, relaxarObjective, 1);
            adicionarInteresseAoHobby(violao, musicaInterest, 3);
            adicionarInteresseAoHobby(violao, criacaoInterest, 2);
            adicionarInteresseAoHobby(violao, culturaInterest, 2);


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
                    intelectualCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(xadrez, aprenderObjective, 3);
            adicionarObjetivoAoHobby(xadrez, competirObjective, 2);
            adicionarObjetivoAoHobby(xadrez, disciplinaObjective, 2);
            adicionarObjetivoAoHobby(xadrez, diversaoObjective, 1);
            adicionarInteresseAoHobby(xadrez, jogosInterest, 3);
            adicionarInteresseAoHobby(xadrez, aprendizadoInterest, 2);


            Hobby leitura = criarOuAtualizarHobby(
                    "Leitura",
                    "Hábito de ler livros, artigos e outros conteúdos.",
                    30.0,
                    2,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    intelectualCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(leitura, aprenderObjective, 3);
            adicionarObjetivoAoHobby(leitura, relaxarObjective, 2);
            adicionarObjetivoAoHobby(leitura, reduzirEstresseObjective, 1);
            adicionarObjetivoAoHobby(leitura, disciplinaObjective, 1);


            Hobby escrita = criarOuAtualizarHobby(
                    "Escrita",
                    "Produção de textos, histórias, ideias ou reflexões.",
                    0.0,
                    3,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    intelectualCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(escrita, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(escrita, produzirObjective, 3);
            adicionarObjetivoAoHobby(escrita, aprenderObjective, 2);
            adicionarObjetivoAoHobby(escrita, relaxarObjective, 1);


            Hobby idiomas = criarOuAtualizarHobby(
                    "Estudo de idiomas",
                    "Aprendizado e prática de uma nova língua.",
                    50.0,
                    4,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    intelectualCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(idiomas, aprenderObjective, 3);
            adicionarObjetivoAoHobby(idiomas, disciplinaObjective, 2);
            adicionarObjetivoAoHobby(idiomas, conhecerPessoasObjective, 1);
            adicionarObjetivoAoHobby(idiomas, produzirObjective, 1);


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
                    socialCategory,
                    NivelAtividadeFisica.MODERADO,
                    AmbientePreferido.AMBIENTE_FECHADO,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(teatro, conhecerPessoasObjective, 3);
            adicionarObjetivoAoHobby(teatro, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(teatro, produzirObjective, 2);
            adicionarObjetivoAoHobby(teatro, aprenderObjective, 2);


            Hobby danca = criarOuAtualizarHobby(
                    "Dança",
                    "Atividade corporal, musical e social.",
                    50.0,
                    3,
                    1.5,
                    TipoSocializacao.SOCIAL,
                    socialCategory,
                    NivelAtividadeFisica.ALTO,
                    AmbientePreferido.AMBIENTE_FECHADO,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(danca, diversaoObjective, 3);
            adicionarObjetivoAoHobby(danca, conhecerPessoasObjective, 3);
            adicionarObjetivoAoHobby(danca, condicionamentoObjective, 2);
            adicionarObjetivoAoHobby(danca, criatividadeObjective, 2);


            Hobby voluntariado = criarOuAtualizarHobby(
                    "Voluntariado",
                    "Participação em ações sociais e comunitárias.",
                    0.0,
                    2,
                    2.0,
                    TipoSocializacao.SOCIAL,
                    socialCategory,
                    NivelAtividadeFisica.MODERADO,
                    AmbientePreferido.INDIFERENTE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(voluntariado, conhecerPessoasObjective, 3);
            adicionarObjetivoAoHobby(voluntariado, aprenderObjective, 2);
            adicionarObjetivoAoHobby(voluntariado, disciplinaObjective, 1);


            Hobby clubeJogos = criarOuAtualizarHobby(
                    "Clube de jogos",
                    "Encontros para jogos de tabuleiro, cartas e jogos sociais.",
                    20.0,
                    2,
                    2.0,
                    TipoSocializacao.SOCIAL,
                    socialCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.AMBIENTE_FECHADO,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(clubeJogos, conhecerPessoasObjective, 3);
            adicionarObjetivoAoHobby(clubeJogos, diversaoObjective, 3);
            adicionarObjetivoAoHobby(clubeJogos, competirObjective, 2);


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
                    tecnologiaCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.REMOTO
            );

            adicionarObjetivoAoHobby(programacaoCriativa, aprenderObjective, 3);
            adicionarObjetivoAoHobby(programacaoCriativa, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(programacaoCriativa, produzirObjective, 3);
            adicionarObjetivoAoHobby(programacaoCriativa, disciplinaObjective, 1);
            adicionarInteresseAoHobby(programacaoCriativa, tecnologiaInterest, 3);
            adicionarInteresseAoHobby(programacaoCriativa, criacaoInterest, 3);
            adicionarInteresseAoHobby(programacaoCriativa, aprendizadoInterest, 2);


            Hobby edicaoVideo = criarOuAtualizarHobby(
                    "Edição de vídeo",
                    "Produção e edição de conteúdos audiovisuais.",
                    0.0,
                    3,
                    1.5,
                    TipoSocializacao.INDIVIDUAL,
                    tecnologiaCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.REMOTO
            );

            adicionarObjetivoAoHobby(edicaoVideo, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(edicaoVideo, produzirObjective, 3);
            adicionarObjetivoAoHobby(edicaoVideo, aprenderObjective, 2);


            Hobby robotica = criarOuAtualizarHobby(
                    "Robótica básica",
                    "Montagem e programação de pequenos circuitos e dispositivos.",
                    150.0,
                    5,
                    2.0,
                    TipoSocializacao.INDIVIDUAL,
                    tecnologiaCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(robotica, aprenderObjective, 3);
            adicionarObjetivoAoHobby(robotica, produzirObjective, 3);
            adicionarObjetivoAoHobby(robotica, criatividadeObjective, 2);
            adicionarObjetivoAoHobby(robotica, disciplinaObjective, 2);


            Hobby criacaoJogos = criarOuAtualizarHobby(
                    "Criação de jogos",
                    "Desenvolvimento de jogos simples e interativos.",
                    0.0,
                    4,
                    2.0,
                    TipoSocializacao.INDIVIDUAL,
                    tecnologiaCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.REMOTO
            );

            adicionarObjetivoAoHobby(criacaoJogos, criatividadeObjective, 3);
            adicionarObjetivoAoHobby(criacaoJogos, produzirObjective, 3);
            adicionarObjetivoAoHobby(criacaoJogos, aprenderObjective, 3);
            adicionarObjetivoAoHobby(criacaoJogos, diversaoObjective, 2);


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
                    relaxamentoCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(meditacao, relaxarObjective, 3);
            adicionarObjetivoAoHobby(meditacao, reduzirEstresseObjective, 3);
            adicionarObjetivoAoHobby(meditacao, disciplinaObjective, 1);


            Hobby jardinagem = criarOuAtualizarHobby(
                    "Jardinagem",
                    "Cultivo e cuidado de plantas, flores e pequenos jardins.",
                    40.0,
                    2,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    relaxamentoCategory,
                    NivelAtividadeFisica.MODERADO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(jardinagem, relaxarObjective, 3);
            adicionarObjetivoAoHobby(jardinagem, produzirObjective, 2);
            adicionarObjetivoAoHobby(jardinagem, reduzirEstresseObjective, 2);
            adicionarObjetivoAoHobby(jardinagem, aprenderObjective, 1);


            Hobby culinaria = criarOuAtualizarHobby(
                    "Culinária",
                    "Preparação de receitas e experimentação gastronômica.",
                    50.0,
                    3,
                    1.5,
                    TipoSocializacao.INDIVIDUAL,
                    relaxamentoCategory,
                    NivelAtividadeFisica.BAIXO,
                    AmbientePreferido.CASA,
                    FormatoPreferido.HIBRIDO
            );

            adicionarObjetivoAoHobby(culinaria, produzirObjective, 3);
            adicionarObjetivoAoHobby(culinaria, criatividadeObjective, 2);
            adicionarObjetivoAoHobby(culinaria, aprenderObjective, 2);
            adicionarObjetivoAoHobby(culinaria, diversaoObjective, 2);
            adicionarObjetivoAoHobby(culinaria, relaxarObjective, 1);


            Hobby caminhada = criarOuAtualizarHobby(
                    "Caminhada",
                    "Atividade física leve praticada principalmente ao ar livre.",
                    0.0,
                    1,
                    1.0,
                    TipoSocializacao.INDIVIDUAL,
                    relaxamentoCategory,
                    NivelAtividadeFisica.MODERADO,
                    AmbientePreferido.AO_AR_LIVRE,
                    FormatoPreferido.PRESENCIAL
            );

            adicionarObjetivoAoHobby(caminhada, relaxarObjective, 3);
            adicionarObjetivoAoHobby(caminhada, reduzirEstresseObjective, 3);
            adicionarObjetivoAoHobby(caminhada, condicionamentoObjective, 2);
            adicionarInteresseAoHobby(caminhada, relaxarInterest, 3);


            System.out.println("Seed finalizado");
        };
    }

    private Interest obterOuCriarInteresse(String nome) {
        return interestRepository
                .findByNomeIgnoreCase(nome)
                .orElseGet(() -> {
                    Interest interest = new Interest();
                    interest.setNome(nome);

                    return interestRepository.save(interest);
                });
    }

    private void adicionarInteresseAoHobby(
         Hobby hobby,
         Interest interest,
         Integer peso
    ) {
        HobbyInterestId id = new HobbyInterestId();

        id.setHobbyId(hobby.getId());
        id.setInterestId(interest.getId());

        HobbyInterest hobbyInterest =
                hobbyInterestRepository
                        .findById(id)
                        .orElseGet(HobbyInterest::new);

        hobbyInterest.setId(id);
        hobbyInterest.setHobby(hobby);
        hobbyInterest.setInterest(interest);
        hobbyInterest.setPeso(peso);

        hobbyInterestRepository.save(hobbyInterest);
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
