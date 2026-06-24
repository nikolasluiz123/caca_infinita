package br.com.schmittsolucoes.cacasobmedida.data.calculator

import br.com.schmittsolucoes.cacasobmedida.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions

class AndroidGridDimensionCalculator: GridDimensionCalculator {
    override fun calculate(
        availableWidthDp: Float,
        availableHeightDp: Float,
        cellTargetSizeDp: Float,
        horizontalPaddingDp: Float,
        verticalPaddingDp: Float
    ): GridDimensions {
        TODO("Not yet implemented")
    }
}