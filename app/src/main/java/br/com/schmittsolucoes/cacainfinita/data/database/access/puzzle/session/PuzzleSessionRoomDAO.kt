package br.com.schmittsolucoes.cacainfinita.data.database.access.puzzle.session

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.cacainfinita.data.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.model.PuzzleSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PuzzleSessionRoomDAO: PuzzleSessionLocalDataSource, RoomLocalDataSource<PuzzleSessionEntity> {

    @Query("select * from puzzle_session where puzzle_id = :puzzleId")
    override fun selectAllBy(puzzleId: String): Flow<List<PuzzleSessionEntity>>

    @Query("""
        select * 
        from puzzle_session 
        where puzzle_id = :puzzleId
        and ended_at is null
        order by started_at desc 
        limit 1
    """)
    override suspend fun selectActualSessionBy(puzzleId: String): PuzzleSessionEntity?
}