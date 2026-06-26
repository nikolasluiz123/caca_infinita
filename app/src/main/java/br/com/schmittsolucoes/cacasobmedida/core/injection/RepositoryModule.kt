package br.com.schmittsolucoes.cacasobmedida.core.injection

import br.com.schmittsolucoes.cacasobmedida.data.repository.PuzzleSessionRepositoryImpl
import br.com.schmittsolucoes.cacasobmedida.data.repository.UserRepositoryImpl
import br.com.schmittsolucoes.cacasobmedida.data.repository.WordRepositoryImpl
import br.com.schmittsolucoes.cacasobmedida.data.repository.WordSearchPuzzleRepositoryImpl
import br.com.schmittsolucoes.cacasobmedida.domain.repository.PuzzleSessionRepository
import br.com.schmittsolucoes.cacasobmedida.domain.repository.UserRepository
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordRepository
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
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
