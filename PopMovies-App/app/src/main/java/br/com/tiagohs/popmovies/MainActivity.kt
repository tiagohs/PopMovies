package br.com.tiagohs.popmovies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import br.com.tiagohs.core.navigation.PopMoviesNavHost
import br.com.tiagohs.core.navigation.destinations.LogInDestination
import br.com.tiagohs.core.navigation.models.Destination
import br.com.tiagohs.core.navigation.popUp
import br.com.tiagohs.core.theme.ui.PopMoviesTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val START_SCREEN = "START_SCREEN"

        fun newIntent(context: Context?, startScreenRoute: String) =
            Intent(context, MainActivity::class.java)
                .putExtra(START_SCREEN, startScreenRoute)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startScreen = intent.extras?.getString(START_SCREEN) ?: LogInDestination.route

        setContent {
            PopMoviesApp(startScreen)
        }
    }
}

@Composable
fun PopMoviesApp(
    startScreen: String
) {
    PopMoviesTheme {
        val navController = rememberNavController()

        PopMoviesNavHost(
            navHostController = navController,
            startScreenRoute = startScreen
        )
    }
}