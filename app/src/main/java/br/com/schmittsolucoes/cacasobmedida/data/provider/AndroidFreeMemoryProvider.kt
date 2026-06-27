package br.com.schmittsolucoes.cacasobmedida.data.provider

import javax.inject.Inject

class AndroidFreeMemoryProvider @Inject constructor() : FreeMemoryProvider {
    override fun getAvailableMemory(): Long {
        val runtime = Runtime.getRuntime()
        val usedMemory = runtime.totalMemory() - runtime.freeMemory()
        val maxMemory = runtime.maxMemory()
        return maxMemory - usedMemory
    }
}
