package br.com.tiagohs.features.home.models

import br.com.tiagohs.data.movies.models.movie.Movie

sealed interface HomeUIState {

    val errorMessage: List<String>

    data class Success(
        val nowPlayingMovies: List<Movie>? = null,
        val popularMovies: List<Movie>? = null,
        val topRatedMovies: List<Movie>? = null,
        val upcomingMovies: List<Movie>? = null,
        override val errorMessage: List<String> = emptyList()
    ): HomeUIState

    class Error(
        override val errorMessage: List<String> = emptyList()
    ): HomeUIState

    object Loading: HomeUIState {
        override val errorMessage: List<String> = emptyList()
    }
}
