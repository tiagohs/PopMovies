package br.com.tiagohs.data.movies

import br.com.tiagohs.core.network.createBaseNetworkModule
import br.com.tiagohs.core.network.setup.addTMDBInterceptor
import br.com.tiagohs.data.movies.api.MovieApi
import br.com.tiagohs.data.movies.repository.MoviesRepository
import br.com.tiagohs.data.movies.repository.MoviesRepositoryImpl
import br.com.tiagohs.data.movies.useCases.*
import br.com.tiagohs.data.movies.useCases.GetNowPlayingMoviesUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal const val DATA_MOVIES_CLIENT = "dataMovies_client"
internal const val DATA_MOVIES_GSON = "dataMovies_gson"
internal const val DATA_MOVIES_RETROFIT = "dataMovies_retrofit"

val moviesNetworkModule = createBaseNetworkModule(
    clientName = DATA_MOVIES_CLIENT,
    gsonName = DATA_MOVIES_GSON,
    retrofitName = DATA_MOVIES_RETROFIT,
    baseUrl = BuildConfig.TMDB_BASE_URL,
    interceptors = listOf(
        addTMDBInterceptor()
    )
)

val moviesModule = module {

    // Movies Repository

    factory<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }

    // Use Cases
    factory<GetNowPlayingMoviesUseCase> {
        GetNowPlayingMoviesUseCaseImpl(get())
    }

    factory<GetUpcomingMoviesUseCase> {
        GetUpcomingMoviesUseCaseImpl(get())
    }

    factory<GetTopRatedMoviesUseCase> {
        GetTopRatedMoviesUseCaseImpl(get())
    }

    factory<GetPopularMoviesUseCase> {
        GetPopularMoviesUseCaseImpl(get())
    }

    factory<MovieApi> {
        get<Retrofit>(
            named(
                name = DATA_MOVIES_RETROFIT
            )
        ).create(MovieApi::class.java)
    }
}