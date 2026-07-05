package br.com.schmittsolucoes.cacainfinita.data.database.access.user

import br.com.schmittsolucoes.cacainfinita.data.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource : EntityLocalDataSource<UserEntity> {
    fun selectFirstObservable(): Flow<UserEntity>
    fun selectFirst(): UserEntity
    suspend fun selectExistsUser(): Boolean
}
