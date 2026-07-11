package br.com.schmittsolucoes.cacainfinita.domain.provider

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language

interface LanguageProvider {
    fun getSystemLanguage(): Language
}
