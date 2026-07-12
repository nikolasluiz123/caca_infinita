package br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert

interface RoomLocalDataSource<T>: EntityLocalDataSource<T> {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insert(entity: List<T>)

    @Update
    override suspend fun update(entity: List<T>)

    @Delete
    override suspend fun delete(entity: List<T>)

    @Upsert
    override suspend fun upsert(entity: List<T>)
}
