package dev.jamal.projetotcc.config;
import dev.jamal.projetotcc.Enum.TipoSocializacao;

import dev.jamal.projetotcc.Entities.Hobby;
import dev.jamal.projetotcc.Entities.HobbyCategory;
import dev.jamal.projetotcc.Repository.HobbyCategoryRepository;
import dev.jamal.projetotcc.Repository.HobbyRepository;
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

    @Bean
    CommandLineRunner seedDatabase(){
        return args -> {

            // -----------------------------------> CATEGORIAS <-----------------------------------------
            HobbyCategory esporte = criarCategoriaSeNaoExistir("Esporte");
            HobbyCategory criatividade = criarCategoriaSeNaoExistir("Criatividade");
            HobbyCategory intelectual = criarCategoriaSeNaoExistir("Intelectual");
            HobbyCategory social = criarCategoriaSeNaoExistir("Social");
            HobbyCategory tecnologia = criarCategoriaSeNaoExistir("Tecnologia");
            HobbyCategory relaxamento = criarCategoriaSeNaoExistir("Relaxamento");

            // -------------------------------------> HOBBIES <-------------------------------------------
            criarOuAtualizarHobby("Corrida", "Atividade física ao ar livre.", 0.0, 2, 1.0, TipoSocializacao.INDIVIDUAL, esporte);
            criarOuAtualizarHobby("Futebol", "Esporte coletivo praticado em grupo.", 20.0, 3, 2.0, TipoSocializacao.SOCIAL, esporte);
            criarOuAtualizarHobby("Ciclismo", "Prática de pedalar por lazer ou exercício.", 200.0, 3, 2.0, TipoSocializacao.INDIVIDUAL, esporte);
            criarOuAtualizarHobby("Natação", "Atividade física em piscina.", 80.0, 3, 1.0, TipoSocializacao.INDIVIDUAL, esporte);

            criarOuAtualizarHobby("Fotografia", "Registrar momentos, paisagens e cenas criativas.", 50.0, 3, 1.5, TipoSocializacao.INDIVIDUAL, criatividade);
            criarOuAtualizarHobby("Desenho", "Prática artística com lápis, papel ou mesa digital.", 20.0, 2, 1.0, TipoSocializacao.INDIVIDUAL, criatividade);
            criarOuAtualizarHobby("Pintura", "Expressão artística usando tintas e telas.", 60.0, 3, 1.5, TipoSocializacao.INDIVIDUAL, criatividade);
            criarOuAtualizarHobby("Violão", "Aprendizado musical com instrumento de cordas.", 150.0, 4, 1.0, TipoSocializacao.INDIVIDUAL, criatividade);

            criarOuAtualizarHobby("Xadrez", "Jogo estratégico que estimula raciocínio lógico.", 0.0, 4, 1.0, TipoSocializacao.INDIVIDUAL, intelectual);
            criarOuAtualizarHobby("Leitura", "Hábito de ler livros, artigos ou textos diversos.", 30.0, 2, 1.0, TipoSocializacao.INDIVIDUAL, intelectual);
            criarOuAtualizarHobby("Escrita", "Produção de textos, histórias ou reflexões.", 0.0, 3, 1.0, TipoSocializacao.INDIVIDUAL, intelectual);
            criarOuAtualizarHobby("Estudo de idiomas", "Aprendizado de uma nova língua.", 50.0, 4, 1.0, TipoSocializacao.INDIVIDUAL, intelectual);

            criarOuAtualizarHobby("Teatro", "Atividade artística em grupo voltada à expressão corporal.", 30.0, 4, 2.0, TipoSocializacao.SOCIAL, social);
            criarOuAtualizarHobby("Dança", "Atividade corporal, rítmica e social.", 50.0, 3, 1.5, TipoSocializacao.SOCIAL, social);
            criarOuAtualizarHobby("Voluntariado", "Participação em ações sociais e comunitárias.", 0.0, 2, 2.0, TipoSocializacao.SOCIAL, social);
            criarOuAtualizarHobby("Clube de jogos", "Encontro para jogos de tabuleiro ou cartas.", 20.0, 2, 2.0, TipoSocializacao.SOCIAL, social);

            criarOuAtualizarHobby("Programação criativa", "Criar pequenos projetos, jogos ou automações.", 0.0, 4, 1.5, TipoSocializacao.INDIVIDUAL, tecnologia);
            criarOuAtualizarHobby("Edição de vídeo", "Produção e edição de conteúdos audiovisuais.", 0.0, 3, 1.5, TipoSocializacao.INDIVIDUAL, tecnologia);
            criarOuAtualizarHobby("Robótica básica", "Montagem e programação de pequenos circuitos.", 150.0, 5, 2.0, TipoSocializacao.INDIVIDUAL, tecnologia);
            criarOuAtualizarHobby("Criação de jogos", "Desenvolvimento de jogos simples e interativos.", 0.0, 4, 2.0, TipoSocializacao.INDIVIDUAL, tecnologia);

            criarOuAtualizarHobby("Meditação", "Prática de atenção plena e relaxamento.", 0.0, 1, 0.5, TipoSocializacao.INDIVIDUAL, relaxamento);
            criarOuAtualizarHobby("Jardinagem", "Cuidado com plantas e pequenos jardins.", 40.0, 2, 1.0, TipoSocializacao.INDIVIDUAL, relaxamento);
            criarOuAtualizarHobby("Culinária", "Preparação de receitas e experimentação gastronômica.", 50.0, 3, 1.5, TipoSocializacao.INDIVIDUAL, relaxamento);
            criarOuAtualizarHobby("Caminhada", "Atividade leve ao ar livre.", 0.0, 1, 1.0, TipoSocializacao.INDIVIDUAL, relaxamento);

            System.out.println("Seed de hobbies finalizado");
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


    private void criarOuAtualizarHobby(String nome, String descricao, Double custoEstimado, Integer nivelDificuldade,
    Double tempoNecessario, TipoSocializacao tipoSocializacao, HobbyCategory category
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
        hobby.setNivelAtividadeFisica(category.getNome().equalsIgnoreCase("Esporte") ? dev.jamal.projetotcc.Enum.NivelAtividadeFisica.MODERADO : dev.jamal.projetotcc.Enum.NivelAtividadeFisica.BAIXO);
        hobby.setAmbiente(category.getNome().equalsIgnoreCase("Esporte") ? dev.jamal.projetotcc.Enum.AmbientePreferido.AO_AR_LIVRE : dev.jamal.projetotcc.Enum.AmbientePreferido.CASA);
        hobby.setFormato(dev.jamal.projetotcc.Enum.FormatoPreferido.HIBRIDO);
        hobby.setRequerEquipamento(hobby.getCustoEstimado() != null && hobby.getCustoEstimado() > 0);

        hobbyRepository.save(hobby);
    }

}
