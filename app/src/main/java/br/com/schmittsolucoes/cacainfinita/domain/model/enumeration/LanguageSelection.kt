package br.com.schmittsolucoes.cacainfinita.domain.model.enumeration

import kotlinx.serialization.Serializable

@Serializable
enum class LanguageSelection {
    PORTUGUESE_ONLY,
    ENGLISH_ONLY,
    BOTH
}