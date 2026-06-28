package br.com.schmittsolucoes.cacasobmedida.data.processor.input

import android.content.Context
import android.net.Uri
import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf.PDFExtractionConfig
import br.com.schmittsolucoes.cacasobmedida.data.extractor.pdf.PDFTextExtractor
import br.com.schmittsolucoes.cacasobmedida.data.processor.input.exceptions.PDFInputProcessorException
import br.com.schmittsolucoes.cacasobmedida.data.provider.FreeMemoryProvider
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PDFInputProcessor @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val freeMemoryProvider: FreeMemoryProvider,
    private val extractor: PDFTextExtractor
): InputProcessor<Uri> {

    override suspend fun process(input: Uri): String {
        val tag = this@PDFInputProcessor::class.simpleName
        Log.d(tag, "Iniciando processamento de PDF: $input")
        
        val inputStream = context.contentResolver.openInputStream(input) 
            ?: throw PDFInputProcessorException.CouldNotOpenStream(input)
            
        return inputStream.use { stream ->
            val availableMemory = freeMemoryProvider.getAvailableMemory()
            val memoryLimit = (availableMemory * MEMORY_USAGE_PERCENTAGE).toLong()
            
            Log.d(tag, "Memória disponível: $availableMemory bytes. Limite para PDFBox: $memoryLimit bytes.")
            
            val config = PDFExtractionConfig(maxMainMemoryBytes = memoryLimit)

            extractor.extract(stream, config).also {
                Log.d(tag, "Fim do processamento de PDF")
            }
        }
    }

    companion object {
        private const val MEMORY_USAGE_PERCENTAGE = 0.7
    }
}
