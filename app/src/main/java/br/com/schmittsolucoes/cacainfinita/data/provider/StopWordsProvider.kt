package br.com.schmittsolucoes.cacainfinita.data.provider

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection

/**
 * Interface que define o contrato para fornecimento de Stop Words.
 */
interface StopWordsProvider {
    /**
     * Retorna um conjunto de palavras consideradas irrelevantes para os idiomas selecionados.
     *
     * @param config A seleção de idiomas feita pelo usuário.
     * @return Um [Set] contendo as stop words em caixa alta para facilitar a comparação.
     */
    fun getStopWords(config: LanguageSelection): Set<String>
}
