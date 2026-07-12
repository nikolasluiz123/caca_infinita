package br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.transaction

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import br.com.schmittsolucoes.cacainfinita.core.database.transaction.DatabaseTransaction

class RoomDatabaseTransaction(private val db: RoomDatabase): DatabaseTransaction {
    override suspend fun <T> run(block: suspend () -> T): T {
        return db.withTransaction(block)
    }
}