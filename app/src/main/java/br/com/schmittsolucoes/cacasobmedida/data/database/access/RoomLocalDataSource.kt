package br.com.schmittsolucoes.cacasobmedida.data.database.access

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert

interface RoomLocalDataSource<T>: LocalDataSource<T> {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insert(entity: List<T>)

    @Update
    override suspend fun update(entity: List<T>)

    @Delete
    override suspend fun delete(entity: List<T>)

    @Upsert
    override suspend fun upsert(entity: List<T>)
}
