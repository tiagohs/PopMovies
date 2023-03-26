package br.com.tiagohs.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.tiagohs.core.helpers.state.UIState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.data.movies.models.movie.Movie

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        homeUiState = homeUiState,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    homeUiState: UIState<List<Movie>>,
    modifier: Modifier = Modifier
) {
    Column {
        when (homeUiState) {
            is UIState.Success -> {
                homeUiState.data?.forEach {
                    Text(it.title ?: "")
                }
            }
            is UIState.Loading -> {
                CircularProgressIndicator()
            }
            else -> { Text("") }
        }
    }

}


@Preview(
    showBackground = true
)
@Composable
fun SignInScreenPreview() {
    PopMoviesTheme {
        HomeScreen(
            homeViewModel = viewModel()
        )
    }
}