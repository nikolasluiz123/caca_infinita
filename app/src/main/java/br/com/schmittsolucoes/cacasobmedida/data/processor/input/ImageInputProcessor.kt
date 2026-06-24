package br.com.schmittsolucoes.cacasobmedida.data.processor.input

import br.com.schmittsolucoes.cacasobmedida.data.extractor.image.ImageTextExtractor
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor
import java.io.File

class ImageInputProcessor(
    private val extractor: ImageTextExtractor
): InputProcessor<File> {

    override suspend fun process(input: File): String {
        TODO("Not yet implemented")
    }
}