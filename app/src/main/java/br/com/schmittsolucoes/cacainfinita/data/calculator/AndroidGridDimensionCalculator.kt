package br.com.schmittsolucoes.cacainfinita.data.calculator

import br.com.schmittsolucoes.cacainfinita.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions
import javax.inject.Inject
import kotlin.math.floor

class AndroidGridDimensionCalculator @Inject constructor(): GridDimensionCalculator {
    override fun calculate(
        availableWidthDp: Float,
        availableHeightDp: Float,
        cellTargetSizeDp: Float,
        paddingStartDp: Float,
        paddingEndDp: Float,
        paddingTopDp: Float,
        paddingBottomDp: Float
    ): GridDimensions {
        val columns = floor((availableWidthDp - paddingStartDp - paddingEndDp) / cellTargetSizeDp).toInt()
        val rows = floor((availableHeightDp - paddingTopDp - paddingBottomDp) / cellTargetSizeDp).toInt()

        return GridDimensions(
            rows = rows.coerceAtLeast(1),
            columns = columns.coerceAtLeast(1)
        )
    }
}