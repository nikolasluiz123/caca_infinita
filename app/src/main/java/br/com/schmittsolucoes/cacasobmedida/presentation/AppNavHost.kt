package br.com.schmittsolucoes.cacasobmedida.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.navigation.cameraScreen
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.navigation.navigateToCamera
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.homeScreen
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.homeScreenRoute
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.navigateToHome
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.navigation.wordSearchGeneratedPuzzlesScreen
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.navigation.navigateToPuzzle
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.navigation.puzzleScreen

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
            onOpenCameraClick = navController::navigateToCamera,
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
