package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.PuzzleViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.composables.PuzzleScreen

const val puzzleIdArg = "puzzleId"
const val puzzleRoute = "puzzle/{$puzzleIdArg}"

fun NavGraphBuilder.puzzleScreen() {
    composable(
        route = puzzleRoute,
        arguments = listOf(
            navArgument(puzzleIdArg) { type = NavType.StringType }
        )
    ) {
        val viewModel = hiltViewModel<PuzzleViewModel>()
        PuzzleScreen(viewModel = viewModel)
    }
}

fun NavController.navigateToPuzzle(puzzleId: String, navOptions: NavOptions? = null) {
    navigate(route = "puzzle/$puzzleId", navOptions = navOptions)
}
