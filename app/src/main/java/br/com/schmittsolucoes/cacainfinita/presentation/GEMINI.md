# Presentation - Diretrizes

## Organização de Telas
As telas devem ser organizadas em pacotes por conceito (ex: `home`), contendo:
- **navigation/**: Arquivos de definição de rota e navegação.
- **composables/**: O arquivo principal da tela e subdivisões.
    - **components/**: Componentes menores e reaproveitáveis específicos da tela.
- **Raiz do pacote**: `ViewModel` e `State` (ex: `HomeViewModel.kt`, `HomeUiState.kt`).

### Fluxo de Navegação
- O `NavController` deve ser utilizado exclusivamente dentro do `AppNavHost.kt`.
- As telas devem expor callbacks para eventos de navegação, que devem ser repassados através dos arquivos em `navigation/` até o `AppNavHost.kt`, onde a ação de navegação será executada.
- **Navegação Type-Safe**: É obrigatório o uso do padrão Type-Safe do Navigation 2.8+.
    - Cada tela deve ter um arquivo `XyzRoute.kt` contendo um `data class` ou `object` anotado com `@Serializable`.
    - Os argumentos devem ser definidos como propriedades dessa classe.
    - No ViewModel, os argumentos devem ser recuperados via `savedStateHandle.toRoute<XyzRoute>()`.
    - Enums passados como argumentos também devem ser anotados com `@Serializable`.

## Tematização e Estilo
- **Suporte a Dark/Light Mode**: Obrigatório. Utilizar cores dinâmicas baseadas no tema em `Theme.kt` e `Color.kt`.
- **Internacionalização**: Obrigatório criar labels em Português (`values`) e Inglês (`values-en`) para todas as novas UIs. Proibido o uso de strings hardcoded.
- **Tipografia**: Preferir estilos padrão do Material Design (`MaterialTheme.typography`). Criar estilos específicos apenas se estritamente necessário.
- **Composables**: Subdividir telas grandes em pequenas partes para facilitar a manutenção e o reaproveitamento.
