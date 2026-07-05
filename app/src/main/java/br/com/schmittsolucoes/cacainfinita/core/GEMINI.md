# Core - Diretrizes

## Injeção de Dependências (Hilt)
- **Módulos**: Organizados por conceito específico e refletindo a estrutura de pacotes.
- **Novas Classes**: Devem ser mapeadas para um módulo existente ou um novo módulo deve ser criado.
- **Escopo e Ciclo de Vida**:
    - `LocalDataAccessModule` e `RepositoryModule`: Devem ser sempre `@Singleton`.
    - **Outros Módulos**: Instanciação sob demanda por padrão. Avaliar necessidade de `@Singleton` ao criar novas classes/módulos.

## Banco de Dados
- **DatabaseTransaction**: Interface que abstrai transações de banco de dados, permitindo a execução de múltiplos DataSources de forma atômica fora da camada de dados.
