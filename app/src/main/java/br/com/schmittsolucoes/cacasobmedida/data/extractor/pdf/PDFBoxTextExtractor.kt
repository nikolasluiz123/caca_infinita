package br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf

import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf.exceptions.PDFTextExtractorException
import com.tom_roush.pdfbox.io.MemoryUsageSetting
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.InputStream
import javax.inject.Inject

class PDFBoxTextExtractor @Inject constructor(): PDFTextExtractor {

    override suspend fun extract(inputStream: InputStream, config: PDFExtractionConfig): String {
        val tag = this@PDFBoxTextExtractor::class.simpleName
        Log.d(tag, "Iniciando extração de texto do PDF")
        
        val setting = MemoryUsageSetting.setupMixed(config.maxMainMemoryBytes)
        return PDDocument.load(inputStream, setting).use { document ->
            val stripper = PDFTextStripper().apply {
                sortByPosition = false
            }

            stripper.getText(document)?.also {
                Log.d(tag, "Extração de texto do PDF concluída")
            } ?: throw PDFTextExtractorException.ExtractionFailed()
        }
    }
}
