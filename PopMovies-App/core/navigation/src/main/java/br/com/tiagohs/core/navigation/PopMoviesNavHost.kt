package br.com.tiagohs.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.tiagohs.core.navigation.destinations.HomeDestination
import br.com.tiagohs.core.navigation.destinations.SignInDestination
import br.com.tiagohs.core.navigation.destinations.SignUpDestination
import br.com.tiagohs.core.navigation.destinations.destinations
import br.com.tiagohs.core.navigation.models.Destination
import br.com.tiagohs.features.home.ui.HomeScreen
import br.com.tiagohs.features.home.ui.HomeViewModel
import br.com.tiagohs.features.signin.ui.SignInScreen
import br.com.tiagohs.features.signup.ui.SignUpScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopMoviesNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startScreen: Destination = destinations.first()
) {
    NavHost(
        navController = navHostController,
        startDestination = startScreen.route,
        modifier = modifier
    ) {
        composable(
            route = SignInDestination.route,
            arguments = SignInDestination.arguments
        ) {
            SignInScreen(
                onClickSignIn = {

                },
                onClickForgotPassword = {

                },
                onClickSignUp = {
                    navHostController.navigateSingleTopTo(HomeDestination.route)
                }
            )
        }
        composable(
            route = SignUpDestination.route,
            arguments = SignInDestination.arguments
        ) {
            SignUpScreen(
                onClickSignUp = {

                },
                onClickSignIn = {

                }
            )
        }
        composable(
            route = HomeDestination.route,
            arguments = HomeDestination.arguments
        ) {
            val homeViewModel = koinViewModel<HomeViewModel>()

            HomeScreen(
                homeViewModel = homeViewModel
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }