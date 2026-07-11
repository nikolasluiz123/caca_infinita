package br.com.schmittsolucoes.cacainfinita.presentation.camera.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.schmittsolucoes.cacainfinita.presentation.camera.CameraViewModel
import br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.CameraScreen
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.LanguageSelection

const val cameraRoute = "camera_feedback"
const val languageSelectionArg = "languageSelection"

fun NavGraphBuilder.cameraScreen(onBackClick: () -> Unit) {
    composable(
        route = "$cameraRoute/{$languageSelectionArg}",
        arguments = listOf(
            navArgument(languageSelectionArg) { type = NavType.StringType }
        )
    ) {
        val viewModel = hiltViewModel<CameraViewModel>()

        CameraScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToCamera(languageSelection: LanguageSelection, navOptions: NavOptions? = null) {
    navigate(route = "$cameraRoute/${languageSelection.name}", navOptions = navOptions)
}
