package br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf.exceptions

sealed class PDFTextExtractorException(message: String) : Exception(message) {
    data class ExtractionFailed(
        override val message: String = "Text extraction from PDF returned null"
    ) : PDFTextExtractorException(message)
}
