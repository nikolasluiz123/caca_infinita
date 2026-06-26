package br.com.schmittsolucoes.cacasobmedida.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.homeScreen
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.homeScreenRoute

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
    }
}