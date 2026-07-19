package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access.TutorialInfoLocalDataSource
import br.com.schmittsolucoes.cacainfinita.domain.manager.TutorialManager
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseStep
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TutorialUseCase @Inject constructor(
    private val tutorialInfoLocalDataSource: TutorialInfoLocalDataSource,
    private val tutorialManager: TutorialManager
) {
    suspend fun checkAndStartTutorial(steps: List<ShowcaseStep>) {
        tutorialManager.currentSteps.first { it == null }

        val stepsToShow = steps.filter { step ->
            !tutorialInfoLocalDataSource.isShowcaseShown(step.targetId).first()
        }

        if (stepsToShow.isNotEmpty()) {
            tutorialManager.startTutorial(stepsToShow)
            tutorialManager.currentSteps.drop(1).first { it == null }

            stepsToShow.forEach { step ->
                tutorialInfoLocalDataSource.markShowcaseAsShown(step.targetId)
            }
        }
    }
}
