package br.com.schmittsolucoes.cacasobmedida.core.injection

import androidx.camera.core.ImageProxy
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.FrameAnalyzer
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.LanguageTextAnalyzer
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.MLKitLanguageIdentifier
import br.com.schmittsolucoes.cacasobmedida.data.analyzer.MLKitTextFrameAnalyzer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyzerModule {

    @Binds
    abstract fun bindFrameAnalyzer(impl: MLKitTextFrameAnalyzer): FrameAnalyzer<ImageProxy>

    @Binds
    abstract fun bindLanguageAnalyzer(impl: MLKitLanguageIdentifier): LanguageTextAnalyzer
}
