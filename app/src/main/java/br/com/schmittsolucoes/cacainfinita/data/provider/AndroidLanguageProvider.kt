package br.com.schmittsolucoes.cacainfinita.data.provider

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language
import br.com.schmittsolucoes.cacainfinita.domain.provider.LanguageProvider
import java.util.Locale
import javax.inject.Inject

class AndroidLanguageProvider @Inject constructor() : LanguageProvider {
    override fun getSystemLanguage(): Language {
        return when (Locale.getDefault().language) {
            "pt" -> Language.PORTUGUESE
            "en" -> Language.ENGLISH
            else -> Language.OTHER
        }
    }
}
