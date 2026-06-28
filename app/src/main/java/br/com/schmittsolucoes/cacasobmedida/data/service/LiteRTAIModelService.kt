package br.com.schmittsolucoes.cacasobmedida.data.service

import android.content.Context
import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.data.service.exceptions.AIModelServiceException
import br.com.schmittsolucoes.cacasobmedida.domain.service.AIModelService
import com.google.ai.edge.litertlm.Backend
import com.google.ai.edge.litertlm.Content.Text
import com.google.ai.edge.litertlm.Engine
import com.google.ai.edge.litertlm.EngineConfig
import com.google.ai.edge.litertlm.Message
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class LiteRTAIModelService @Inject constructor(
    @param:ApplicationContext private val context: Context
) : AIModelService {

    private var engine: Engine? = null
    private val modelPath = File(context.filesDir, "models/gemma3-1b-it-int4.litertlm").absolutePath

    override suspend fun generate(prompt: String): Result<String> {
        val tag = this@LiteRTAIModelService::class.simpleName

        Log.d(tag, "Iniciando geração de resposta com LiteRT")
        
        return runCatching {
            val currentEngine = engine ?: throw AIModelServiceException.EngineNotInitialized()
            val userMessage = Message.user(prompt)

            currentEngine.createConversation().use { conversation ->
                val responseBuilder = StringBuilder()
                val response = conversation.sendMessage(userMessage)

                response.contents.contents.forEach { content ->
                    if (content is Text) {
                        responseBuilder.append(content.text)
                    }
                }

                responseBuilder.toString().also {
                    Log.d(tag, "Resposta LiteRT gerada com sucesso")
                }
            }
        }.recoverCatching { 
            Log.e(tag, "Falha na inferência LiteRT: ${it.message}")
            throw AIModelServiceException.InferenceFailed(it) 
        }
    }

    override fun isReady(): Boolean {
        return engine != null
    }

    override suspend fun initialize(): Result<Unit> {
        val tag = this@LiteRTAIModelService::class.simpleName

        Log.d(tag, "Inicializando motor LiteRT")
        
        return runCatching {
            if (engine != null) return@runCatching

            val modelFile = File(modelPath)

            if (!modelFile.exists()) {
                throw AIModelServiceException.ModelFileNotFound(modelPath)
            }

            val config = EngineConfig(
                modelPath = modelPath,
                backend = Backend.GPU(),
                cacheDir = context.cacheDir.path,
                maxNumTokens = 8096
            )

            val newEngine = Engine(config)
            newEngine.initialize()
            engine = newEngine

            Log.d(tag, "Motor LiteRT inicializado com sucesso")
        }.recoverCatching { 
            Log.e(tag, "Falha na inicialização LiteRT: ${it.message}")
            throw AIModelServiceException.InitializationFailed(it) 
        }
    }

    override fun close() {
        val tag = this@LiteRTAIModelService::class.simpleName

        Log.d(tag, "Fechando motor LiteRT")

        engine?.close()
        engine = null
    }
}
