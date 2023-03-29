package br.com.tiagohs.features.home.models

import br.com.tiagohs.data.movies.models.movie.Movie

data class HomeViewModelState(
    val nowPlayingMovies: List<Movie>? = null,
    val popularMovies: List<Movie>? = null,
    val topRatedMovies: List<Movie>? = null,
    val upcomingMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) {

    fun toUIState(): HomeUIState {
        if (isLoading) {
            return HomeUIState.Loading
        }

        if (error != null) {
            return HomeUIState.Error(
                error = error
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