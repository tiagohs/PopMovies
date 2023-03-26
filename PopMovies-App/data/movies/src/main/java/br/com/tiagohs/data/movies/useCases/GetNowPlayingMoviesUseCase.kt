package br.com.tiagohs.data.movies.useCases

import br.com.tiagohs.core.helpers.state.UIState
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.data.movies.repository.MoviesRepository

interface GetNowPlayingMoviesUseCase {
    suspend operator fun invoke(): UIState<List<Movie>>
}

internal class GetNowPlayingMoviesUseCaseImpl(
    private val moviesRepository: MoviesRepository
): GetNowPlayingMoviesUseCase {
    override suspend fun invoke(): UIState<List<Movie>> {
        try {
            val movies = moviesRepository.nowPlaying().results ?: emptyList()

            return UIState.Success(
                data = movies
            )
        } catch (ex: Exception) {
            return UIState.Error()
        }
    }
}