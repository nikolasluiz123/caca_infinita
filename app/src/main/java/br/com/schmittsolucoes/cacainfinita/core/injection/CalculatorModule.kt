package br.com.schmittsolucoes.cacainfinita.core.injection

import br.com.schmittsolucoes.cacainfinita.data.calculator.AndroidGridDimensionCalculator
import br.com.schmittsolucoes.cacainfinita.domain.calculator.GridDimensionCalculator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CalculatorModule {

    @Binds
    abstract fun bindGridDimensionCalculator(impl: AndroidGridDimensionCalculator): GridDimensionCalculator
}
