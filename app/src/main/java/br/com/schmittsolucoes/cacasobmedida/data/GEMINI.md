# Data - Diretrizes

## Acesso a Dados Locais
- **LocalDataSource**: Padrão para acesso a dados no dispositivo. Toda classe de acesso local deve implementar `LocalDataSource`.
- **Room**: 
    - Interfaces DAO devem estender `RoomLocalDataSource<T>` para definir comportamentos padrão do Room.
    - Cada `LocalDataSource` deve gerenciar uma única entidade (uso de Generics).
    - Novas DAOs devem ser registradas em `AppDatabase` e configuradas no `LocalDataAccessModule`.
- **Transações**: Utilizar `RoomDatabaseTransaction` para operações atômicas que envolvam múltiplos `LocalDataSources`.

## Modelagem (Entities)
- **Nomenclatura**: Sufixo `Entity` (ex: `UserEntity`).
- **Localização**: Pacote `data/model`.
- **Identificação**: Usar `String` (UUID). Implementar a interface `UniqueEntity` sempre que houver um ID.

## Repositories
- **Implementação**: Residem na camada `data` e implementam interfaces da camada `domain`.
- **Mapeamento**: O Repository é responsável por converter entidades para modelos de domínio (e vice-versa) via *extension functions* (Mappers).
