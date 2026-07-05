package br.com.schmittsolucoes.cacainfinita.data.extractor.image

import br.com.schmittsolucoes.cacainfinita.domain.model.result.text.TextResult
import java.io.File

interface ImageTextExtractor {
    suspend fun recognizeText(image: File): TextResult
}