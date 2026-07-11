package br.com.schmittsolucoes.cacainfinita.presentation.puzzle.composables

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.Coordinate
import br.com.schmittsolucoes.cacainfinita.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseIds
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.showcaseTarget
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.PuzzleUiState
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.PuzzleViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.composables.components.PuzzleGrid
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.composables.components.WordsBottomSheet
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.presentation.theme.SecondaryTextColor
import kotlinx.coroutines.launch

@Composable
fun PuzzleScreen(
    viewModel: PuzzleViewModel,
    onNavigateToHome: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isPuzzleFinished) {
        if (state.isPuzzleFinished) {
            viewModel.logNavigationToHome()
            onNavigateToHome()
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.onStart()
    }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        viewModel.onStop()
    }

    PuzzleScreen(
        state = state,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onToggleWordsBottomSheet = viewModel::onToggleWordsBottomSheet,
        onWordSelected = viewModel::onWordSelected,
        onAnimationFinished = viewModel::onAnimationFinished
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleScreen(
    state: PuzzleUiState,
    onDismissErrorDialog: () -> Unit = {},
    onToggleWordsBottomSheet: (Boolean) -> Unit = {},
    onWordSelected: (Coordinate, Coordinate) -> Unit = { _, _ -> },
    onAnimationFinished: (Long) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onToggleWordsBottomSheet(true) },
                    modifier = Modifier.showcaseTarget(ShowcaseIds.PUZZLE_WORDS_FAB)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_grid_view),
                        contentDescription = null
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.formattedTime,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.showcaseTarget(ShowcaseIds.PUZZLE_TIMER)
                )
                Text(
                    text = stringResource(R.string.puzzle_screen_time_label),
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryTextColor
                )

                state.puzzle?.let { puzzle ->
                    val foundWords = state.words.filter { it.foundDate != null }

                    PuzzleGrid(
                        puzzle = puzzle,
                        foundWords = foundWords,
                        onWordSelected = onWordSelected,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 16.dp)
                            .padding(bottom = state.paddingBottom.dp)
                            .showcaseTarget(ShowcaseIds.PUZZLE_GRID)
                    )
                }
            }
        }

        state.errorMessage?.let { message ->
            ErrorDialog(
                message = message,
                onDismiss = onDismissErrorDialog
            )
        }

        if (state.isWordsBottomSheetVisible) {
            WordsBottomSheet(
                words = state.words,
                sheetState = sheetState,
                onDismissRequest = { onToggleWordsBottomSheet(false) }
            )
        }

        state.xpAnimations.forEach { xpAnimation ->
            key(xpAnimation.id) {
                XpGainAnimation(
                    amount = xpAnimation.amount,
                    onAnimationFinished = { onAnimationFinished(xpAnimation.id) },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun XpGainAnimation(
    amount: Long,
    onAnimationFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val alpha = remember { Animatable(1f) }
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            alpha.animateTo(0f, animationSpec = tween(durationMillis = 2000))
        }
        offsetY.animateTo(-150f, animationSpec = tween(durationMillis = 2000))
        onAnimationFinished()
    }

    Text(
        text = "+$amount XP",
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        modifier = modifier
            .offset(y = offsetY.value.dp)
            .alpha(alpha.value)
    )
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PuzzleScreenPreview() {
    val samplePuzzle = WordSearchPuzzle(
        id = "1",
        grid = listOf(
            listOf('A', 'B', 'C'),
            listOf('D', 'E', 'F'),
            listOf('G', 'H', 'I')
        ),
        name = "Sample",
        rows = 3,
        columns = 3,
        orientation = GridOrientation.PORTRAIT
    )

    CacaInfinitaTheme {
        PuzzleScreen(
            state = PuzzleUiState(
                puzzle = samplePuzzle,
                formattedTime = "00:05:30",
            )
        )
    }
}
