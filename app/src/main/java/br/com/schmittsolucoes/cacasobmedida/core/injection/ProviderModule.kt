package br.com.schmittsolucoes.cacasobmedida.core.injection

import br.com.schmittsolucoes.cacasobmedida.data.provider.AndroidDimensionsProvider
import br.com.schmittsolucoes.cacasobmedida.data.provider.AndroidFreeMemoryProvider
import br.com.schmittsolucoes.cacasobmedida.data.provider.FreeMemoryProvider
import br.com.schmittsolucoes.cacasobmedida.data.provider.LuceneStopWordsProvider
import br.com.schmittsolucoes.cacasobmedida.data.provider.StopWordsProvider
import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    abstract fun bindDeviceDimensionsProvider(impl: AndroidDimensionsProvider): DeviceDimensionsProvider

    @Binds
    abstract fun bindFreeMemoryProvider(impl: AndroidFreeMemoryProvider): FreeMemoryProvider

    @Binds
    abstract fun bindStopWordsProvider(impl: LuceneStopWordsProvider): StopWordsProvider
}
