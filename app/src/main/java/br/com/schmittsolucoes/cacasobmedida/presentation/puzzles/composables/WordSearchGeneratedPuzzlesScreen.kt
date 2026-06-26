package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchUiState
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.components.AddWordSearchBottomSheet
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.components.EmptyWordSearchList
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme

@Composable
fun WordSearchGeneratedPuzzlesScreen(
    viewModel: WordSearchViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    WordSearchGeneratedPuzzlesScreen(
        state = state
    )
}

@Composable
fun WordSearchGeneratedPuzzlesScreen(
    state: WordSearchUiState,
) {
    WordSearchGeneratedPuzzlesScreen(
        state = state,
        onOpenCameraClick = {},
        onLoadImageClick = {},
        onLoadPdfClick = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSearchGeneratedPuzzlesScreen(
    state: WordSearchUiState,
    onOpenCameraClick: () -> Unit,
    onLoadImageClick: () -> Unit,
    onLoadPdfClick: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(value = false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(painterResource(R.drawable.ic_add_24dp), contentDescription = null)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            EmptyWordSearchList()
        }

        if (showBottomSheet) {
            AddWordSearchBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                onOpenCameraClick = onOpenCameraClick,
                onLoadImageClick = onLoadImageClick,
                onLoadPdfClick = onLoadPdfClick
            )
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
