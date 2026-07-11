package br.com.schmittsolucoes.cacainfinita.data.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE word ADD COLUMN language TEXT NOT NULL DEFAULT 'OTHER'")
        }
    }

    fun getAll(): Array<Migration> = arrayOf(
        MIGRATION_1_2
    )
}
