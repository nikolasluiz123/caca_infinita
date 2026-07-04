package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacasobmedida.presentation.components.LoadingOverlay
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.PuzzleUiState
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.PuzzleViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.composables.components.WordsBottomSheet
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.SecondaryTextColor

@Composable
fun PuzzleScreen(
    viewModel: PuzzleViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.onStart()
    }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        viewModel.onStop()
    }

    PuzzleScreen(
        state = state,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onToggleWordsBottomSheet = viewModel::onToggleWordsBottomSheet
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleScreen(
    state: PuzzleUiState,
    onDismissErrorDialog: () -> Unit = {},
    onToggleWordsBottomSheet: (Boolean) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onToggleWordsBottomSheet(true) },
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
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(R.string.puzzle_screen_time_label),
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondaryTextColor
                )
            }
        }

        if (state.isLoading) {
            LoadingOverlay()
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
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PuzzleScreenPreview() {
    CacaSobMedidaTheme {
        PuzzleScreen(
            state = PuzzleUiState(
                formattedTime = "00:05:30",
                foundWordsCount = 5,
                totalWordsCount = 10
            )
        )
    }
}
