package br.com.schmittsolucoes.cacasobmedida.data.database.transaction

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import br.com.schmittsolucoes.cacasobmedida.core.database.transaction.DatabaseTransaction

class RoomDatabaseTransaction(private val db: RoomDatabase): DatabaseTransaction {
    override suspend fun <T> run(block: suspend () -> T): T {
        return db.withTransaction(block)
    }
}