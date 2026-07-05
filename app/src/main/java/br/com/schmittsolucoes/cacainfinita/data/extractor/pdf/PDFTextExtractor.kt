package br.com.schmittsolucoes.cacainfinita.data.extractor.pdf

import java.io.InputStream

interface PDFTextExtractor {
    suspend fun extract(inputStream: InputStream, config: PDFExtractionConfig): String
}
