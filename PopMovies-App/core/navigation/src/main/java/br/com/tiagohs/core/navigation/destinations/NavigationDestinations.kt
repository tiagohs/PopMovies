package br.com.tiagohs.core.navigation.destinations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.tiagohs.core.navigation.models.Destination

val destinations: List<Destination> = listOf(
    LogInDestination,
    SignInDestination,
    SignUpDestination,
    MainHomeDestination
)

val homeDestinations = listOf(
    HomeDestination,
    ProfileDestination,
    SearchDestination,
    WeekFeaturesDestination
)

object LogInDestination: Destination {
    override val route: String = "logIn"
    override val arguments: List<NamedNavArgument> = emptyList()

    override val screenName: String = ""
    override val withToolbar: Boolean = false
}

object SignInDestination: Destination {
    override val route: String = "signin"
    override val arguments: List<NamedNavArgument> = emptyList()

    override val screenName: String = "Logar"
    override val withToolbar: Boolean = false
}

object SignUpDestination: Destination {
    override val route: String = "signup"
    override val arguments: List<NamedNavArgument> = emptyList()

    override val screenName: String = "Cadastro"
    override val withToolbar: Boolean = false
}

object MainHomeDestination: Destination {
    override val route: String = "MainHome"
    override val arguments: List<NamedNavArgument> = emptyList()

    override val screenName: String = "MainHome"
    override val withToolbar: Boolean = true
}

object HomeDestination: Destination {
    override val route: String = "home"
    override val arguments: List<NamedNavArgument> = emptyList()

    override val screenName: String = "Home"
    override val withToolbar: Boolean = true
}

object ProfileDestination: Destination {
    override val route: String = "profile"
    override val arguments: List<NamedNavArgument> = emptyList()

    override val screenName: String = "Perfil"
    override val withToolbar: Boolean = true
}

object SearchDestination: Destination {
    override val route: String = "search"
    override val arguments: List<NamedNavArgument> = emptyList()

    override val screenName: String = "Procurar"
    override val withToolbar: Boolean = true
}

object WeekFeaturesDestination: Destination {
    override val route: String = "weekFeatures"
    override val arguments: List<NamedNavArgument> = emptyList()

    override val screenName: String = "Lançamentos da Semana"
    override val withToolbar: Boolean = true
}

@Composable
fun currentDestination(naviHostController: NavHostController): Destination {
    val currentBackStack by naviHostController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    return destinations.find { it.route == currentDestination?.route } ?: SignInDestination
}