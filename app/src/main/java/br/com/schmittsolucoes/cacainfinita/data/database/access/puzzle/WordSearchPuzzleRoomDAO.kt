package br.com.schmittsolucoes.cacainfinita.data.database.access.puzzle

import androidx.room.Dao
import androidx.room.Query
import androidx.paging.PagingSource
import br.com.schmittsolucoes.cacainfinita.data.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.model.WordSearchPuzzleEntity
import br.com.schmittsolucoes.cacainfinita.data.model.WordSearchPuzzleWithStats
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface WordSearchPuzzleRoomDAO: WordSearchPuzzleLocalDataSource, RoomLocalDataSource<WordSearchPuzzleEntity> {

    @Query("""
        select *,
               (select count(id) from word where word.puzzle_id = word_search_puzzle.id) as wordsCount,
               (exists (select 1 from word where word.puzzle_id = word_search_puzzle.id and word.found_date is null)) as hasUnfinishedWords,
               (select group_concat(distinct language) from word where word.puzzle_id = word_search_puzzle.id) as languages
        from word_search_puzzle
        order by hasUnfinishedWords desc
    """)
    override fun selectAll(): PagingSource<Int, WordSearchPuzzleWithStats>

    @Query("select * from word_search_puzzle where id = :id")
    override suspend fun selectById(id: String): WordSearchPuzzleEntity

    @Query("""
        select puzzle.id 
        from word_search_puzzle puzzle
        inner join puzzle_session session on session.puzzle_id = puzzle.id
        where (
            select 1
            from word
            where word.puzzle_id = puzzle.id
            and word.found_date is null
        )
        group by puzzle.id
        order by session.started_at desc
        limit 1
    """)
    override fun selectLastUnfinished(): Flow<String?>

    @Query("""
        select puzzle.id
        from word_search_puzzle puzzle
        where not exists (
            select 1 from puzzle_session where puzzle_id = puzzle.id
        )
        limit 1
    """)
    override fun selectNextPuzzleToPlay(): Flow<String?>

    @Query("""
        select puzzle.id as id,
               puzzle.name as puzzleName,
               (
                    select count(word.id)
                    from word
                    where word.puzzle_id = puzzle.id
               ) as wordsCount,
               (
                    select sum(session.ended_at - session.started_at)
                    from puzzle_session session
                    where session.puzzle_id = puzzle.id 
                    and session.ended_at is not null
               ) as duration
        from word_search_puzzle puzzle
        where not exists (
            select 1
            from word
            where word.puzzle_id = puzzle.id
            and word.found_date is null
        )
        order by duration asc, wordsCount desc
        limit 3
    """)
    override fun selectRecords(): Flow<List<PuzzleRecord>>

    @Query("select count(*) from word_search_puzzle")
    override suspend fun selectCount(): Long
}