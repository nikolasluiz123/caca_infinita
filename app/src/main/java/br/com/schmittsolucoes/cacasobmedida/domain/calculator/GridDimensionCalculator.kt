package br.com.schmittsolucoes.cacasobmedida.domain.calculator

import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions

interface GridDimensionCalculator {
    fun calculate(
        availableWidthDp: Float,
        availableHeightDp: Float,
        cellTargetSizeDp: Float,
        horizontalPaddingDp: Float,
        verticalPaddingDp: Float
    ): GridDimensions
}