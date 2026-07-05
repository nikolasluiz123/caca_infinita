package br.com.schmittsolucoes.cacainfinita.data.manager

import android.content.Context
import br.com.schmittsolucoes.cacainfinita.domain.manager.PDFTextExtractorManager
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import javax.inject.Inject

class PDFBoxTextExtractorManager @Inject constructor(): PDFTextExtractorManager {

    override fun initialize(context: Context) {
        PDFBoxResourceLoader.init(context)
    }
}
