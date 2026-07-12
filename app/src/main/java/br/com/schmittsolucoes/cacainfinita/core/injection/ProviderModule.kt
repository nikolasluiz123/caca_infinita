package br.com.schmittsolucoes.cacainfinita.core.injection

import br.com.schmittsolucoes.cacainfinita.data.provider.ActivityProvider
import br.com.schmittsolucoes.cacainfinita.data.provider.AndroidActivityProvider
import br.com.schmittsolucoes.cacainfinita.data.provider.AndroidDimensionsProvider
import br.com.schmittsolucoes.cacainfinita.data.provider.AndroidFreeMemoryProvider
import br.com.schmittsolucoes.cacainfinita.data.provider.AndroidLanguageProvider
import br.com.schmittsolucoes.cacainfinita.data.provider.AndroidOrientationProvider
import br.com.schmittsolucoes.cacainfinita.data.provider.FreeMemoryProvider
import br.com.schmittsolucoes.cacainfinita.data.provider.LuceneStopWordsProvider
import br.com.schmittsolucoes.cacainfinita.data.provider.StopWordsProvider
import br.com.schmittsolucoes.cacainfinita.domain.provider.DeviceDimensionsProvider
import br.com.schmittsolucoes.cacainfinita.domain.provider.LanguageProvider
import br.com.schmittsolucoes.cacainfinita.domain.provider.OrientationProvider
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

    @Binds
    abstract fun bindLanguageProvider(impl: AndroidLanguageProvider): LanguageProvider

    @Binds
    abstract fun bindOrientationProvider(impl: AndroidOrientationProvider): OrientationProvider

    @Binds
    abstract fun bindActivityProvider(impl: AndroidActivityProvider): ActivityProvider
}
