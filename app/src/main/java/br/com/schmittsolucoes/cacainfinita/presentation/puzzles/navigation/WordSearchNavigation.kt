package br.com.schmittsolucoes.cacainfinita.presentation.puzzles.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.WordSearchViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.WordSearchGeneratedPuzzlesScreen

const val wordSearchGeneratedPuzzlesRoute = "word_search_generated_puzzles"

fun NavGraphBuilder.wordSearchGeneratedPuzzlesScreen(
    onOpenCameraClick: () -> Unit,
    onPuzzleClick: (String) -> Unit
) {
    composable(route = wordSearchGeneratedPuzzlesRoute) {
        val viewModel = hiltViewModel<WordSearchViewModel>()

        WordSearchGeneratedPuzzlesScreen(
            viewModel = viewModel,
            onOpenCameraClick = onOpenCameraClick,
            onPuzzleClick = onPuzzleClick
        )
    }
}

fun NavController.navigateToWordSearchGeneratedPuzzles(navOptions: NavOptions? = null) {
    navigate(route = wordSearchGeneratedPuzzlesRoute, navOptions = navOptions)
}
