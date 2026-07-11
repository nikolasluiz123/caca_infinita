package br.com.schmittsolucoes.cacainfinita.data.calculator

import br.com.schmittsolucoes.cacainfinita.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacainfinita.domain.model.GridCalculationParams
import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions
import br.com.schmittsolucoes.cacainfinita.domain.model.PaddingParams
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import javax.inject.Inject
import kotlin.math.floor

class AndroidGridDimensionCalculator @Inject constructor() : GridDimensionCalculator {

    override fun calculate(params: GridCalculationParams, padding: PaddingParams): GridDimensions {
        val isLandscapeRequested = params.orientation == GridOrientation.LANDSCAPE
        val isCurrentLandscape = params.availableWidthDp > params.availableHeightDp

        val (calcWidth, calcHeight) = if (isLandscapeRequested != isCurrentLandscape) {
            params.availableHeightDp to params.availableWidthDp
        } else {
            params.availableWidthDp to params.availableHeightDp
        }

        val calcPaddingHorizontal = padding.paddingStartDp + padding.paddingEndDp
        val calcPaddingVertical = padding.paddingTopDp + padding.paddingBottomDp

        val columns = floor((calcWidth - calcPaddingHorizontal) / params.cellTargetSizeDp).toInt()
        val rows = floor((calcHeight - calcPaddingVertical) / params.cellTargetSizeDp).toInt()

        return GridDimensions(
            rows = rows.coerceAtLeast(1),
            columns = columns.coerceAtLeast(1),
            orientation = params.orientation
        )
    }
}