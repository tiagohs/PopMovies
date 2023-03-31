package br.com.tiagohs.features.home.models

import br.com.tiagohs.data.movies.models.movie.Movie

data class HomeViewModelState(
    val nowPlayingMovies: List<Movie>? = null,
    val popularMovies: List<Movie>? = null,
    val topRatedMovies: List<Movie>? = null,
    val upcomingMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val errorMessage: List<String> = emptyList()
) {

    fun toUIState(): HomeUIState {
        if (isLoading) {
            return HomeUIState.Loading
        }

        if (errorMessage.isNotEmpty()) {
            return HomeUIState.Error(
                errorMessage = errorMessage
            )
        }

        return HomeUIState.Success(
            nowPlayingMovies = nowPlayingMovies,
            popularMovies = popularMovies,
            topRatedMovies = topRatedMovies,
            upcomingMovies = upcomingMovies
        )
    }
}