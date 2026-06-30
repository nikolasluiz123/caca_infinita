package br.com.schmittsolucoes.cacasobmedida.data.processor.input

import br.com.schmittsolucoes.cacasobmedida.data.extractor.image.ImageTextExtractor
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

import javax.inject.Inject

class ImageInputProcessor @Inject constructor(
    private val extractor: ImageTextExtractor
): InputProcessor<File> {

    override suspend fun process(input: File): String = withContext(Dispatchers.IO) {
        val result = extractor.recognizeText(input)
        result.text
    }
}