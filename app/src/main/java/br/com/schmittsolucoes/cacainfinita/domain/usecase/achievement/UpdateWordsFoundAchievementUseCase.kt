package br.com.schmittsolucoes.cacainfinita.domain.usecase.achievement

import br.com.schmittsolucoes.cacainfinita.domain.manager.AchievementsManager
import br.com.schmittsolucoes.cacainfinita.domain.model.achievement.WordFoundAchievement
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetUserUseCase

class UpdateWordsFoundAchievementUseCase(
    private val getUserUseCase: GetUserUseCase,
    private val userRepository: UserRepository,
    private val achievementsManager: AchievementsManager
) {
    suspend operator fun invoke() {
        val user = getUserUseCase.first()
        val newTotalWordsFound = user.totalWordsFound + 1
        
        userRepository.save(user.copy(totalWordsFound = newTotalWordsFound))

        WordFoundAchievement.Incremental.entries().forEach { achievement ->
            if (newTotalWordsFound <= achievement.threshold) {
                achievementsManager.incrementAchievement(achievement, 1)
            }
        }
    }
}
