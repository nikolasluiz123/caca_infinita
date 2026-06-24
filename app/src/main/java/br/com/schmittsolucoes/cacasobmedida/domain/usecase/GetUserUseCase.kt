package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.model.User
import br.com.schmittsolucoes.cacasobmedida.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    private val userRepository: UserRepository,
    private val getNextUserLevelUseCase: GetNextUserLevelUseCase
) {
    fun observable(): Flow<User> {
        return userRepository.getFirstObservable { getNextUserLevelUseCase(it) }
    }

    suspend fun first(): User {
        return userRepository.getUser { getNextUserLevelUseCase(it) }
    }
}
