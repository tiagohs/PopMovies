package br.com.tiagohs.core.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.tiagohs.core.navigation.destinations.*
import br.com.tiagohs.core.navigation.models.Destination
import br.com.tiagohs.features.auth.ui.logIn.LogInScreen
import br.com.tiagohs.features.home.ui.HomeScreen
import br.com.tiagohs.features.home.ui.HomeViewModel
import br.com.tiagohs.features.auth.ui.signIn.SignInScreen
import br.com.tiagohs.features.auth.ui.signUp.SignUpScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopMoviesNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startScreen: Destination = destinations.first(),
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = startScreen.route,
        modifier = modifier
    ) {
        composable(
            route = LogInDestination.route,
            arguments = LogInDestination.arguments
        ) {
            LogInScreen(
                innerPadding = innerPadding,
                onClickSignIn = {
                    navHostController.navigateSingleTopTo(SignInDestination.route)
                },
                onClickHelp = {

                },
                onClickSignUp = {
                    navHostController.navigateSingleTopTo(SignUpDestination.route)
                }
            )
        }
        composable(
            route = SignInDestination.route,
            arguments = SignInDestination.arguments
        ) {
            SignInScreen(
                innerPadding = innerPadding,
                onClickSignIn = {
                    navHostController.navigateSingleTopTo(SignInDestination.route)
                },
                onClickForgotPassword = {

                },
                onClickSignUp = {
                    navHostController.navigateSingleTopTo(HomeDestination.route)
                },
                onClickClose = {

                }
            )
        }
        composable(
            route = SignUpDestination.route,
            arguments = SignInDestination.arguments
        ) {
            SignUpScreen(
                innerPadding = innerPadding,
                onClickSignUp = {

                },
                onClickSignIn = {

                },
                onClickClose = {

                }
            )
        }
        composable(
            route = HomeDestination.route,
            arguments = HomeDestination.arguments
        ) {
            val homeViewModel = koinViewModel<HomeViewModel>()

            HomeScreen(
                innerPadding = innerPadding,
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