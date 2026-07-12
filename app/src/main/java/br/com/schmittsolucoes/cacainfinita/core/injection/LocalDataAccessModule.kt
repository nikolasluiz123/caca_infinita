package br.com.schmittsolucoes.cacainfinita.core.injection

import android.content.Context
import androidx.room.Room
import br.com.schmittsolucoes.cacainfinita.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.AppDatabase
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.DataStoreTutorialInfoLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.TutorialInfoLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.assets.AndroidAssetsLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.assets.AssetsLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.puzzle.WordSearchPuzzleLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.puzzle.session.PuzzleSessionLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.puzzle.word.WordLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.migration.DatabaseMigrations
import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.transaction.RoomDatabaseTransaction
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
        )
        .addMigrations(*DatabaseMigrations.getAll())
        .build()
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

    @Provides
    @Singleton
    fun provideTutorialInfoLocalDataSource(impl: DataStoreTutorialInfoLocalDataSource): TutorialInfoLocalDataSource {
        return impl
    }
}
