package br.com.schmittsolucoes.cacainfinita.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity(
    tableName = "word_search_puzzle",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordSearchPuzzleEntity(
    @PrimaryKey
    override val id: String = Uuid.random().toString(),
    val grid: String,
    val name: String,
    val rows: Int,
    val columns: Int,
    @ColumnInfo("user_id", index = true)
    val userId: String
): UniqueEntity
