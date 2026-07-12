package br.com.schmittsolucoes.cacainfinita.data.model.dto

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import kotlinx.serialization.Serializable

@Serializable
data class WordSearchPuzzleDTO(
    val id: String,
    val grid: String,
    val name: String,
    val rows: Int,
    val columns: Int,
    val orientation: GridOrientation,
    val userId: String
)
