package br.com.schmittsolucoes.cacainfinita.presentation

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.schmittsolucoes.cacainfinita.presentation.components.ErrorDialog
import br.com.schmittsolucoes.cacainfinita.presentation.components.LoadingOverlay
import br.com.schmittsolucoes.cacainfinita.presentation.components.showcase.ShowcaseHost
import br.com.schmittsolucoes.cacainfinita.presentation.home.composables.components.HomeBottomNavBar
import br.com.schmittsolucoes.cacainfinita.presentation.home.navigation.homeScreenRoute
import br.com.schmittsolucoes.cacainfinita.presentation.home.navigation.navigateToHome
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.navigation.navigateToWordSearchGeneratedPuzzles
import br.com.schmittsolucoes.cacainfinita.presentation.puzzles.navigation.wordSearchGeneratedPuzzlesRoute
import br.com.schmittsolucoes.cacainfinita.presentation.theme.CacaInfinitaTheme
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
            val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
            val loadingMessage by viewModel.loadingMessage.collectAsStateWithLifecycle()
            val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(snackbarMessage) {
                snackbarMessage?.let {
                    snackbarHostState.showSnackbar(it)
                    viewModel.onDismissSnackbar()
                }
            }

            CacaInfinitaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    Box(modifier = Modifier.fillMaxSize()) {
                        App(
                            navController = navController,
                            viewModel = viewModel,
                            snackbarHostState = snackbarHostState
                        ) {
                            AppNavHost(navController = navController)

                            appErrorMessage?.let { message ->
                                ErrorDialog(
                                    message = message,
                                    onDismiss = viewModel::onDismissErrorDialog
                                )
                            }
                        }

                        if (isLoading) {
                            loadingMessage?.let { LoadingOverlay(message = it) } ?: LoadingOverlay()
                        }

                        ShowcaseHost(
                            tutorialManager = viewModel.tutorialManager,
                            onTutorialFinished = viewModel::onDismissTutorial
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun App(
    navController: NavHostController,
    viewModel: AppViewModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable () -> Unit = { }
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == homeScreenRoute || currentRoute == wordSearchGeneratedPuzzlesRoute) {
                HomeBottomNavBar(
                    onHomeClick = {
                        viewModel.logBottomNavigation(MainActivityAnalytics.HOME_DESTINY)
                        navController.navigateToHome()
                    },
                    onWordSearchClick = {
                        viewModel.logBottomNavigation(MainActivityAnalytics.WORD_SEARCH_DESTINY)
                        navController.navigateToWordSearchGeneratedPuzzles()
                    },
                    isHomeSelected = currentRoute == homeScreenRoute
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
    CacaInfinitaTheme {
        // App(navController = rememberNavController(), viewModel = ...)
    }
}
