package br.com.schmittsolucoes.cacainfinita.core.injection

import br.com.schmittsolucoes.cacainfinita.data.generator.HeuristicPuzzleGenerator
import br.com.schmittsolucoes.cacainfinita.domain.generator.PuzzleGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GeneratorModule {

    @Binds
    abstract fun bindPuzzleGenerator(impl: HeuristicPuzzleGenerator): PuzzleGenerator

}
