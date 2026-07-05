package br.com.schmittsolucoes.cacainfinita.core.injection

import br.com.schmittsolucoes.cacainfinita.data.repository.PuzzleSessionRepositoryImpl
import br.com.schmittsolucoes.cacainfinita.data.repository.UserRepositoryImpl
import br.com.schmittsolucoes.cacainfinita.data.repository.WordRepositoryImpl
import br.com.schmittsolucoes.cacainfinita.data.repository.WordSearchPuzzleRepositoryImpl
import br.com.schmittsolucoes.cacainfinita.domain.repository.PuzzleSessionRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindWordRepository(impl: WordRepositoryImpl): WordRepository

    @Binds
    @Singleton
    abstract fun bindWordSearchPuzzleRepository(impl: WordSearchPuzzleRepositoryImpl): WordSearchPuzzleRepository

    @Binds
    @Singleton
    abstract fun bindPuzzleSessionRepository(impl: PuzzleSessionRepositoryImpl): PuzzleSessionRepository

}
