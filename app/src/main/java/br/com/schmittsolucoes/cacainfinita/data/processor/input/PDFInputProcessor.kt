package br.com.schmittsolucoes.cacainfinita.data.processor.input

import android.content.Context
import android.net.Uri
import android.util.Log
import br.com.schmittsolucoes.cacainfinita.data.extractor.pdf.PDFExtractionConfig
import br.com.schmittsolucoes.cacainfinita.data.extractor.pdf.PDFTextExtractor
import br.com.schmittsolucoes.cacainfinita.data.processor.input.exceptions.PDFInputProcessorException
import br.com.schmittsolucoes.cacainfinita.data.provider.FreeMemoryProvider
import br.com.schmittsolucoes.cacainfinita.domain.exception.NoTextFoundException
import br.com.schmittsolucoes.cacainfinita.domain.processor.input.InputProcessor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PDFInputProcessor @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val freeMemoryProvider: FreeMemoryProvider,
    private val extractor: PDFTextExtractor
): InputProcessor<Uri> {

    override suspend fun process(input: Uri): String = withContext(Dispatchers.IO) {
        val tag = this@PDFInputProcessor::class.simpleName
        Log.d("DEBUG_PROCESS", "$tag: Iniciando processamento de PDF: $input")
        
        val inputStream = context.contentResolver.openInputStream(input) 
            ?: throw PDFInputProcessorException.CouldNotOpenStream(input)

        inputStream.use { stream ->
            val availableMemory = freeMemoryProvider.getAvailableMemory()
            val memoryLimit = (availableMemory * MEMORY_USAGE_PERCENTAGE).toLong()

            Log.d("DEBUG_PROCESS", "$tag: Memória disponível: $availableMemory bytes. Limite para PDFBox: $memoryLimit bytes.")

            val config = PDFExtractionConfig(maxMainMemoryBytes = memoryLimit)

            val text = extractor.extract(stream, config)

            if (text.isBlank()) {
                throw NoTextFoundException()
            }

            text.also {
                Log.d("DEBUG_PROCESS", "$tag: Fim do processamento de PDF")
            }
        }
    }

    companion object {
        private const val MEMORY_USAGE_PERCENTAGE = 0.7
    }
}
