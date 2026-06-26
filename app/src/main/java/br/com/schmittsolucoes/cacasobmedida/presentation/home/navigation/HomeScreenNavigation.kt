package br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.cacasobmedida.presentation.home.HomeViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.home.composables.HomeScreen

const val homeScreenRoute = "home"

fun NavGraphBuilder.homeScreen(
    onContinueGameClick: (String) -> Unit
) {
    composable(route = homeScreenRoute) {
        val viewModel = hiltViewModel<HomeViewModel>()

        HomeScreen(
            viewModel = viewModel,
            onContinueGameClick = onContinueGameClick
        )
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(route = homeScreenRoute, navOptions = navOptions)
}