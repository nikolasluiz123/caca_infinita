package br.com.schmittsolucoes.cacasobmedida.data.analyzer

import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions
import com.google.mlkit.nl.languageid.LanguageIdentifier
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MLKitLanguageIdentifier @Inject constructor(): LanguageTextAnalyzer {
    private lateinit var client: LanguageIdentifier

    override fun initialize() {
        if (::client.isInitialized) return

        val options = LanguageIdentificationOptions.Builder()
            .setConfidenceThreshold(CONFIDENCE_PERCENTAGE)
            .build()

        client = LanguageIdentification.getClient(options)
    }

    override suspend fun analyze(text: String): String = suspendCancellableCoroutine { continuation ->
        client.identifyLanguage(text)
            .addOnSuccessListener { languageCode ->
                continuation.resume(languageCode)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    companion object {
        private const val CONFIDENCE_PERCENTAGE = 0.6f
    }
}