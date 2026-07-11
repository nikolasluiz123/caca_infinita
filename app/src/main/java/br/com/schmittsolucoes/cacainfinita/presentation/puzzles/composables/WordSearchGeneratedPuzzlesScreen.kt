package br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.schmittsolucoes.cacainfinita.R
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacainfinita.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacainfinita.presentation.components.PagedList
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseIds
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.showcaseTarget
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.WordSearchAnalytics
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.WordSearchUiState
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.WordSearchViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.components.AddWordSearchBottomSheet
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.components.EmptyWordSearchList
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.components.PuzzleConfigBottomSheet
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.components.WordSearchItem
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleGenerationConfig
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation

@Composable
fun WordSearchGeneratedPuzzlesScreen(
    viewModel: WordSearchViewModel,
    onOpenCameraClick: (LanguageSelection, GridOrientation) -> Unit,
    onPuzzleClick: (String) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val puzzles = state.puzzles.collectAsLazyPagingItems()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.startTutorialIfNeeded(puzzles.itemCount)
    }

    LaunchedEffect(puzzles.itemCount) {
        viewModel.startTutorialIfNeeded(puzzles.itemCount)
    }

    var pendingConfig by remember { mutableStateOf<PuzzleGenerationConfig?>(null) }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            pendingConfig?.let { config ->
                viewModel.onPdfsSelected(uris, config)
            }
        }
    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            pendingConfig?.let { config ->
                viewModel.onImagesSelected(uris, config)
            }
        }
    )

    WordSearchGeneratedPuzzlesScreen(
        state = state,
        onLoadImageClick = { config ->
            pendingConfig = config
            imagePickerLauncher.launch(arrayOf("image/*"))
        },
        onLoadPdfClick = { config ->
            pendingConfig = config
            pdfPickerLauncher.launch(arrayOf("application/pdf"))
        },
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onPuzzleClick = { puzzleId ->
            viewModel.onPuzzleItemClick()
            onPuzzleClick(puzzleId)
        },
        onDeletePuzzleClick = viewModel::onDeletePuzzle,
        onAddWordSearchClick = viewModel::onAddWordSearchClick,
        onBottomSheetOptionClick = viewModel::onBottomSheetOptionClick,
        onOpenCameraClick = { languageSelection, orientation ->
            viewModel.logNavigationToCamera()
            onOpenCameraClick(languageSelection, orientation)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordSearchGeneratedPuzzlesScreen(
    state: WordSearchUiState,
    onOpenCameraClick: (LanguageSelection, GridOrientation) -> Unit = { _, _ -> },
    onLoadImageClick: (PuzzleGenerationConfig) -> Unit = {},
    onLoadPdfClick: (PuzzleGenerationConfig) -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
    onPuzzleClick: (String) -> Unit = {},
    onDeletePuzzleClick: (String) -> Unit = {},
    onAddWordSearchClick: () -> Unit = {},
    onBottomSheetOptionClick: (String) -> Unit = {},
    puzzles: LazyPagingItems<WordSearchPuzzleSummary> = state.puzzles.collectAsLazyPagingItems()
) {
    val addSheetState = rememberModalBottomSheetState()
    val configSheetState = rememberModalBottomSheetState()
    var showAddBottomSheet by remember { mutableStateOf(value = false) }
    var showConfigBottomSheet by remember { mutableStateOf(value = false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onAddWordSearchClick()
                        showAddBottomSheet = true
                    },
                    modifier = Modifier.showcaseTarget(ShowcaseIds.ADD_PUZZLE_FAB)
                ) {
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
                ) { index, puzzle ->
                    puzzle?.let {
                        WordSearchItem(
                            puzzle = it,
                            onClick = onPuzzleClick,
                            onDeleteClick = onDeletePuzzleClick,
                            statusModifier = if (index == 0) Modifier.showcaseTarget(ShowcaseIds.PUZZLE_ITEM_STATUS) else Modifier,
                            deleteModifier = if (index == 0) Modifier.showcaseTarget(ShowcaseIds.PUZZLE_ITEM_DELETE) else Modifier
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            if (showAddBottomSheet) {
                AddWordSearchBottomSheet(
                    sheetState = addSheetState,
                    onDismissRequest = { showAddBottomSheet = false },
                    onOpenCameraClick = {
                        onBottomSheetOptionClick(WordSearchAnalytics.OPTION_CAMERA)
                        selectedOption = WordSearchAnalytics.OPTION_CAMERA
                        showConfigBottomSheet = true
                    },
                    onLoadImageClick = {
                        onBottomSheetOptionClick(WordSearchAnalytics.OPTION_IMAGE)
                        selectedOption = WordSearchAnalytics.OPTION_IMAGE
                        showConfigBottomSheet = true
                    },
                    onLoadPdfClick = {
                        onBottomSheetOptionClick(WordSearchAnalytics.OPTION_PDF)
                        selectedOption = WordSearchAnalytics.OPTION_PDF
                        showConfigBottomSheet = true
                    }
                )
            }

            if (showConfigBottomSheet) {
                PuzzleConfigBottomSheet(
                    sheetState = configSheetState,
                    onDismissRequest = { showConfigBottomSheet = false },
                    onConfirm = { config ->
                        showConfigBottomSheet = false
                        when (selectedOption) {
                            WordSearchAnalytics.OPTION_CAMERA -> onOpenCameraClick(config.languageSelection, config.orientation)
                            WordSearchAnalytics.OPTION_IMAGE -> onLoadImageClick(config)
                            WordSearchAnalytics.OPTION_PDF -> onLoadPdfClick(config)
                        }
                    }
                )
            }

            state.errorMessage?.let { message ->
                ErrorDialog(
                    message = message,
                    onDismiss = onDismissErrorDialog
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WordSearchGeneratedPuzzlesScreenPreview() {
    CacaInfinitaTheme {
        WordSearchGeneratedPuzzlesScreen(
            state = WordSearchUiState(),
        )
    }
}
