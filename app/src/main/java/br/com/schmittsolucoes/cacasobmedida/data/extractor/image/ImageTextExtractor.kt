package br.com.schmittsolucoes.cacasobmedida.data.extractor.image

import br.com.schmittsolucoes.cacasobmedida.domain.model.result.text.TextResult
import java.io.File

interface ImageTextExtractor {
    suspend fun recognizeText(image: File): TextResult
}