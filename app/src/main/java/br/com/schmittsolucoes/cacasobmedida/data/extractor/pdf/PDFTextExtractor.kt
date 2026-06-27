package br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf

import java.io.InputStream

interface PDFTextExtractor {
    suspend fun extract(inputStream: InputStream, config: PDFExtractionConfig): String
}
