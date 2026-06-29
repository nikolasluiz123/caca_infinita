package br.com.schmittsolucoes.cacasobmedida.presentation.camera.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.CameraViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.camera.composables.CameraScreen

const val cameraRoute = "camera_feedback"

fun NavGraphBuilder.cameraScreen(onBackClick: () -> Unit) {
    composable(route = cameraRoute) {
        val viewModel = hiltViewModel<CameraViewModel>()

        CameraScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToCamera(navOptions: NavOptions? = null) {
    navigate(route = cameraRoute, navOptions = navOptions)
}
