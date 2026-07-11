package br.com.schmittsolucoes.cacainfinita.presentation.camera.navigation

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection
import kotlinx.serialization.Serializable

@Serializable
data class CameraRoute(
    val languageSelection: LanguageSelection,
    val orientation: GridOrientation
)
