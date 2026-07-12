package br.com.schmittsolucoes.cacainfinita.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class CloudSaveDTO(
    val user: UserDTO,
    val puzzles: List<FullPuzzleDTO>
)
