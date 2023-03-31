package br.com.tiagohs.popmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import br.com.tiagohs.core.navigation.PopMoviesNavHost
import br.com.tiagohs.core.navigation.destinations.LogInDestination
import br.com.tiagohs.core.navigation.popUp
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

        PopMoviesNavHost(
            navHostController = navController,
            startScreen = LogInDestination,
            onBackPressed = {
                navController.popUp()
            }
        )
    }
}