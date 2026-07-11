package br.com.schmittsolucoes.cacainfinita.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import br.com.schmittsolucoes.cacainfinita.presentation.camera.navigation.cameraScreen
import br.com.schmittsolucoes.cacainfinita.presentation.camera.navigation.navigateToCamera
import br.com.schmittsolucoes.cacainfinita.presentation.home.navigation.homeScreen
import br.com.schmittsolucoes.cacainfinita.presentation.home.navigation.homeScreenRoute
import br.com.schmittsolucoes.cacainfinita.presentation.home.navigation.navigateToHome
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.navigation.wordSearchGeneratedPuzzlesScreen
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.navigation.navigateToPuzzle
import br.com.schmittsolucoes.cacainfinita.presentation.puzzle.navigation.puzzleScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = homeScreenRoute,
        modifier = modifier
    ) {
        homeScreen(
            onContinueGameClick = navController::navigateToPuzzle
        )
        wordSearchGeneratedPuzzlesScreen(
            onOpenCameraClick = { languageSelection ->
                navController.navigateToCamera(languageSelection)
            },
            onPuzzleClick = navController::navigateToPuzzle
        )

        cameraScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )

        puzzleScreen(
            onNavigateToHome = {
                navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo(homeScreenRoute) {
                            inclusive = true
                        }
                    }
                )
            }
        )
    }
}
