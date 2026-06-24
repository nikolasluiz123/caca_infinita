package br.com.schmittsolucoes.cacasobmedida.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.Direction
import java.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity(
    tableName = "word",
    foreignKeys = [
        ForeignKey(
            entity = WordSearchPuzzleEntity::class,
            parentColumns = ["id"],
            childColumns = ["puzzle_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WordEntity(
    @PrimaryKey
    override val id: String = Uuid.random().toString(),
    @ColumnInfo(name = "puzzle_id", index = true)
    val puzzleId: String,
    val text: String,
    @ColumnInfo(name = "start_row")
    val startRow: Int,
    @ColumnInfo(name = "start_col")
    val startCol: Int,
    val direction: Direction,
    @ColumnInfo(name = "found_date")
    val foundDate: Instant? = null
) : UniqueEntity
