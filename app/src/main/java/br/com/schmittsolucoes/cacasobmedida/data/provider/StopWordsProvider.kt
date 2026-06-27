package br.com.schmittsolucoes.cacasobmedida.data.provider

/**
 * Interface que define o contrato para fornecimento de Stop Words.
 *
 * **O que são Stop Words?**
 * São palavras que, embora gramaticalmente necessárias para a construção de frases (como artigos,
 * preposições, conjunções e alguns pronomes), não agregam valor semântico significativo para a
 * geração de um caça-palavras. Exemplos: "de", "para", "com", "uma", "os".
 */
interface StopWordsProvider {
    /**
     * Retorna um conjunto de palavras consideradas irrelevantes para o idioma especificado.
     *
     * @param language O código do idioma (ex: "pt", "en").
     * @return Um [Set] contendo as stop words em caixa alta para facilitar a comparação.
     */
    fun getStopWords(language: String): Set<String>
}
