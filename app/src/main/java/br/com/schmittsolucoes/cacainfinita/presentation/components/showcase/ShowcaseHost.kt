package br.com.schmittsolucoes.cacainfinita.presentation.components.showcase

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.manager.TutorialManager
import kotlin.math.max

@Composable
fun ShowcaseHost(
    tutorialManager: TutorialManager,
    modifier: Modifier = Modifier,
    onTutorialFinished: () -> Unit = {}
) {
    val steps by tutorialManager.currentSteps.collectAsState()
    val currentIndex by tutorialManager.currentStepIndex.collectAsState()
    val targets by ShowcaseTargets.targets.collectAsState()

    AnimatedVisibility(
        visible = steps != null,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        val currentStep = steps?.getOrNull(currentIndex) ?: return@AnimatedVisibility
        val targetRect = targets[currentStep.targetId] ?: Rect.Zero

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.99f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { 
                        if (currentIndex + 1 < (steps?.size ?: 0)) {
                            tutorialManager.nextStep()
                        } else {
                            onTutorialFinished()
                        }
                    }
                )
        ) {
            val density = LocalDensity.current
            val padding = with(density) { 8.dp.toPx() }

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(color = Color.Black.copy(alpha = 0.7f))

                if (targetRect != Rect.Zero) {
                    when (currentStep.shape) {
                        ShowcaseShape.Rectangle -> {
                            drawRoundRect(
                                color = Color.Transparent,
                                topLeft = targetRect.topLeft.copy(
                                    x = targetRect.left - padding,
                                    y = targetRect.top - padding
                                ),
                                size = Size(
                                    targetRect.width + (padding * 2),
                                    targetRect.height + (padding * 2)
                                ),
                                cornerRadius = CornerRadius(with(density) { 8.dp.toPx() }),
                                blendMode = BlendMode.Clear
                            )
                        }
                        ShowcaseShape.Circle -> {
                            val radius = max(targetRect.width, targetRect.height) / 2 + padding
                            drawCircle(
                                color = Color.Transparent,
                                radius = radius,
                                center = targetRect.center,
                                blendMode = BlendMode.Clear
                            )
                        }
                    }
                }
            }

            if (targetRect != Rect.Zero) {
                ShowcaseTooltip(
                    text = currentStep.text,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 32.dp)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            val tooltipHeight = placeable.height

                            val spaceAbove = targetRect.top
                            val spaceBelow = constraints.maxHeight - targetRect.bottom

                            val y = if (spaceBelow >= tooltipHeight + padding * 4) {
                                targetRect.bottom + padding * 3
                            } else if (spaceAbove >= tooltipHeight + padding * 4) {
                                targetRect.top - tooltipHeight - padding * 3
                            } else {
                                if (spaceBelow > spaceAbove) {
                                    (constraints.maxHeight - tooltipHeight - padding * 3).coerceAtLeast(0f)
                                } else {
                                    (padding * 3).coerceAtMost(constraints.maxHeight - tooltipHeight - padding * 3)
                                }
                            }

                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(
                                    x = (constraints.maxWidth - placeable.width) / 2,
                                    y = y.toInt()
                                )
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun ShowcaseTooltip(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.widthIn(max = 300.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.tutorial_tap_to_continue),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
