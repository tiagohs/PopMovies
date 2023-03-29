package br.com.tiagohs.data.movies.useCases

import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.data.movies.repository.MoviesRepository

interface GetTopRatedMoviesUseCase {
    suspend operator fun invoke(): ResultState<List<Movie>>
}

internal class GetTopRatedMoviesUseCaseImpl(
    private val moviesRepository: MoviesRepository
): GetTopRatedMoviesUseCase {
    override suspend fun invoke(): ResultState<List<Movie>> {
        return try {
            val movies = moviesRepository.topRated().results ?: emptyList()

            ResultState.Success(
                data = movies
            )
        } catch (ex: Exception) {
            ResultState.Error(
                error = ex
            )
        }
    }
}