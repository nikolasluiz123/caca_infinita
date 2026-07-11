package br.com.schmittsolucoes.cacainfinita.domain.calculator

import br.com.schmittsolucoes.cacainfinita.domain.model.GridCalculationParams
import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions
import br.com.schmittsolucoes.cacainfinita.domain.model.PaddingParams

interface GridDimensionCalculator {
    fun calculate(params: GridCalculationParams, padding: PaddingParams): GridDimensions
}