package br.com.schmittsolucoes.cacasobmedida.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.WordSearchPuzzleRoomDAO
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.session.PuzzleSessionRoomDAO
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.word.WordRoomDAO
import br.com.schmittsolucoes.cacasobmedida.data.database.access.user.UserRoomDAO
import br.com.schmittsolucoes.cacasobmedida.data.model.PuzzleSessionEntity
import br.com.schmittsolucoes.cacasobmedida.data.model.UserEntity
import br.com.schmittsolucoes.cacasobmedida.data.model.WordEntity
import br.com.schmittsolucoes.cacasobmedida.data.model.WordSearchPuzzleEntity
import br.com.schmittsolucoes.cacasobmedida.data.model.converters.DurationConverter
import br.com.schmittsolucoes.cacasobmedida.data.model.converters.InstantConverter

@Database(
    version = 1,
    entities = [
        WordSearchPuzzleEntity::class, WordEntity::class, UserEntity::class, PuzzleSessionEntity::class
    ],
    exportSchema = true
)
@TypeConverters(InstantConverter::class, DurationConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserRoomDAO
    abstract fun wordSearchPuzzleDAO(): WordSearchPuzzleRoomDAO
    abstract fun wordDAO(): WordRoomDAO
    abstract fun puzzleSessionDAO(): PuzzleSessionRoomDAO
}
