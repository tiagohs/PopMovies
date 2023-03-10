package br.com.tiagohs.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.tiagohs.core.navigation.destinations.SignInDestination
import br.com.tiagohs.core.navigation.destinations.destinations
import br.com.tiagohs.core.navigation.models.Destination
import br.com.tiagohs.features.signin.SignInScreen

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
            SignInScreen()
        }
    }
}