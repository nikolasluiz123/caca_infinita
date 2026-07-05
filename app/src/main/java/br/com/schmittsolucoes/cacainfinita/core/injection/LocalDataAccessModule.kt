package br.com.schmittsolucoes.cacainfinita.core.injection

import android.content.Context
import androidx.room.Room
import br.com.schmittsolucoes.cacainfinita.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacainfinita.data.database.AppDatabase
import br.com.schmittsolucoes.cacainfinita.data.database.access.assets.AndroidAssetsLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.database.access.assets.AssetsLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.database.access.puzzle.WordSearchPuzzleLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.database.access.puzzle.session.PuzzleSessionLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.database.access.puzzle.word.WordLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.database.transaction.RoomDatabaseTransaction
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

    @Provides
    @Singleton
    fun provideAssetsLocalDataSource(impl: AndroidAssetsLocalDataSource): AssetsLocalDataSource {
        return impl
    }
}
