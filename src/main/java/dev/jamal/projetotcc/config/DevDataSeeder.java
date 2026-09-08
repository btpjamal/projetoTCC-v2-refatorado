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

            Interesses interesses = seedInteresses();
            Categorias categorias = seedCategorias();
            Objetivos objetivos = seedObjetivos();


            seedEsportes(categorias, interesses, objetivos);
            seedCriatividade(categorias, interesses, objetivos);
            seedIntelectual(categorias, interesses, objetivos);
            seedSocial(categorias, interesses, objetivos);
            seedTecnologia(categorias, interesses, objetivos);
            seedRelaxamento(categorias, interesses, objetivos);


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

    private record Interesses(
            Interest esportes,
            Interest musica,
            Interest artesVisuais,
            Interest tecnologia,
            Interest jogos,
            Interest natureza,
            Interest literatura,
            Interest culinaria,
            Interest aprendizado,
            Interest criacao,
            Interest cultura,
            Interest socializacao,
            Interest relaxar
    ) {
    }

    private record Categorias(
            HobbyCategory esporte,
            HobbyCategory criatividade,
            HobbyCategory intelectual,
            HobbyCategory social,
            HobbyCategory tecnologia,
            HobbyCategory relaxamento
    ) {
    }

    private record Objetivos(
            Objective relaxar,
            Objective conhecerPessoas,
            Objective aprender,
            Objective condicionamento,
            Objective criatividade,
            Objective disciplina,
            Objective reduzirEstresse,
            Objective diversao,
            Objective produzir,
            Objective competir
    ) {
    }

    private Interesses seedInteresses() {

        return new Interesses(
                obterOuCriarInteresse("Esportes"),
                obterOuCriarInteresse("Música"),
                obterOuCriarInteresse("Artes visuais"),
                obterOuCriarInteresse("Tecnologia"),
                obterOuCriarInteresse("Jogos"),
                obterOuCriarInteresse("Natureza"),
                obterOuCriarInteresse("Leitura e literatura"),
                obterOuCriarInteresse("Culinária"),
                obterOuCriarInteresse("Aprendizado"),
                obterOuCriarInteresse("Criação"),
                obterOuCriarInteresse("Cultura e expressão"),
                obterOuCriarInteresse("Atividades sociais"),
                obterOuCriarInteresse("Relaxar")
        );
    }

    private Categorias seedCategorias() {

        return new Categorias(
                criarCategoriaSeNaoExistir("Esporte"),
                criarCategoriaSeNaoExistir("Criatividade"),
                criarCategoriaSeNaoExistir("Intelectual"),
                criarCategoriaSeNaoExistir("Social"),
                criarCategoriaSeNaoExistir("Tecnologia"),
                criarCategoriaSeNaoExistir("Relaxamento")
        );
    }

    private Objetivos seedObjetivos() {

        return new Objetivos(

                obterOuCriarObjetivo(
                        "Relaxar",
                        "Atividades voltadas ao descanso, tranquilidade e bem-estar."
                ),

                obterOuCriarObjetivo(
                        "Conhecer pessoas",
                        "Atividades que favorecem interação social e criação de vínculos."
                ),

                obterOuCriarObjetivo(
                        "Aprender algo novo",
                        "Atividades focadas em aprendizado e desenvolvimento de novas habilidades."
                ),

                obterOuCriarObjetivo(
                        "Condicionamento",
                        "Atividades que promovem esforço físico e condicionamento."
                ),

                obterOuCriarObjetivo(
                        "Desenvolver criatividade",
                        "Atividades que estimulam imaginação, expressão e criação."
                ),

                obterOuCriarObjetivo(
                        "Criar disciplina",
                        "Atividades que favorecem consistência, rotina e desenvolvimento de hábitos."
                ),

                obterOuCriarObjetivo(
                        "Reduzir estresse",
                        "Atividades associadas ao relaxamento e redução de tensão."
                ),

                obterOuCriarObjetivo(
                        "Se divertir",
                        "Atividades voltadas principalmente ao entretenimento e lazer."
                ),

                obterOuCriarObjetivo(
                        "Produzir algo",
                        "Atividades que resultam na criação de algo concreto ou compartilhável."
                ),

                obterOuCriarObjetivo(
                        "Competir",
                        "Atividades que possuem elemento competitivo ou desafios contra outras pessoas."
                )
        );
    }

    private void seedEsportes(
            Categorias categorias,
            Interesses interesses,
            Objetivos objetivos
    ) {

        Hobby corrida = criarOuAtualizarHobby(
                "Corrida",
                "Atividade física ao ar livre.",
                0.0,
                2,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.esporte(),
                NivelAtividadeFisica.ALTO,
                AmbientePreferido.AO_AR_LIVRE,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(corrida, objetivos.condicionamento(), 3);
        adicionarObjetivoAoHobby(corrida, objetivos.disciplina(), 2);
        adicionarObjetivoAoHobby(corrida, objetivos.reduzirEstresse(), 2);
        adicionarObjetivoAoHobby(corrida, objetivos.competir(), 1);

        adicionarInteresseAoHobby(corrida, interesses.esportes(), 3);
        adicionarInteresseAoHobby(corrida, interesses.natureza(), 2);


        Hobby futebol = criarOuAtualizarHobby(
                "Futebol",
                "Esporte coletivo praticado em grupo.",
                20.0,
                3,
                2.0,
                TipoSocializacao.SOCIAL,
                categorias.esporte(),
                NivelAtividadeFisica.ALTO,
                AmbientePreferido.AO_AR_LIVRE,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(futebol, objetivos.conhecerPessoas(), 3);
        adicionarObjetivoAoHobby(futebol, objetivos.competir(), 3);
        adicionarObjetivoAoHobby(futebol, objetivos.condicionamento(), 2);
        adicionarObjetivoAoHobby(futebol, objetivos.diversao(), 2);

        adicionarInteresseAoHobby(futebol, interesses.esportes(), 3);
        adicionarInteresseAoHobby(futebol, interesses.socializacao(), 2);
        adicionarInteresseAoHobby(futebol, interesses.jogos(), 1);


        Hobby ciclismo = criarOuAtualizarHobby(
                "Ciclismo",
                "Prática de pedalar por lazer ou exercício.",
                200.0,
                3,
                2.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.esporte(),
                NivelAtividadeFisica.ALTO,
                AmbientePreferido.AO_AR_LIVRE,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(ciclismo, objetivos.condicionamento(), 3);
        adicionarObjetivoAoHobby(ciclismo, objetivos.reduzirEstresse(), 2);
        adicionarObjetivoAoHobby(ciclismo, objetivos.diversao(), 2);
        adicionarObjetivoAoHobby(ciclismo, objetivos.disciplina(), 1);

        adicionarInteresseAoHobby(ciclismo, interesses.esportes(), 3);
        adicionarInteresseAoHobby(ciclismo, interesses.natureza(), 2);


        Hobby natacao = criarOuAtualizarHobby(
                "Natação",
                "Atividade física praticada em piscina.",
                80.0,
                3,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.esporte(),
                NivelAtividadeFisica.ALTO,
                AmbientePreferido.AMBIENTE_FECHADO,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(natacao, objetivos.condicionamento(), 3);
        adicionarObjetivoAoHobby(natacao, objetivos.disciplina(), 2);
        adicionarObjetivoAoHobby(natacao, objetivos.reduzirEstresse(), 2);

        adicionarInteresseAoHobby(natacao, interesses.esportes(), 3);
        adicionarInteresseAoHobby(natacao, interesses.relaxar(), 1);
    }

    private void seedCriatividade(
            Categorias categorias,
            Interesses interesses,
            Objetivos objetivos
    ) {

        Hobby fotografia = criarOuAtualizarHobby(
                "Fotografia",
                "Registrar momentos, paisagens e cenas criativas.",
                50.0,
                3,
                1.5,
                TipoSocializacao.INDIVIDUAL,
                categorias.criatividade(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.INDIFERENTE,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(fotografia, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(fotografia, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(fotografia, objetivos.aprender(), 2);
        adicionarObjetivoAoHobby(fotografia, objetivos.relaxar(), 1);

        adicionarInteresseAoHobby(fotografia, interesses.artesVisuais(), 3);
        adicionarInteresseAoHobby(fotografia, interesses.natureza(), 2);
        adicionarInteresseAoHobby(fotografia, interesses.criacao(), 3);


        Hobby desenho = criarOuAtualizarHobby(
                "Desenho",
                "Prática artística utilizando lápis, papel ou ferramentas digitais.",
                20.0,
                2,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.criatividade(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(desenho, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(desenho, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(desenho, objetivos.relaxar(), 2);
        adicionarObjetivoAoHobby(desenho, objetivos.aprender(), 1);

        adicionarInteresseAoHobby(desenho, interesses.artesVisuais(), 3);
        adicionarInteresseAoHobby(desenho, interesses.criacao(), 3);


        Hobby pintura = criarOuAtualizarHobby(
                "Pintura",
                "Expressão artística utilizando tintas e diferentes superfícies.",
                60.0,
                3,
                1.5,
                TipoSocializacao.INDIVIDUAL,
                categorias.criatividade(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(pintura, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(pintura, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(pintura, objetivos.relaxar(), 2);
        adicionarObjetivoAoHobby(pintura, objetivos.reduzirEstresse(), 2);

        adicionarInteresseAoHobby(pintura, interesses.artesVisuais(), 3);
        adicionarInteresseAoHobby(pintura, interesses.criacao(), 3);


        Hobby violao = criarOuAtualizarHobby(
                "Violão",
                "Aprendizado musical utilizando instrumento de cordas.",
                150.0,
                4,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.criatividade,
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(violao, objetivos.aprender(), 3);
        adicionarObjetivoAoHobby(violao, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(violao, objetivos.produzir(), 2);
        adicionarObjetivoAoHobby(violao, objetivos.relaxar(), 1);

        adicionarInteresseAoHobby(violao, interesses.musica(), 3);
        adicionarInteresseAoHobby(violao, interesses.criacao(), 2);
        adicionarInteresseAoHobby(violao, interesses.cultura(), 2);
    }

    private void seedIntelectual(
            Categorias categorias,
            Interesses interesses,
            Objetivos objetivos
    ) {

        Hobby xadrez = criarOuAtualizarHobby(
                "Xadrez",
                "Jogo estratégico que estimula raciocínio lógico.",
                0.0,
                4,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.intelectual(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(xadrez, objetivos.aprender(), 3);
        adicionarObjetivoAoHobby(xadrez, objetivos.competir(), 2);
        adicionarObjetivoAoHobby(xadrez, objetivos.disciplina(), 2);
        adicionarObjetivoAoHobby(xadrez, objetivos.diversao(), 1);
        adicionarInteresseAoHobby(xadrez, interesses.jogos(), 3);
        adicionarInteresseAoHobby(xadrez, interesses.aprendizado(), 2);


        Hobby leitura = criarOuAtualizarHobby(
                "Leitura",
                "Hábito de ler livros, artigos e outros conteúdos.",
                30.0,
                2,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.intelectual(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(leitura, objetivos.aprender(), 3);
        adicionarObjetivoAoHobby(leitura, objetivos.relaxar(), 2);
        adicionarObjetivoAoHobby(leitura, objetivos.reduzirEstresse(), 1);
        adicionarObjetivoAoHobby(leitura, objetivos.disciplina(), 1);
        adicionarInteresseAoHobby(leitura, interesses.literatura(), 3);
        adicionarInteresseAoHobby(leitura, interesses.aprendizado(), 2);
        adicionarInteresseAoHobby(leitura, interesses.relaxar(), 2);


        Hobby escrita = criarOuAtualizarHobby(
                "Escrita",
                "Produção de textos, histórias, ideias ou reflexões.",
                0.0,
                3,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.intelectual(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(escrita, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(escrita, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(escrita, objetivos.aprender(), 2);
        adicionarObjetivoAoHobby(escrita, objetivos.relaxar(), 1);
        adicionarInteresseAoHobby(escrita, interesses.literatura(), 3);
        adicionarInteresseAoHobby(escrita, interesses.criacao(), 3);
        adicionarInteresseAoHobby(escrita, interesses.cultura(), 1);


        Hobby idiomas = criarOuAtualizarHobby(
                "Estudo de idiomas",
                "Aprendizado e prática de uma nova língua.",
                50.0,
                4,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.intelectual(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(idiomas, objetivos.aprender(), 3);
        adicionarObjetivoAoHobby(idiomas, objetivos.disciplina(), 2);
        adicionarObjetivoAoHobby(idiomas, objetivos.conhecerPessoas(), 1);
        adicionarObjetivoAoHobby(idiomas, objetivos.produzir(), 1);
        adicionarInteresseAoHobby(idiomas, interesses.aprendizado(), 3);
        adicionarInteresseAoHobby(idiomas, interesses.cultura(), 2);
    }

    private void seedSocial(
            Categorias categorias,
            Interesses interesses,
            Objetivos objetivos
    ) {

        Hobby teatro = criarOuAtualizarHobby(
                "Teatro",
                "Atividade artística em grupo voltada à expressão e interpretação.",
                30.0,
                4,
                2.0,
                TipoSocializacao.SOCIAL,
                categorias.social(),
                NivelAtividadeFisica.MODERADO,
                AmbientePreferido.AMBIENTE_FECHADO,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(teatro, objetivos.conhecerPessoas(), 3);
        adicionarObjetivoAoHobby(teatro, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(teatro, objetivos.produzir(), 2);
        adicionarObjetivoAoHobby(teatro, objetivos.aprender(), 2);
        adicionarInteresseAoHobby(teatro, interesses.cultura(), 3);
        adicionarInteresseAoHobby(teatro, interesses.criacao(), 2);
        adicionarInteresseAoHobby(teatro, interesses.socializacao(), 3);


        Hobby danca = criarOuAtualizarHobby(
                "Dança",
                "Atividade corporal, musical e social.",
                50.0,
                3,
                1.5,
                TipoSocializacao.SOCIAL,
                categorias.social(),
                NivelAtividadeFisica.ALTO,
                AmbientePreferido.AMBIENTE_FECHADO,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(danca, objetivos.diversao(), 3);
        adicionarObjetivoAoHobby(danca, objetivos.conhecerPessoas(), 3);
        adicionarObjetivoAoHobby(danca, objetivos.condicionamento(), 2);
        adicionarObjetivoAoHobby(danca, objetivos.criatividade(), 2);
        adicionarInteresseAoHobby(danca, interesses.musica(), 2);
        adicionarInteresseAoHobby(danca, interesses.cultura(), 2);
        adicionarInteresseAoHobby(danca, interesses.socializacao(), 3);


        Hobby voluntariado = criarOuAtualizarHobby(
                "Voluntariado",
                "Participação em ações sociais e comunitárias.",
                0.0,
                2,
                2.0,
                TipoSocializacao.SOCIAL,
                categorias.social(),
                NivelAtividadeFisica.MODERADO,
                AmbientePreferido.INDIFERENTE,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(voluntariado, objetivos.conhecerPessoas(), 3);
        adicionarObjetivoAoHobby(voluntariado, objetivos.aprender(), 2);
        adicionarObjetivoAoHobby(voluntariado, objetivos.disciplina(), 1);
        adicionarInteresseAoHobby(voluntariado, interesses.socializacao(), 3);
        adicionarInteresseAoHobby(voluntariado, interesses.aprendizado(), 1);


        Hobby clubeJogos = criarOuAtualizarHobby(
                "Clube de jogos",
                "Encontros para jogos de tabuleiro, cartas e jogos sociais.",
                20.0,
                2,
                2.0,
                TipoSocializacao.SOCIAL,
                categorias.social(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.AMBIENTE_FECHADO,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(clubeJogos, objetivos.conhecerPessoas(), 3);
        adicionarObjetivoAoHobby(clubeJogos, objetivos.diversao(), 3);
        adicionarObjetivoAoHobby(clubeJogos, objetivos.competir(), 2);
        adicionarInteresseAoHobby(clubeJogos, interesses.jogos(), 3);
        adicionarInteresseAoHobby(clubeJogos, interesses.socializacao(), 3);
    }

    private void seedTecnologia(
            Categorias categorias,
            Interesses interesses,
            Objetivos objetivos
    ) {

        Hobby programacaoCriativa = criarOuAtualizarHobby(
                "Programação criativa",
                "Criação de pequenos projetos, automações e experimentos utilizando programação.",
                0.0,
                4,
                1.5,
                TipoSocializacao.INDIVIDUAL,
                categorias.tecnologia(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.REMOTO
        );

        adicionarObjetivoAoHobby(programacaoCriativa, objetivos.aprender(), 3);
        adicionarObjetivoAoHobby(programacaoCriativa, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(programacaoCriativa, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(programacaoCriativa, objetivos.disciplina(), 1);
        adicionarInteresseAoHobby(programacaoCriativa, interesses.tecnologia(), 3);
        adicionarInteresseAoHobby(programacaoCriativa, interesses.criacao(), 3);
        adicionarInteresseAoHobby(programacaoCriativa, interesses.aprendizado(), 2);


        Hobby edicaoVideo = criarOuAtualizarHobby(
                "Edição de vídeo",
                "Produção e edição de conteúdos audiovisuais.",
                0.0,
                3,
                1.5,
                TipoSocializacao.INDIVIDUAL,
                categorias.tecnologia(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.REMOTO
        );

        adicionarObjetivoAoHobby(edicaoVideo, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(edicaoVideo, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(edicaoVideo, objetivos.aprender(), 2);
        adicionarInteresseAoHobby(edicaoVideo, interesses.tecnologia(), 2);
        adicionarInteresseAoHobby(edicaoVideo, interesses.artesVisuais(), 3);
        adicionarInteresseAoHobby(edicaoVideo, interesses.criacao(), 3);


        Hobby robotica = criarOuAtualizarHobby(
                "Robótica básica",
                "Montagem e programação de pequenos circuitos e dispositivos.",
                150.0,
                5,
                2.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.tecnologia(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(robotica, objetivos.aprender(), 3);
        adicionarObjetivoAoHobby(robotica, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(robotica, objetivos.criatividade(), 2);
        adicionarObjetivoAoHobby(robotica, objetivos.disciplina(), 2);
        adicionarInteresseAoHobby(robotica, interesses.tecnologia(), 3);
        adicionarInteresseAoHobby(robotica, interesses.aprendizado(), 3);
        adicionarInteresseAoHobby(robotica, interesses.criacao(), 2);


        Hobby criacaoJogos = criarOuAtualizarHobby(
                "Criação de jogos",
                "Desenvolvimento de jogos simples e interativos.",
                0.0,
                4,
                2.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.tecnologia(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.REMOTO
        );

        adicionarObjetivoAoHobby(criacaoJogos, objetivos.criatividade(), 3);
        adicionarObjetivoAoHobby(criacaoJogos, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(criacaoJogos, objetivos.aprender(), 3);
        adicionarObjetivoAoHobby(criacaoJogos, objetivos.diversao(), 2);
        adicionarInteresseAoHobby(criacaoJogos, interesses.tecnologia(), 3);
        adicionarInteresseAoHobby(criacaoJogos, interesses.jogos(), 3);
        adicionarInteresseAoHobby(criacaoJogos, interesses.criacao(), 3);
    }

    private void seedRelaxamento(
            Categorias categorias,
            Interesses interesses,
            Objetivos objetivos
    ) {

        Hobby meditacao = criarOuAtualizarHobby(
                "Meditação",
                "Prática de atenção plena, respiração e relaxamento.",
                0.0,
                1,
                0.5,
                TipoSocializacao.INDIVIDUAL,
                categorias.relaxamento(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(meditacao, objetivos.relaxar(), 3);
        adicionarObjetivoAoHobby(meditacao, objetivos.reduzirEstresse(), 3);
        adicionarObjetivoAoHobby(meditacao, objetivos.disciplina(), 1);
        adicionarInteresseAoHobby(meditacao, interesses.relaxar(), 3);


        Hobby jardinagem = criarOuAtualizarHobby(
                "Jardinagem",
                "Cultivo e cuidado de plantas, flores e pequenos jardins.",
                40.0,
                2,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.relaxamento(),
                NivelAtividadeFisica.MODERADO,
                AmbientePreferido.AO_AR_LIVRE,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(jardinagem, objetivos.relaxar(), 3);
        adicionarObjetivoAoHobby(jardinagem, objetivos.produzir(), 2);
        adicionarObjetivoAoHobby(jardinagem, objetivos.reduzirEstresse(), 2);
        adicionarObjetivoAoHobby(jardinagem, objetivos.aprender(), 1);
        adicionarInteresseAoHobby(jardinagem, interesses.natureza(), 3);
        adicionarInteresseAoHobby(jardinagem, interesses.relaxar(), 2);
        adicionarInteresseAoHobby(jardinagem, interesses.criacao(), 1);


        Hobby culinaria = criarOuAtualizarHobby(
                "Culinária",
                "Preparação de receitas e experimentação gastronômica.",
                50.0,
                3,
                1.5,
                TipoSocializacao.INDIVIDUAL,
                categorias.relaxamento(),
                NivelAtividadeFisica.BAIXO,
                AmbientePreferido.CASA,
                FormatoPreferido.HIBRIDO
        );

        adicionarObjetivoAoHobby(culinaria, objetivos.produzir(), 3);
        adicionarObjetivoAoHobby(culinaria, objetivos.criatividade(), 2);
        adicionarObjetivoAoHobby(culinaria, objetivos.aprender(), 2);
        adicionarObjetivoAoHobby(culinaria, objetivos.diversao(), 2);
        adicionarObjetivoAoHobby(culinaria, objetivos.relaxar(), 1);
        adicionarInteresseAoHobby(culinaria, interesses.culinaria(), 3);
        adicionarInteresseAoHobby(culinaria, interesses.criacao(), 2);
        adicionarInteresseAoHobby(culinaria, interesses.aprendizado(), 1);


        Hobby caminhada = criarOuAtualizarHobby(
                "Caminhada",
                "Atividade física leve praticada principalmente ao ar livre.",
                0.0,
                1,
                1.0,
                TipoSocializacao.INDIVIDUAL,
                categorias.relaxamento(),
                NivelAtividadeFisica.MODERADO,
                AmbientePreferido.AO_AR_LIVRE,
                FormatoPreferido.PRESENCIAL
        );

        adicionarObjetivoAoHobby(caminhada, objetivos.relaxar(), 3);
        adicionarObjetivoAoHobby(caminhada, objetivos.reduzirEstresse(), 3);
        adicionarObjetivoAoHobby(caminhada, objetivos.condicionamento(), 2);
        adicionarInteresseAoHobby(caminhada, interesses.relaxar(), 3);
    }

}
