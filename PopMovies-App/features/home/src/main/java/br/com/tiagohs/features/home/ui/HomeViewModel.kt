package br.com.tiagohs.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tiagohs.core.helpers.state.UIState
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.data.movies.useCases.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
): ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    val uiState =
        viewModelState
            .map(HomeViewModelState::toUIState)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                viewModelState.value.toUIState()
            )

    init {
        viewModelScope.launch {
            val result = getNowPlayingMoviesUseCase()

            viewModelState.update {
                when (result) {
                    is UIState.Success -> {
                        it.copy(
                            nowPlayingMovies = result.data,
                            isLoading = false
                        )
                    }
                    else -> {
                        it.copy(
                            errorMessage = "Houve um problema ao buscar os filmes",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}

private data class HomeViewModelState(
    val nowPlayingMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {

    fun toUIState(): UIState<List<Movie>> {
        if (isLoading) {
            return UIState.Loading()
        }

        if (nowPlayingMovies == null) {
            return UIState.Error(
                message = errorMessage
            )
        }

        return UIState.Success(
            data = nowPlayingMovies
        )
    }
}