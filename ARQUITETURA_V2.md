# Arquitetura V2 — Descoberta de Hobbies

## Visão definitiva
O produto funciona como uma experiência de descoberta por cards: cadastro, login, questionário inicial, perfil de recomendação, ranking de hobbies e interação progressiva.

## Decisões aplicadas nesta entrega
- O projeto atual foi reaproveitado.
- `User` agora armazena data de nascimento; idade deve ser calculada.
- `RecommendationProfile` tornou-se o perfil de recomendação criado pelo onboarding.
- `Hobby` ganhou características comparáveis ao perfil.
- O algoritmo foi dividido em critérios independentes.
- A recomendação retorna score, motivos e alertas.
- O frontend ganhou cadastro real e questionário em etapas.

## Próximas camadas
1. Entidades `Objective`, `UserObjective` e `HobbyObjective`.
2. Relacionamento explícito entre hobbies e interesses.
3. Interações dos cards: curtir, descartar, salvar e abrir detalhes.
4. Persistência de recomendações e planos iniciais.
5. Geração de plano por IA com cache.
6. Testes unitários por critério e testes de integração.

## Migração de banco
Como campos obrigatórios foram adicionados, recomenda-se criar uma migration Flyway antes de usar em produção. Em ambiente local de desenvolvimento, um banco novo evita dados antigos incompatíveis.
