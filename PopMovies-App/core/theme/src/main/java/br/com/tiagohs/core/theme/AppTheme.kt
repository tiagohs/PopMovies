package br.com.tiagohs.core.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.tiagohs.core.navigation.PopMoviesNavHost
import br.com.tiagohs.core.navigation.destinations.currentDestination

@Composable
fun AppTheme(
    modifier: Modifier = Modifier
) {
    PopMoviesTheme {
        val navController = rememberNavController()
        val currentDestination = currentDestination(naviHostController = navController)

        Scaffold(
            modifier = modifier
        ) { innerPadding ->
            PopMoviesNavHost(
                navHostController = navController,
                modifier = Modifier.padding(innerPadding),
                startScreen = currentDestination
            )
        }
    }
}