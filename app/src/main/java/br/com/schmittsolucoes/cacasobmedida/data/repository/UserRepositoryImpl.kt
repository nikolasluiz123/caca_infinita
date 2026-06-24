package br.com.schmittsolucoes.cacasobmedida.data.repository

import br.com.schmittsolucoes.cacasobmedida.data.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.data.repository.mapper.toDomain
import br.com.schmittsolucoes.cacasobmedida.data.repository.mapper.toEntity
import br.com.schmittsolucoes.cacasobmedida.domain.model.User
import br.com.schmittsolucoes.cacasobmedida.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource
): UserRepository {
    override suspend fun save(user: User) {
        userLocalDataSource.upsert(listOf(user.toEntity()))
    }

    override fun getFirstObservable(calculateMaxLevelXP: (Long) -> Long): Flow<User> {
        return userLocalDataSource.selectFirstObservable().map {
            it.toDomain(calculateMaxLevelXP(it.level))
        }
    }

    override suspend fun getUser(calculateMaxLevelXP: (Long) -> Long): User {
        val entity = userLocalDataSource.selectFirst()
        return entity.toDomain(calculateMaxLevelXP(entity.level))
    }
}
