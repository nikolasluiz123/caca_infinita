package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.home.composables.components.HomeBottomNavBar
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchUiState
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme

@Composable
fun WordSearchGeneratedPuzzlesScreen(
    viewModel: WordSearchViewModel,
    onHomeClick: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    WordSearchGeneratedPuzzlesScreen(
        state = state,
        onHomeClick = onHomeClick
    )
}

@Composable
fun WordSearchGeneratedPuzzlesScreen(
    state: WordSearchUiState,
    onHomeClick: () -> Unit = {},
) {
    Scaffold(
        bottomBar = {
            HomeBottomNavBar(
                onHomeClick = onHomeClick,
                isHomeSelected = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(painterResource(R.drawable.ic_add_24dp), contentDescription = null)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Screen content
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WordSearchGeneratedPuzzlesScreenPreview() {
    CacaSobMedidaTheme {
        WordSearchGeneratedPuzzlesScreen(
            state = WordSearchUiState()
        )
    }
}
