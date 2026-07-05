package br.com.schmittsolucoes.cacainfinita.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    override val id: String = Uuid.random().toString(),
    val experience: Long,
    val level: Long,
): UniqueEntity
