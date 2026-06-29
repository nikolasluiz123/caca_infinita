package br.com.schmittsolucoes.cacasobmedida.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.navigation.cameraScreen
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.navigation.navigateToCamera
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.homeScreen
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.homeScreenRoute
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.navigation.wordSearchGeneratedPuzzlesScreen

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
            onContinueGameClick = { puzzleId ->

            }
        )
        wordSearchGeneratedPuzzlesScreen(
            onOpenCameraClick = navController::navigateToCamera
        )

        cameraScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}
