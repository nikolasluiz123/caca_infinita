package br.com.schmittsolucoes.cacainfinita.data.repository

import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toDomain
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toEntity
import br.com.schmittsolucoes.cacainfinita.domain.model.User
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource
): UserRepository {
    override suspend fun save(user: User) {
        userLocalDataSource.upsert(listOf(user.toEntity()))
    }

    override fun getFirstObservable(calculateMaxLevelXP: (Long) -> Long): Flow<User?> {
        return userLocalDataSource.selectFirstObservable().map {
            it?.toDomain(calculateMaxLevelXP(it.level))
        }
    }

    override suspend fun getUser(calculateMaxLevelXP: (Long) -> Long): User {
        val entity = userLocalDataSource.selectFirst()
        return entity.toDomain(calculateMaxLevelXP(entity.level))
    }

    override suspend fun getExistsUser(): Boolean {
        return userLocalDataSource.selectExistsUser()
    }
}
