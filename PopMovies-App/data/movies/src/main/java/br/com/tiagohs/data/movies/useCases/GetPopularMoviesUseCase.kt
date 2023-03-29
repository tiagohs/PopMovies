package br.com.tiagohs.data.movies.useCases

import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.movies.models.movie.Movie
import br.com.tiagohs.data.movies.repository.MoviesRepository

interface GetPopularMoviesUseCase {
    suspend operator fun invoke(): ResultState<List<Movie>>
}

internal class GetPopularMoviesUseCaseImpl(
    private val moviesRepository: MoviesRepository
): GetPopularMoviesUseCase {
    override suspend fun invoke(): ResultState<List<Movie>> {
        return try {
            val movies = moviesRepository.popular().results ?: emptyList()

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