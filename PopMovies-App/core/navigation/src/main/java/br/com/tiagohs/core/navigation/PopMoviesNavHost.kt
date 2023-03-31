package br.com.tiagohs.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.tiagohs.core.navigation.destinations.*
import br.com.tiagohs.core.navigation.models.Destination
import br.com.tiagohs.features.auth.ui.logIn.LogInRoute
import br.com.tiagohs.features.auth.ui.logIn.LogInScreen
import br.com.tiagohs.features.auth.ui.signIn.SignInRoute
import br.com.tiagohs.features.home.ui.HomeScreen
import br.com.tiagohs.features.home.ui.HomeViewModel
import br.com.tiagohs.features.auth.ui.signIn.SignInScreen
import br.com.tiagohs.features.auth.ui.signUp.SignUpRoute
import br.com.tiagohs.features.auth.ui.signUp.SignUpScreen
import br.com.tiagohs.features.home.ui.HomeRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopMoviesNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startScreen: Destination = destinations.first(),
    onBackPressed: () -> Unit
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
            LogInRoute(
                screenName = LogInDestination.screenName,
                onBackPressed = onBackPressed,
                onClickSignIn = {
                    navHostController.navigateSingleTop(SignInDestination.route)
                },
                onClickHelp = {

                },
                onClickSignUp = {
                    navHostController.navigateSingleTop(SignUpDestination.route)
                }
            )
        }
        composable(
            route = SignInDestination.route,
            arguments = SignInDestination.arguments
        ) {
            SignInRoute(
                screenName = SignInDestination.screenName,
                onBackPressed = onBackPressed,
                onClickSignIn = {
                    navHostController.navigateSingleTop(SignInDestination.route)
                },
                onClickForgotPassword = {

                },
                onClickSignUp = {
                    navHostController.clearAndNavigate(HomeDestination.route)
                },
                onClickClose = {

                }
            )
        }
        composable(
            route = SignUpDestination.route,
            arguments = SignUpDestination.arguments
        ) {
            SignUpRoute(
                screenName = SignUpDestination.screenName,
                onBackPressed = onBackPressed,
                onClickSignUp = {
                    navHostController.navigateSingleTop(SignUpDestination.route)
                },
                onClickSignIn = {
                    navHostController.navigateSingleTop(SignInDestination.route)
                },
                onClickClose = {

                }
            )
        }
        composable(
            route = HomeDestination.route,
            arguments = HomeDestination.arguments
        ) {
            HomeRoute(
                screenName = HomeDestination.screenName,
                onBackPressed = onBackPressed
            )
        }
    }
}

fun NavHostController.popUp() {
    this.popBackStack()
}

fun NavHostController.navigateSingleTop(route: String) {
    this.navigate(route) { launchSingleTop = true }
}

fun NavHostController.navigateAndPopUp(route: String, popUp: String) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}

fun NavHostController.clearAndNavigate(route: String) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
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