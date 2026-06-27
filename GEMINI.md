# Caça Sob Medida - Guia de Arquitetura

Este documento centraliza as diretrizes de desenvolvimento do projeto, organizadas por camadas. Para detalhes sobre cada uma, acesse os arquivos específicos abaixo:

## Estrutura de Diretrizes
- [**Core**](./app/src/main/java/br/com/schmittsolucoes/cacasobmedida/core/GEMINI.md): Injeção de dependência (Hilt), módulos, transações de banco de dados e utilitários globais.
- [**Data**](./app/src/main/java/br/com/schmittsolucoes/cacasobmedida/data/GEMINI.md): Implementação de Repositories, EntityLocalDataSources (Room), Entities e Mappers.
- [**Domain**](./app/src/main/java/br/com/schmittsolucoes/cacasobmedida/domain/GEMINI.md): Modelos de domínio puros, interfaces de repositório e regras para UseCases.
- [**Presentation**](./app/src/main/java/br/com/schmittsolucoes/cacasobmedida/presentation/GEMINI.md): Organização de UI com Jetpack Compose, ViewModels, States, Navegação e Tematização (Dark/Light).