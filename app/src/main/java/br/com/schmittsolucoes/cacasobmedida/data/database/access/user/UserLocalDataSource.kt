package br.com.schmittsolucoes.cacasobmedida.data.database.access.user

import br.com.schmittsolucoes.cacasobmedida.data.database.access.LocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource : LocalDataSource<UserEntity> {
    fun selectFirstObservable(): Flow<UserEntity>
    fun selectFirst(): UserEntity
    suspend fun selectExistsUser(): Boolean
}
