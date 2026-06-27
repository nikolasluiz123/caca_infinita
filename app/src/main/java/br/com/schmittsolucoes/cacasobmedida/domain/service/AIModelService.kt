package br.com.schmittsolucoes.cacasobmedida.domain.service

interface AIModelService {
    suspend fun generate(prompt: String): Result<String>
    fun isReady(): Boolean
    suspend fun initialize(): Result<Unit>
    fun close()
}
