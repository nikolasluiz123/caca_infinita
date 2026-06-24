package br.com.schmittsolucoes.cacasobmedida.data.extractor.image

import br.com.schmittsolucoes.cacasobmedida.domain.model.result.text.TextResult
import java.io.File

class MLKitTextRecognition: ImageTextExtractor {

    override suspend fun recognizeText(image: File): TextResult {
        TODO("Not yet implemented")
    }
}