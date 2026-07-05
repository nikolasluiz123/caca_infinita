package br.com.schmittsolucoes.cacainfinita.core.database.transaction

interface DatabaseTransaction {
    suspend fun <T> run(block: suspend () -> T): T
}