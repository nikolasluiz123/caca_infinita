package br.com.schmittsolucoes.cacasobmedida.core.database.transaction

interface DatabaseTransaction {
    suspend fun <T> run(block: suspend () -> T): T
}