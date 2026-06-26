package br.com.schmittsolucoes.cacasobmedida.core.injection

import android.net.Uri
import br.com.schmittsolucoes.cacasobmedida.data.processor.input.ImageInputProcessor
import br.com.schmittsolucoes.cacasobmedida.data.processor.input.PDFInputProcessor
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.ImageTextProcessorPipeline
import br.com.schmittsolucoes.cacasobmedida.data.processor.pipeline.PDFTextProcessorPipeline
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor
import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
abstract class ProcessorModule {

    @Binds
    @ImageProcessor
    abstract fun bindImageInputProcessor(impl: ImageInputProcessor): InputProcessor<File>

    @Binds
    @PDFProcessor
    abstract fun bindPDFInputProcessor(impl: PDFInputProcessor): InputProcessor<Uri>

    @Binds
    @ImageProcessor
    abstract fun bindImageTextProcessorPipeline(impl: ImageTextProcessorPipeline): TextProcessorPipeline

    @Binds
    @PDFProcessor
    abstract fun bindPDFTextProcessorPipeline(impl: PDFTextProcessorPipeline): TextProcessorPipeline
}
