package br.com.schmittsolucoes.cacainfinita.data.processor.input

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.data.extractor.image.ImageTextExtractor
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoTextFoundException
import br.com.schmittsolucoes.cacainfinita.domain.processor.input.InputProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

import javax.inject.Inject

class ImageInputProcessor @Inject constructor(
    private val extractor: ImageTextExtractor
): InputProcessor<File> {

    override suspend fun process(input: File): String = withContext(Dispatchers.IO) {
        val tag = this@ImageInputProcessor::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando processamento de imagem: ${input.absolutePath}")

        val result = extractor.recognizeText(input)

        if (result.text.isBlank()) {
            throw NoTextFoundException()
        }

        result.text.also {
            Log.d("DEBUG_PROCESS", "$tag: Fim do processamento de imagem")
        }
    }
}