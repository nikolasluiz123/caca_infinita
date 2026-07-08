package br.com.schmittsolucoes.cacainfinita.data.manager

import br.com.schmittsolucoes.cacainfinita.domain.manager.TutorialManager
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TutorialManagerImpl @Inject constructor() : TutorialManager {
    private val _currentSteps = MutableStateFlow<List<ShowcaseStep>?>(null)
    override val currentSteps: StateFlow<List<ShowcaseStep>?> = _currentSteps.asStateFlow()

    private val _currentStepIndex = MutableStateFlow(0)
    override val currentStepIndex: StateFlow<Int> = _currentStepIndex.asStateFlow()

    override fun startTutorial(steps: List<ShowcaseStep>) {
        _currentStepIndex.value = 0
        _currentSteps.value = steps
    }

    override fun nextStep() {
        val steps = _currentSteps.value ?: return
        
        if (_currentStepIndex.value + 1 < steps.size) {
            _currentStepIndex.value += 1
        } else {
            dismiss()
        }
    }

    override fun dismiss() {
        _currentSteps.value = null
        _currentStepIndex.value = 0
    }
}
