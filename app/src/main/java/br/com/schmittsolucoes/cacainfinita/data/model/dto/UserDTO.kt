package br.com.schmittsolucoes.cacainfinita.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: String,
    val experience: Long,
    val level: Long
)
