package dev.jamal.projetotcc.Service.AI;

import org.springframework.stereotype.Service;

@Service
public class OpenAIProvider implements AIprovider{


    @Override
    public String generate(String prompt) {
        return """
                                Plano inicial personalizado:
                
                                Dia 1:
                                Conheça os conceitos básicos da atividade.
                
                                Dia 2:
                                Organize os materiais necessários.
                
                                Dia 3:
                                Realize uma primeira prática de curta duração.
                
                                Dia 4:
                                Assista a um conteúdo introdutório.
                
                                Dia 5:
                                Repita a prática, prestando atenção às dificuldades.
                
                                Dia 6:
                                Revise o que aprendeu durante a semana.
                
                                Dia 7:
                                Avalie sua experiência e defina os próximos objetivos.
                """;
    }
}
