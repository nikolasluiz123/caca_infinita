# Data - Diretrizes

## Acesso a Dados Locais
- **EntityLocalDataSource**: Padrão para acesso a entidades de persistência no dispositivo. Toda classe de acesso a banco de dados local deve implementar `EntityLocalDataSource`.
- **AssetsLocalDataSource**: Padrão para acesso a recursos estáticos armazenados na pasta `assets` (ex: prompts de IA, configurações JSON).
- **Room**: 
    - Interfaces DAO devem estender `RoomLocalDataSource<T>` para definir comportamentos padrão do Room.
    - Cada `EntityLocalDataSource` deve gerenciar uma única entidade (uso de Generics).
    - Novas DAOs devem ser registradas em `AppDatabase` e configuradas no `LocalDataAccessModule`.
- **Transações**: Utilizar `RoomDatabaseTransaction` para operações atômicas que envolvam múltiplos `LocalDataSources`.

## Modelagem (Entities)
- **Nomenclatura**: Sufixo `Entity` (ex: `UserEntity`).
- **Localização**: Pacote `data/model`.
- **Identificação**: Usar `String` (UUID). Implementar a interface `UniqueEntity` sempre que houver um ID.

## Repositories
- **Implementação**: Residem na camada `data` e implementam interfaces da camada `domain`.
- **Mapeamento**: O Repository é responsável por converter entidades para modelos de domínio (e vice-versa) via *extension functions* (Mappers).
