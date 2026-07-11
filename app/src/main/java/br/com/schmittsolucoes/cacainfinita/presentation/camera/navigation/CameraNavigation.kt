package br.com.schmittsolucoes.cacainfinita.presentation.camera.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.cacainfinita.presentation.camera.CameraViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.CameraScreen

fun NavGraphBuilder.cameraScreen(onBackClick: () -> Unit) {
    composable<CameraRoute> {
        val viewModel = hiltViewModel<CameraViewModel>()

        CameraScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToCamera(
    route: CameraRoute,
    navOptions: NavOptions? = null
) {
    navigate(
        route = route,
        navOptions = navOptions
    )
}
