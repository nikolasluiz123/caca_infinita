package br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf

import br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf.exceptions.PDFTextExtractorException
import com.tom_roush.pdfbox.io.MemoryUsageSetting
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.InputStream
import javax.inject.Inject

class PDFBoxTextExtractor @Inject constructor(): PDFTextExtractor {

    override suspend fun extract(inputStream: InputStream, config: PDFExtractionConfig): String {
        val setting = MemoryUsageSetting.setupMixed(config.maxMainMemoryBytes)
        return PDDocument.load(inputStream, setting).use { document ->
            val stripper = PDFTextStripper().apply {
                sortByPosition = false
            }

            stripper.getText(document) ?: throw PDFTextExtractorException.ExtractionFailed()
        }
    }
}
