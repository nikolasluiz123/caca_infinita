package br.com.schmittsolucoes.cacasobmedida.data.database.access.user

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.cacasobmedida.data.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRoomDAO: UserLocalDataSource, RoomLocalDataSource<UserEntity> {

    @Query("select * from user limit 1")
    override fun selectFirstObservable(): Flow<UserEntity>

    @Query("select * from user limit 1")
    override fun selectFirst(): UserEntity

    @Query("SELECT EXISTS(SELECT 1 FROM user)")
    override suspend fun selectExistsUser(): Boolean
}