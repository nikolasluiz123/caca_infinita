package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import br.com.schmittsolucoes.cacasobmedida.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacasobmedida.presentation.components.LoadingOverlay
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchUiState
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.components.AddWordSearchBottomSheet
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.components.EmptyWordSearchList
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme

@Composable
fun WordSearchGeneratedPuzzlesScreen(
    viewModel: WordSearchViewModel,
    onOpenCameraClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            viewModel.onPdfsSelected(uris)
        }
    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            viewModel.onImagesSelected(uris)
        }
    )

    WordSearchGeneratedPuzzlesScreen(
        state = state,
        onOpenCameraClick = onOpenCameraClick,
        onLoadImageClick = {
            imagePickerLauncher.launch(arrayOf("image/*"))
        },
        onLoadPdfClick = {
            pdfPickerLauncher.launch(arrayOf("application/pdf"))
        },
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSearchGeneratedPuzzlesScreen(
    state: WordSearchUiState,
    onOpenCameraClick: () -> Unit,
    onLoadImageClick: () -> Unit,
    onLoadPdfClick: () -> Unit,
    onDismissErrorDialog: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(value = false) }

    Box(modifier = Modifier.fillMaxSize()) {
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

            state.errorMessage?.let { message ->
                ErrorDialog(
                    message = message,
                    onDismiss = onDismissErrorDialog
                )
            }
        }

        if (state.isLoading) {
            LoadingOverlay()
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WordSearchGeneratedPuzzlesScreenPreview() {
    CacaSobMedidaTheme {
        WordSearchGeneratedPuzzlesScreen(
            state = WordSearchUiState(),
            onOpenCameraClick = {},
            onLoadImageClick = {},
            onLoadPdfClick = {},
            onDismissErrorDialog = {}
        )
    }
}
