package br.com.schmittsolucoes.cacainfinita.presentation.components.showcase

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ShowcaseTargets {
    private val _targets = MutableStateFlow<Map<String, Rect>>(emptyMap())
    val targets = _targets.asStateFlow()

    fun updateTarget(id: String, rect: Rect) {
        _targets.value += (id to rect)
    }
}

@Composable
fun Modifier.showcaseTarget(id: String): Modifier {
    return this.onGloballyPositioned { coordinates ->
        val position = coordinates.positionInWindow()
        val size = coordinates.size.toSize()
        ShowcaseTargets.updateTarget(id, Rect(position, size))
    }
}
