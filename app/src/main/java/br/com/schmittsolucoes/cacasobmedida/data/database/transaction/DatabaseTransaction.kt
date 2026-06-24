package br.com.schmittsolucoes.cacasobmedida.data.database.transaction

interface DatabaseTransaction {
    suspend fun <T> run(block: suspend () -> T): T
}