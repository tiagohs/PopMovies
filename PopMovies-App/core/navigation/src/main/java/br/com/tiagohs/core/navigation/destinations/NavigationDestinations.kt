package br.com.tiagohs.core.navigation.destinations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.tiagohs.core.navigation.models.Destination

val destinations: List<Destination> = listOf(
    SignInDestination
)

object SignInDestination: Destination {
    override val route: String = "signin"
    override val arguments: List<NamedNavArgument> = emptyList()
}

@Composable
fun currentDestination(naviHostController: NavHostController): Destination {
    val currentBackStack by naviHostController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    return destinations.find { it.route == currentDestination?.route } ?: SignInDestination
}