# Domain - Diretrizes

## Modelagem (Domain Models)
- **Modelos**: Classes de tipos puros, sem dependências de frameworks, localizadas no pacote `domain/model`.
- **Uso**: Utilizadas em UseCases e expostas para a camada de apresentação.

## Repositories
- **Interfaces**: Apenas definições de interfaces de repositório são permitidas nesta camada.
- **Implementação**: Devem ser implementadas exclusivamente na camada `data`.

## UseCases
- **Responsabilidade Única**: Devem ter, preferencialmente, um objetivo singular.
- **Múltiplas Funções**: Permitidas apenas se mantiverem a coesão do propósito (ex: `GetCountWordsUseCase` com funções para contar totais e encontradas) e se a complexidade for baixa (ex: chamadas diretas ao repositório).
