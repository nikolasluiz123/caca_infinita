package br.com.schmittsolucoes.cacasobmedida.data.calculator

import br.com.schmittsolucoes.cacasobmedida.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions

import javax.inject.Inject

class AndroidGridDimensionCalculator @Inject constructor(): GridDimensionCalculator {
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