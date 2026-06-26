package br.com.schmittsolucoes.cacasobmedida.core.injection

import android.content.Context
import androidx.room.Room
import br.com.schmittsolucoes.cacasobmedida.data.database.AppDatabase
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.WordSearchPuzzleLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.session.PuzzleSessionLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.database.access.puzzle.word.WordLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacasobmedida.data.database.transaction.RoomDatabaseTransaction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataAccessModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "caca_sob_medida.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseTransaction(db: AppDatabase): DatabaseTransaction {
        return RoomDatabaseTransaction(db)
    }

    @Provides
    fun provideUserLocalDataSource(db: AppDatabase): UserLocalDataSource {
        return db.userDAO()
    }

    @Provides
    fun provideWordSearchPuzzleLocalDataSource(db: AppDatabase): WordSearchPuzzleLocalDataSource {
        return db.wordSearchPuzzleDAO()
    }

    @Provides
    fun provideWordLocalDataSource(db: AppDatabase): WordLocalDataSource {
        return db.wordDAO()
    }

    @Provides
    fun providePuzzleSessionLocalDataSource(db: AppDatabase): PuzzleSessionLocalDataSource {
        return db.puzzleSessionDAO()
    }
}
