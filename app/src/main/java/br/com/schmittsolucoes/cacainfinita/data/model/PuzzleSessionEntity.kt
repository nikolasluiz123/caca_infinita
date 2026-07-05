package br.com.schmittsolucoes.cacainfinita.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity(
    tableName = "puzzle_session",
    foreignKeys = [
        ForeignKey(
            entity = WordSearchPuzzleEntity::class,
            parentColumns = ["id"],
            childColumns = ["puzzle_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PuzzleSessionEntity(
    @PrimaryKey
    override val id: String = Uuid.random().toString(),
    @ColumnInfo(name = "puzzle_id", index = true)
    val puzzleId: String,
    @ColumnInfo(name = "started_at")
    val startedAt: Instant,
    @ColumnInfo(name = "ended_at")
    val endedAt: Instant? = null
) : UniqueEntity
