package br.com.schmittsolucoes.cacainfinita.domain.calculator

import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions

interface GridDimensionCalculator {
    fun calculate(
        availableWidthDp: Float,
        availableHeightDp: Float,
        cellTargetSizeDp: Float,
        paddingStartDp: Float,
        paddingEndDp: Float,
        paddingTopDp: Float,
        paddingBottomDp: Float
    ): GridDimensions
}