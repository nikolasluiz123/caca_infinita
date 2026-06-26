package br.com.schmittsolucoes.cacasobmedida.core.injection

import br.com.schmittsolucoes.cacasobmedida.data.generator.HeuristicPuzzleGenerator
import br.com.schmittsolucoes.cacasobmedida.data.generator.LLMPuzzleNameGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleNameGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GeneratorModule {

    @Binds
    abstract fun bindPuzzleGenerator(impl: HeuristicPuzzleGenerator): PuzzleGenerator

    @Binds
    abstract fun bindPuzzleNameGenerator(impl: LLMPuzzleNameGenerator): PuzzleNameGenerator
}
