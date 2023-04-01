package br.com.tiagohs.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import br.com.tiagohs.features.home.models.HomeBottomItem
import br.com.tiagohs.features.home.ui.HomeRoute
import br.com.tiagohs.features.profile.ui.ProfileRoute
import br.com.tiagohs.features.search.ui.SearchRoute
import br.com.tiagohs.features.weekfeatures.ui.WeekFeaturesRoute
import br.com.tiagohs.popmovies.ui.main.MainHomeRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun PopMoviesNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startScreenRoute: String = destinations.first().route,
    onBackPressed: () -> Unit = {
        navHostController.popUp()
    }
) {
    NavHost(
        navController = navHostController,
        startDestination = startScreenRoute,
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
                navigateToHome = {
                    navHostController.navigateSingleTop(HomeDestination.route)
                },
                navigateToSignUp = {
                    navHostController.navigateSingleTop(SignUpDestination.route)
                },
                navigateToForgotPassword = {

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
                navigateToSignIn = {
                    navHostController.navigateSingleTop(SignInDestination.route)
                },
                navigateToHome = {
                    navHostController.clearAndNavigate(HomeDestination.route)
                }
            )
        }
        composable(
            route = MainHomeDestination.route,
            arguments = MainHomeDestination.arguments
        ) {
            val homeNavController = rememberNavController()

            MainHomeRoute(
                navHost = {
                    HomeNavHost(
                        navHostController = homeNavController
                    )
                },
                bottomBarItems = bottomBarItems,
                onClickBottomNavigationItem = {
                    homeNavController.clearAndNavigate(it)
                }
            )
        }
    }
}


@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startScreenRoute: String = homeDestinations.first().route,
    onBackPressed: () -> Unit = {
        navHostController.popUp()
    }
) {
    NavHost(
        navController = navHostController,
        startDestination = startScreenRoute,
        modifier = modifier
    ) {
        composable(
            route = HomeDestination.route,
            arguments = HomeDestination.arguments
        ) {
            HomeRoute(
                onBackPressed = onBackPressed,
                screenName = HomeDestination.screenName
            )
        }
        composable(
            route = SearchDestination.route,
            arguments = SearchDestination.arguments
        ) {
            SearchRoute(
                onBackPressed = onBackPressed,
                screenName = HomeDestination.screenName
            )
        }
        composable(
            route = WeekFeaturesDestination.route,
            arguments = WeekFeaturesDestination.arguments
        ) {
            WeekFeaturesRoute(
                onBackPressed = onBackPressed,
                screenName = WeekFeaturesDestination.screenName
            )
        }
        composable(
            route = ProfileDestination.route,
            arguments = ProfileDestination.arguments
        ) {
            ProfileRoute(
                onBackPressed = onBackPressed,
                screenName = ProfileDestination.screenName
            )
        }
    }
}

val bottomBarItems = listOf(
    HomeBottomItem(
        icon = Icons.Filled.Home,
        name = "Início",
        route = HomeDestination.route
    ),
    HomeBottomItem(
        icon = Icons.Filled.Search,
        name = "Procurar",
        route = SearchDestination.route
    ),
    HomeBottomItem(
        icon = Icons.Filled.DateRange,
        name = "Lançamentos",
        route = WeekFeaturesDestination.route
    ),
    HomeBottomItem(
        icon = Icons.Filled.Person,
        name = "Perfil",
        route = ProfileDestination.route
    )
)


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

        popUpTo(0) {
            inclusive = true
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