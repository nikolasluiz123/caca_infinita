package br.com.schmittsolucoes.cacainfinita.presentation.puzzle.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.PuzzleViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.composables.PuzzleScreen

fun NavGraphBuilder.puzzleScreen(
    onNavigateToHome: () -> Unit
) {
    composable<PuzzleRoute> {
        val viewModel = hiltViewModel<PuzzleViewModel>()
        PuzzleScreen(
            viewModel = viewModel,
            onNavigateToHome = onNavigateToHome
        )
    }
}

fun NavController.navigateToPuzzle(puzzleId: String, navOptions: NavOptions? = null) {
    navigate(route = PuzzleRoute(puzzleId), navOptions = navOptions)
}
