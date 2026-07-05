package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository

/**
 * Caso de uso responsável por atualizar a experiência e o nível do usuário após encontrar uma palavra.
 *
 * A lógica segue os seguintes passos:
 * 1. Recupera o estado atual do usuário.
 * 2. Calcula a experiência ganha pela palavra encontrada através do [GetUserExperienceByFoundWordUseCase].
 * 3. Soma a experiência ganha à experiência atual do usuário.
 * 4. Verifica se o usuário atingiu o limite para subir de nível através de um laço de repetição (permitindo múltiplos "level ups" de uma vez).
 * 5. Se subir de nível, calcula a nova meta de experiência necessária para o próximo nível usando [GetNextUserLevelUseCase].
 * 6. Persiste os dados atualizados do usuário no repositório.
 */
class UpdateUserExperienceUseCase(
    private val getUserUseCase: GetUserUseCase,
    private val userRepository: UserRepository,
    private val getUserExperienceByFoundWordUseCase: GetUserExperienceByFoundWordUseCase,
    private val getNextUserLevelUseCase: GetNextUserLevelUseCase
) {
    /**
     * Executa a atualização de progresso do usuário.
     *
     * @param word A palavra que foi encontrada e que gerará a recompensa de XP.
     * @return O total de XP ganho.
     */
    suspend operator fun invoke(word: Word): Long {
        val user = getUserUseCase.first()
        val xpGained = getUserExperienceByFoundWordUseCase(word)
        
        var newExperience = user.actualExperience + xpGained
        var newLevel = user.level
        var maxLevelExperience = user.maxLevelExperience
        
        while (newExperience >= maxLevelExperience) {
            newExperience -= maxLevelExperience
            newLevel++
            maxLevelExperience = getNextUserLevelUseCase(newLevel)
        }
        
        val updatedUser = user.copy(
            actualExperience = newExperience,
            level = newLevel,
            maxLevelExperience = maxLevelExperience
        )
        
        userRepository.save(updatedUser)

        return xpGained
    }
}
