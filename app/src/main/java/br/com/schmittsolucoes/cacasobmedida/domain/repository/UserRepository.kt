package br.com.schmittsolucoes.cacasobmedida.domain.repository

import br.com.schmittsolucoes.cacasobmedida.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun save(user: User)
    suspend fun getUser(calculateMaxLevelXP: (Long) -> Long): User
    fun getFirstObservable(calculateMaxLevelXP: (Long) -> Long): Flow<User>
    suspend fun getExistsUser(): Boolean
}