package br.com.schmittsolucoes.cacainfinita.domain.model

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation

data class GridDimensions(val rows: Int, val columns: Int, val orientation: GridOrientation)
