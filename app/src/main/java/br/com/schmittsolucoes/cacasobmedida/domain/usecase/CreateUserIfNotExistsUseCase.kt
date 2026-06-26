package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.model.User
import br.com.schmittsolucoes.cacasobmedida.domain.repository.UserRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateUserIfNotExistsUseCase(
    private val userRepository: UserRepository,
    private val getNextUserLevelUseCase: GetNextUserLevelUseCase
) {
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke() {
        if (!userRepository.getExistsUser()) {
            val level = 1L

            val defaultUser = User(
                id = Uuid.random().toString(),
                actualExperience = 0L,
                maxLevelExperience = getNextUserLevelUseCase(level),
                level = level
            )
            userRepository.save(defaultUser)
        }
    }
}
