package br.com.schmittsolucoes.cacainfinita.presentation.camera.navigation

import androidx.lifecycle.SavedStateHandle
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection

data class CameraNavArgs(val languageSelection: LanguageSelection, val orientation: GridOrientation) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        languageSelection = LanguageSelection.valueOf(
            savedStateHandle.get<String>(languageSelectionArg) ?: LanguageSelection.BOTH.name
        ),
        orientation = GridOrientation.valueOf(
            savedStateHandle.get<String>(orientationArg) ?: GridOrientation.PORTRAIT.name
        )
    )
}