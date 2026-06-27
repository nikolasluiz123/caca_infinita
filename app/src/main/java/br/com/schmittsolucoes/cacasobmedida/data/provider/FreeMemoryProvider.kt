package br.com.schmittsolucoes.cacasobmedida.data.provider

interface FreeMemoryProvider {
    fun getAvailableMemory(): Long
}
