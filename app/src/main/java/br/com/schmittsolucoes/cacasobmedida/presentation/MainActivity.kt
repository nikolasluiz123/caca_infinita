package br.com.schmittsolucoes.cacasobmedida.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.schmittsolucoes.cacasobmedida.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacasobmedida.presentation.home.composables.components.HomeBottomNavBar
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.homeScreenRoute
import br.com.schmittsolucoes.cacasobmedida.presentation.home.navigation.navigateToHome
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.navigation.navigateToWordSearchGeneratedPuzzles
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzles.navigation.wordSearchGeneratedPuzzlesRoute
import br.com.schmittsolucoes.cacasobmedida.presentation.theme.CacaSobMedidaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.isInitializing.value
        }

        enableEdgeToEdge()
        setContent {
            val appErrorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

            CacaSobMedidaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    App(navController = navController) {
                        AppNavHost(navController = navController)

                        appErrorMessage?.let { message ->
                            ErrorDialog(
                                message = message,
                                onDismiss = viewModel::onDismissErrorDialog
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun App(
    navController: NavHostController,
    content: @Composable () -> Unit = { }
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == homeScreenRoute || currentRoute == wordSearchGeneratedPuzzlesRoute) {
                HomeBottomNavBar(
                    onHomeClick = {
                        navController.navigateToHome()
                    },
                    onWordSearchClick = {
                        navController.navigateToWordSearchGeneratedPuzzles()
                    },
                    isHomeSelected = currentRoute == homeScreenRoute
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    CacaSobMedidaTheme {
        App(navController = rememberNavController())
    }
}