package br.com.tiagohs.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.tiagohs.core.theme.AppContent
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.core.theme.ui.Screen
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.features.home.models.HomeUIState

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Screen(innerPadding = innerPadding) {
        val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            homeUiState = homeUiState,
            modifier = modifier
        )
    }
}

@Composable
fun HomeScreen(
    homeUiState: HomeUIState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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
    AppContent(onBackPressed = {}) {
        HomeScreen(
            innerPadding = it,
            homeViewModel = viewModel()
        )
    }
}