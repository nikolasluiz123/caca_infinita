package br.com.schmittsolucoes.cacasobmedida.presentation.home.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacasobmedida.presentation.home.HomeUIState
import br.com.schmittsolucoes.cacasobmedida.presentation.home.HomeViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.home.composables.components.ContinueGameButton
import br.com.schmittsolucoes.cacasobmedida.presentation.home.composables.components.PersonalRecordsSection
import br.com.schmittsolucoes.cacasobmedida.presentation.home.composables.components.UserLevelStatusCard
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onContinueGameClick: (String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onContinueGameClick = onContinueGameClick
    )
}

@Composable
fun HomeScreen(
    state: HomeUIState = HomeUIState(),
    onContinueGameClick: (String) -> Unit = {},
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            UserLevelStatusCard(
                level = state.user?.level ?: 0,
                xp = state.user?.actualExperience ?: 0,
                xpToNextLevel = state.user?.maxLevelExperience ?: 0
            )

            state.unfinishedPuzzleId?.let { puzzleId ->
                ContinueGameButton(onClick = { onContinueGameClick(puzzleId) })
            }

            PersonalRecordsSection(records = state.records)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    CacaSobMedidaTheme {
        HomeScreen()
    }
}
