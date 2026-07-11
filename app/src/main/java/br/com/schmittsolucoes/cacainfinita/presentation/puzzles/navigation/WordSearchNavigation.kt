package br.com.schmittsolucoes.cacainfinita.presentation.puzzles.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.WordSearchViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.composables.WordSearchGeneratedPuzzlesScreen
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection

fun NavGraphBuilder.wordSearchGeneratedPuzzlesScreen(
    onOpenCameraClick: (LanguageSelection, GridOrientation) -> Unit,
    onPuzzleClick: (String) -> Unit
) {
    composable<WordSearchGeneratedPuzzlesRoute> {
        val viewModel = hiltViewModel<WordSearchViewModel>()

        WordSearchGeneratedPuzzlesScreen(
            viewModel = viewModel,
            onOpenCameraClick = onOpenCameraClick,
            onPuzzleClick = onPuzzleClick
        )
    }
}

fun NavController.navigateToWordSearchGeneratedPuzzles(navOptions: NavOptions? = null) {
    navigate(route = WordSearchGeneratedPuzzlesRoute, navOptions = navOptions)
}
