package br.com.schmittsolucoes.cacasobmedida.core.injection

import br.com.schmittsolucoes.cacasobmedida.data.service.LiteRTAIModelService
import br.com.schmittsolucoes.cacasobmedida.domain.service.AIModelService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AIModule {

    @Binds
    @Singleton
    abstract fun bindAIModelService(impl: LiteRTAIModelService): AIModelService
}
