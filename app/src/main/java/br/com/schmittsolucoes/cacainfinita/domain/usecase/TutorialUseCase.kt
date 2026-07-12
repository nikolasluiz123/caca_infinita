package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.TutorialInfoLocalDataSource
import br.com.schmittsolucoes.cacainfinita.domain.manager.TutorialManager
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseStep
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TutorialUseCase @Inject constructor(
    private val tutorialInfoLocalDataSource: TutorialInfoLocalDataSource,
    private val tutorialManager: TutorialManager
) {
    suspend fun checkAndStartTutorial(tutorialId: String, steps: List<ShowcaseStep>) {
        tutorialManager.currentSteps.first { it == null }
        val wasShown = tutorialInfoLocalDataSource.isTutorialShown(tutorialId).first()

        if (!wasShown) {
            tutorialManager.startTutorial(steps)
            tutorialManager.currentSteps.first { it == null }
            markTutorialAsShown(tutorialId)
        }
    }

    suspend fun markTutorialAsShown(tutorialId: String) {
        tutorialInfoLocalDataSource.markTutorialAsShown(tutorialId)
    }
}
