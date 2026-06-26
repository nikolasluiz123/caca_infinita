package br.com.schmittsolucoes.cacasobmedida.data.processor.input

import android.net.Uri
import br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf.PDFTextExtractor
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor

import javax.inject.Inject

class PDFInputProcessor @Inject constructor(
    private val extractor: PDFTextExtractor
): InputProcessor<Uri> {

    override suspend fun process(input: Uri): String {
        TODO("Not yet implemented")
    }
}