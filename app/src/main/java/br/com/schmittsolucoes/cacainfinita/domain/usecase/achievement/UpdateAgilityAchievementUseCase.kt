package br.com.schmittsolucoes.cacainfinita.domain.usecase.achievement

import br.com.schmittsolucoes.cacainfinita.domain.manager.AchievementsManager
import br.com.schmittsolucoes.cacainfinita.domain.model.achievement.AgilityAchievement
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordRepository
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetUserUseCase

class UpdateAgilityAchievementUseCase(
    private val getUserUseCase: GetUserUseCase,
    private val userRepository: UserRepository,
    private val wordRepository: WordRepository,
    private val achievementsManager: AchievementsManager
) {
    suspend operator fun invoke(puzzleId: String, elapsedTimeMs: Long) {
        val foundWordsCount = wordRepository.getCountFoundWords(puzzleId)
        
        if (foundWordsCount == 1L) {
            val user = getUserUseCase.first()

            AgilityAchievement.Normal.entries().forEach { achievement ->
                if (elapsedTimeMs < achievement.thresholdMs) {
                    achievementsManager.unlockAchievement(achievement)
                }
            }

            if (user.fastestFirstWordMs == null || elapsedTimeMs < user.fastestFirstWordMs) {
                userRepository.save(user.copy(fastestFirstWordMs = elapsedTimeMs))
            }
        }
    }
}
