# Presentation - Diretrizes

## Organização de Telas
As telas devem ser organizadas em pacotes por conceito (ex: `home`), contendo:
- **navigation/**: Arquivos de definição de rota e navegação.
- **composables/**: O arquivo principal da tela e subdivisões.
    - **components/**: Componentes menores e reaproveitáveis específicos da tela.
- **Raiz do pacote**: `ViewModel` e `State` (ex: `HomeViewModel.kt`, `HomeUiState.kt`).

## Tematização e Estilo
- **Suporte a Dark/Light Mode**: Obrigatório. Utilizar cores dinâmicas baseadas no tema em `Theme.kt` e `Color.kt`.
- **Tipografia**: Preferir estilos padrão do Material Design (`MaterialTheme.typography`). Criar estilos específicos apenas se estritamente necessário.
- **Composables**: Subdividir telas grandes em pequenas partes para facilitar a manutenção e o reaproveitamento.
