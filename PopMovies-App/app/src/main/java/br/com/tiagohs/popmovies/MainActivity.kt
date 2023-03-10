package br.com.tiagohs.popmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.tiagohs.core.navigation.PopMoviesNavHost
import br.com.tiagohs.core.navigation.destinations.currentDestination
import br.com.tiagohs.core.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val currentDestination = currentDestination(naviHostController = navController)

            AppTheme { innerPadding ->
                PopMoviesNavHost(
                    navHostController = navController,
                    modifier = Modifier.padding(innerPadding),
                    startScreen = currentDestination
                )
            }
        }
    }
}