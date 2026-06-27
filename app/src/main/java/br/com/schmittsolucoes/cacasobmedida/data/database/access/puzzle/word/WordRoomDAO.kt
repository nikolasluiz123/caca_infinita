package br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.word

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.cacasobmedida.data.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordRoomDAO: WordLocalDataSource, RoomLocalDataSource<WordEntity> {

    @Query("select * from word where puzzle_id = :puzzleId")
    override suspend fun selectAllBy(puzzleId: String): List<WordEntity>

    @Query("select count(id) from word where puzzle_id = :puzzleId")
    override suspend fun selectCountWords(puzzleId: String): Long

    @Query("select count(id) from word where puzzle_id = :puzzleId and found_date is not null")
    override fun selectCountFoundWordsObservable(puzzleId: String): Flow<Long>

    @Query("select count(id) from word where puzzle_id = :puzzleId")
    override fun selectCountWordsObservable(puzzleId: String): Flow<Long>
}