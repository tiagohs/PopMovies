package br.com.tiagohs.popmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.tiagohs.core.navigation.PopMoviesNavHost
import br.com.tiagohs.core.navigation.destinations.SignInDestination
import br.com.tiagohs.core.navigation.destinations.destinations
import br.com.tiagohs.core.theme.AppContent
import br.com.tiagohs.core.theme.ui.PopMoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PopMoviesApp()
        }

    }
}

@Composable
fun PopMoviesApp() {
    PopMoviesTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentNavDestination = currentBackStack?.destination
        val currentDestination = destinations.find { it.route == currentNavDestination?.route } ?: SignInDestination

        AppContent(
            withTopBar = currentDestination.withToolbar,
            screenName = currentDestination.screenName,
            onBackPressed = {
                navController.popBackStack()
            }
        ) { innerPadding ->

            PopMoviesNavHost(
                navHostController = navController,
                modifier = Modifier.padding(innerPadding),
                startScreen = currentDestination
            )
        }
    }
}