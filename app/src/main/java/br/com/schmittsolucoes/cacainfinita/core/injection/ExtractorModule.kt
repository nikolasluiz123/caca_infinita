package br.com.schmittsolucoes.cacainfinita.core.injection

import br.com.schmittsolucoes.cacainfinita.data.extractor.image.ImageTextExtractor
import br.com.schmittsolucoes.cacainfinita.data.extractor.image.MLKitTextRecognition
import br.com.schmittsolucoes.cacainfinita.data.extractor.pdf.PDFBoxTextExtractor
import br.com.schmittsolucoes.cacainfinita.data.extractor.pdf.PDFTextExtractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExtractorModule {

    @Binds
    abstract fun bindImageTextExtractor(impl: MLKitTextRecognition): ImageTextExtractor

    @Binds
    abstract fun bindPDFTextExtractor(impl: PDFBoxTextExtractor): PDFTextExtractor
}
