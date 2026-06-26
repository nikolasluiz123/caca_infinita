package br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf

import android.net.Uri

import javax.inject.Inject

class PDFBoxTextExtractor @Inject constructor(): PDFTextExtractor {

    override suspend fun extract(fileUri: Uri): String {
        TODO("Not yet implemented")
    }
}