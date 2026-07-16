package br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE word ADD COLUMN language TEXT NOT NULL DEFAULT 'OTHER'")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE word_search_puzzle ADD COLUMN orientation TEXT NOT NULL DEFAULT 'PORTRAIT'")
            db.execSQL("UPDATE word_search_puzzle SET orientation = 'LANDSCAPE' WHERE columns > rows")
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE user ADD COLUMN puzzlesCompleted INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE user ADD COLUMN totalWordsFound INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE user ADD COLUMN fastestFirstWordMs INTEGER")
        }
    }

    fun getAll(): Array<Migration> = arrayOf(
        MIGRATION_1_2,
        MIGRATION_2_3,
        MIGRATION_3_4
    )
}
