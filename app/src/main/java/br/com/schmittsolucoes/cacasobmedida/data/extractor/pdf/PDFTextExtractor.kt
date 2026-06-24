package br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf

import android.net.Uri

interface PDFTextExtractor {
    suspend fun extract(fileUri: Uri): String
}