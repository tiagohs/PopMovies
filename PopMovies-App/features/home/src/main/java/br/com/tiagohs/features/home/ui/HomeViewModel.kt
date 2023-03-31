package br.com.tiagohs.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.data.movies.useCases.GetNowPlayingMoviesUseCase
import br.com.tiagohs.data.movies.useCases.GetPopularMoviesUseCase
import br.com.tiagohs.data.movies.useCases.GetTopRatedMoviesUseCase
import br.com.tiagohs.data.movies.useCases.GetUpcomingMoviesUseCase
import br.com.tiagohs.features.home.models.HomeViewModelState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
): ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    val uiState = viewModelState
                    .map(HomeViewModelState::toUIState)
                    .stateIn(
                        viewModelScope,
                        SharingStarted.Eagerly,
                        viewModelState.value.toUIState()
                    )

    init {
        fetchHomeData()
    }

    fun onErrorDismiss(error: String) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessage.filterNot { it == error }

            currentUiState.copy(errorMessage = errorMessages)
        }
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            try {
                launch { nowPlayingMovies() }
                launch { popularMovies() }
                launch { topRatedMovies() }
                launch { upcomingMovies() }
            } catch (ex: Exception) {
                viewModelState.update {
                    val errorMessages = it.errorMessage + "Houve um Problema ao buscar os filmes"

                    it.copy(
                        errorMessage = errorMessages,
                        isLoading = false
                    )
                }
            }
        }
    }

    private suspend fun nowPlayingMovies() {
        val result = getNowPlayingMoviesUseCase()

        validateResult(result) { movies ->
            viewModelState.update {
                it.copy(
                    nowPlayingMovies = movies,
                    isLoading = false
                )
            }
        }
    }

    private suspend fun popularMovies() {
        val result = getPopularMoviesUseCase()

        validateResult(result) { movies ->
            viewModelState.update {
                it.copy(
                    popularMovies = movies,
                    isLoading = false
                )
            }
        }
    }

    private suspend fun topRatedMovies() {
        val result = getTopRatedMoviesUseCase()

        validateResult(result) { movies ->
            viewModelState.update {
                it.copy(
                    topRatedMovies = movies,
                    isLoading = false
                )
            }
        }
    }

    private suspend fun upcomingMovies() {
        val result = getUpcomingMoviesUseCase()

        validateResult(result) { movies ->
            viewModelState.update {
                it.copy(
                    upcomingMovies = movies,
                    isLoading = false
                )
            }
        }
    }

    private fun validateResult(result: ResultState<List<Movie>>, onSuccess: (List<Movie>) -> Unit) {
        viewModelState.update {
            when (result) {
                is ResultState.Success -> {
                    onSuccess(result.data ?: emptyList())

                    it.copy(
                        isLoading = false
                    )
                }
                is ResultState.Error -> {
                    val errorMessages = it.errorMessage + (result.error?.message ?: "Houve um Problema ao buscar os filmes")

                    it.copy(
                        errorMessage = errorMessages,
                        isLoading = false
                    )
                }
                else -> {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}
