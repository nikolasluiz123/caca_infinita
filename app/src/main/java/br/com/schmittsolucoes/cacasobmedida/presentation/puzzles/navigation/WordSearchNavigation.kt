package br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.WordSearchViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.composables.WordSearchGeneratedPuzzlesScreen

const val wordSearchGeneratedPuzzlesRoute = "word_search_generated_puzzles"

fun NavGraphBuilder.wordSearchGeneratedPuzzlesScreen(navController: NavController) {
    composable(route = wordSearchGeneratedPuzzlesRoute) {
        val viewModel = hiltViewModel<WordSearchViewModel>()
        WordSearchGeneratedPuzzlesScreen(
            viewModel = viewModel
        )
    }
}

fun NavController.navigateToWordSearchGeneratedPuzzles(navOptions: NavOptions? = null) {
    navigate(route = wordSearchGeneratedPuzzlesRoute, navOptions = navOptions)
}
