package br.com.schmittsolucoes.cacainfinita.presentation.home.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.cacainfinita.presentation.home.HomeViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.home.composables.HomeScreen

fun NavGraphBuilder.homeScreen(
    onContinueGameClick: (String) -> Unit
) {
    composable<HomeRoute> {
        val viewModel = hiltViewModel<HomeViewModel>()

        HomeScreen(
            viewModel = viewModel,
            onContinueGameClick = onContinueGameClick
        )
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(route = HomeRoute, navOptions = navOptions)
}
