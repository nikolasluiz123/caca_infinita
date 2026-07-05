package br.com.schmittsolucoes.cacainfinita.data.extractor.pdf

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.data.extractor.pdf.exceptions.PDFTextExtractorException
import com.tom_roush.pdfbox.io.MemoryUsageSetting
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.InputStream
import javax.inject.Inject

class PDFBoxTextExtractor @Inject constructor(): PDFTextExtractor {

    override suspend fun extract(inputStream: InputStream, config: PDFExtractionConfig): String {
        val tag = this@PDFBoxTextExtractor::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando extração de texto do PDF")
        
        val setting = MemoryUsageSetting.setupMixed(config.maxMainMemoryBytes)
        return PDDocument.load(inputStream, setting).use { document ->
            val stripper = PDFTextStripper().apply {
                sortByPosition = false
            }

            stripper.getText(document)?.also {
                Log.d("DEBUG_PROCESS", "$tag: Extração de texto do PDF concluída")
            } ?: throw PDFTextExtractorException.ExtractionFailed()
        }
    }
}
