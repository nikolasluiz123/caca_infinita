package br.com.schmittsolucoes.cacainfinita.domain.manager

import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseStep
import kotlinx.coroutines.flow.StateFlow

interface TutorialManager {
    val currentSteps: StateFlow<List<ShowcaseStep>?>
    val currentStepIndex: StateFlow<Int>
    
    fun startTutorial(steps: List<ShowcaseStep>)
    fun nextStep()
    fun dismiss()
}
