package br.com.tiagohs.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.core.theme.ui.Screen
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.features.home.models.HomeUIState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    onBackPressed: () -> Unit,
    screenName: String,
    homeViewModel: HomeViewModel = koinViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        homeUiState = homeUiState,
        onBackPressed = onBackPressed,
        screenName = screenName,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun HomeScreen(
    homeUiState: HomeUIState,
    onBackPressed: () -> Unit,
    screenName: String,
    snackBarHostState: SnackbarHostState
) {
    Screen(
        screenName = screenName,
        onBackPressed = onBackPressed,
        snackBarHostState = snackBarHostState,
        errorMessage = homeUiState.errorMessage,
        withTopBar = true
    ) {
        Column(Modifier.padding(it)) {
            when (homeUiState) {
                is HomeUIState.Success -> {
                    NowPlayingMoviesList(
                        movies = homeUiState.nowPlayingMovies
                    )
                    PopularMoviesList(
                        movies = homeUiState.popularMovies
                    )
                    UpcomingMoviesList(
                        movies = homeUiState.upcomingMovies
                    )
                    TopRatedMoviesList(
                        movies = homeUiState.topRatedMovies
                    )
                }
                is HomeUIState.Loading -> {
                    CircularProgressIndicator()
                }
                else -> { Text("") }
            }
        }
    }
}

@Composable
fun NowPlayingMoviesList(
    movies: List<Movie>? = null,
    modifier: Modifier = Modifier
) {
    if (movies != null) {
        Text("Pegou nowPlayingMovies")
    }
}

@Composable
fun UpcomingMoviesList(
    movies: List<Movie>? = null,
    modifier: Modifier = Modifier
) {
    if (movies != null) {
        Text("Pegou upcoming")
    }
}

@Composable
fun TopRatedMoviesList(
    movies: List<Movie>? = null,
    modifier: Modifier = Modifier
) {
    if (movies != null) {
        Text("Pegou topRated")
    }
}

@Composable
fun PopularMoviesList(
    movies: List<Movie>? = null,
    modifier: Modifier = Modifier
) {
    if (movies != null) {
        Text("Pegou popular")
    }
}


@Preview(
    showBackground = true
)
@Composable
fun SignInScreenPreview() {
    PopMoviesTheme {
        HomeRoute(
            screenName = "Início",
            onBackPressed = {},
            homeViewModel = viewModel()
        )
    }
}