package br.com.schmittsolucoes.cacasobmedida.core.injection

import br.com.schmittsolucoes.cacasobmedida.data.analyzer.frame.FrameAnalyzer
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.language.LanguageTextAnalyzer
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.language.MLKitLanguageIdentifier
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.frame.MLKitTextFrameAnalyzer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyzerModule {

    @Binds
    abstract fun bindFrameAnalyzer(impl: MLKitTextFrameAnalyzer): FrameAnalyzer

    @Binds
    abstract fun bindLanguageAnalyzer(impl: MLKitLanguageIdentifier): LanguageTextAnalyzer
}
