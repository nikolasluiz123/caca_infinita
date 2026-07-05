package br.com.schmittsolucoes.cacainfinita.data.provider

interface FreeMemoryProvider {
    fun getAvailableMemory(): Long
}
