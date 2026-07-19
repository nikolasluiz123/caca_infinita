package br.com.schmittsolucoes.cacainfinita.presentation.home.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.AppTutorial
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.showcaseTarget
import br.com.schmittsolucoes.cacainfinita.presentation.core.RequestAllPermissions
import br.com.schmittsolucoes.cacainfinita.presentation.home.HomeUIState
import br.com.schmittsolucoes.cacainfinita.presentation.home.HomeViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.home.composables.components.ContinueGameButton
import br.com.schmittsolucoes.cacainfinita.presentation.home.composables.components.PersonalRecordsSection
import br.com.schmittsolucoes.cacainfinita.presentation.home.composables.components.UserLevelStatusCard
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleRecord
import br.com.schmittsolucoes.cacainfinita.domain.model.User
import kotlin.time.Duration.Companion.minutes

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onContinueGameClick: (String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.startTutorialIfNeeded()
    }

    HomeScreen(
        state = state,
        onContinueGameClick = onContinueGameClick,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        logContinueGameClick = { viewModel.logNavigationToPuzzle() }
    )
}

@Composable
fun HomeScreen(
    state: HomeUIState = HomeUIState(),
    onContinueGameClick: (String) -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
    logContinueGameClick: (String) -> Unit = {}
) {
    RequestAllPermissions(context = LocalContext.current)

    Scaffold(
        contentWindowInsets = WindowInsets()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            UserLevelStatusCard(
                level = state.user?.level ?: 0,
                xp = state.user?.actualExperience ?: 0,
                xpToNextLevel = state.user?.maxLevelExperience ?: 0,
                modifier = Modifier.showcaseTarget(AppTutorial.Home.USER_LEVEL_CARD)
            )

            state.puzzleIdToPlay?.let { puzzleId ->
                val text = if (state.isNewGame) {
                    stringResource(R.string.new_game)
                } else {
                    stringResource(R.string.continue_game)
                }

                ContinueGameButton(
                    text = text,
                    onClick = {
                        logContinueGameClick(puzzleId)
                        onContinueGameClick(puzzleId)
                    }
                )
            }

            PersonalRecordsSection(
                records = state.records,
                user = state.user,
                modifier = Modifier.showcaseTarget(AppTutorial.Home.PERSONAL_RECORDS_SECTION)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        state.errorMessage?.let { message ->
            ErrorDialog(
                message = message,
                onDismiss = onDismissErrorDialog
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    CacaInfinitaTheme {
        HomeScreen()
    }
}

@Preview(name = "With Records Light")
@Preview(name = "With Records Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenWithRecordsPreview() {
    CacaInfinitaTheme {
        HomeScreen(
            state = HomeUIState(
                user = User(
                    id = "1",
                    actualExperience = 500,
                    maxLevelExperience = 1000,
                    level = 5,
                    puzzlesCompleted = 10,
                    totalWordsFound = 150,
                    fastestFirstWordMs = 2000
                ),
                records = listOf(
                    PuzzleRecord("1", "Puzzle 1", 15, 5.minutes),
                    PuzzleRecord("2", "Puzzle 2", 20, 10.minutes),
                    PuzzleRecord("3", "Puzzle 3", 12, 4.minutes)
                ),
                puzzleIdToPlay = "1"
            )
        )
    }
}
