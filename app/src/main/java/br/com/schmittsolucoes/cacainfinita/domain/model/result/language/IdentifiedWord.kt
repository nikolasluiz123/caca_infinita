package br.com.schmittsolucoes.cacainfinita.domain.model.result.language

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language

data class IdentifiedWord(
    val text: String,
    val language: Language
)
