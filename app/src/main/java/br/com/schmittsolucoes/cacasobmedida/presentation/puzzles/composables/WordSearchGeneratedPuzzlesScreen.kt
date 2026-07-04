package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacasobmedida.presentation.components.LoadingOverlay
import br.com.schmittsolucoes.cacasobmedida.presentation.components.PagedList
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchUiState
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.components.AddWordSearchBottomSheet
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.components.EmptyWordSearchList
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.components.WordSearchItem
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme

@Composable
fun WordSearchGeneratedPuzzlesScreen(
    viewModel: WordSearchViewModel,
    onOpenCameraClick: () -> Unit,
    onPuzzleClick: (String) -> Unit,
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
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onPuzzleClick = onPuzzleClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSearchGeneratedPuzzlesScreen(
    state: WordSearchUiState,
    onOpenCameraClick: () -> Unit = {},
    onLoadImageClick: () -> Unit = {},
    onLoadPdfClick: () -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
    onPuzzleClick: (String) -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(value = false) }
    val puzzles = state.puzzles.collectAsLazyPagingItems()

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
                PagedList(
                    items = puzzles,
                    emptyContent = { EmptyWordSearchList() },
                    contentPadding = PaddingValues(16.dp),
                ) { puzzle ->
                    puzzle?.let {
                        WordSearchItem(
                            puzzle = it,
                            onClick = onPuzzleClick
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
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
        )
    }
}
