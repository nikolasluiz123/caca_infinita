package br.com.schmittsolucoes.cacainfinita.data.processor.input.exceptions

import android.net.Uri

sealed class PDFInputProcessorException(message: String) : Exception(message) {
    data class CouldNotOpenStream(
        val uri: Uri
    ) : PDFInputProcessorException("Could not open input stream for URI: $uri")
}
