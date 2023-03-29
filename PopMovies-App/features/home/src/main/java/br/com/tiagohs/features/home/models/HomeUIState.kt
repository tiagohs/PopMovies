package br.com.tiagohs.features.home.models

import br.com.tiagohs.data.movies.models.movie.Movie

sealed interface HomeUIState {
    class Success(
        val nowPlayingMovies: List<Movie>? = null,
        val popularMovies: List<Movie>? = null,
        val topRatedMovies: List<Movie>? = null,
        val upcomingMovies: List<Movie>? = null
    ): HomeUIState

    class Error(
        val error: Throwable? = null
    ): HomeUIState

    object Loading: HomeUIState
}
