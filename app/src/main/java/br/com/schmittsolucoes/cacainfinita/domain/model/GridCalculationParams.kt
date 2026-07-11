package br.com.schmittsolucoes.cacainfinita.domain.model

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation

data class GridCalculationParams(
    val availableWidthDp: Float,
    val availableHeightDp: Float,
    val cellTargetSizeDp: Float,
    val orientation: GridOrientation
)

