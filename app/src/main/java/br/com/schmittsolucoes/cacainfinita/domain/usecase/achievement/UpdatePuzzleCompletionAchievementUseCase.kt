package br.com.schmittsolucoes.cacainfinita.domain.usecase.achievement

import br.com.schmittsolucoes.cacainfinita.domain.manager.AchievementsManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.AppReviewManager
import br.com.schmittsolucoes.cacainfinita.domain.model.achievement.PuzzleCompletionAchievement
import br.com.schmittsolucoes.cacainfinita.domain.model.achievement.PuzzleCompletionAchievement.JourneyBegins
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetUserUseCase

class UpdatePuzzleCompletionAchievementUseCase(
    private val getUserUseCase: GetUserUseCase,
    private val userRepository: UserRepository,
    private val achievementsManager: AchievementsManager,
    private val appReviewManager: AppReviewManager
) {
    suspend operator fun invoke() {
        val user = getUserUseCase.first()
        val newPuzzlesCompleted = user.puzzlesCompleted + 1
        
        userRepository.save(user.copy(puzzlesCompleted = newPuzzlesCompleted))

        if (newPuzzlesCompleted == 1L) {
            achievementsManager.unlockAchievement(JourneyBegins)
            appReviewManager.triggerReviewRequest()
        }

        PuzzleCompletionAchievement.Incremental.entries().forEach { achievement ->
            if (newPuzzlesCompleted <= achievement.threshold) {
                achievementsManager.incrementAchievement(achievement, 1)

                if (newPuzzlesCompleted == achievement.threshold.toLong()) {
                    appReviewManager.triggerReviewRequest()
                }
            }
        }
    }
}
