package br.com.tiagohs.data.movies

import br.com.tiagohs.core.network.setup.addTMDBInterceptor
import br.com.tiagohs.core.network.setup.createClient
import br.com.tiagohs.core.network.setup.createGson
import br.com.tiagohs.core.network.setup.createRetrofit
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val DATA_MOVIES_CLIENT = "dataMovies_client"
internal const val DATA_MOVIES_GSON = "dataMovies_gson"
internal const val DATA_MOVIES_RETROFIT = "dataMovies_retrofit"

val moviesModule = module {

    // Retrofit Setup
    val interceptors = listOf(
        addTMDBInterceptor()
    )

    factory(named(
        name = DATA_MOVIES_CLIENT
    )) {
        createClient(
            interceptors = interceptors
        )
    }
    factory(named(
        name = DATA_MOVIES_GSON
    )) {
        createGson()
    }
    single(named(
        name = DATA_MOVIES_RETROFIT
    )) {
        createRetrofit(
            get(named(name = DATA_MOVIES_CLIENT)),
            get(named(name = DATA_MOVIES_GSON)),
            BuildConfig.TMDB_BASE_URL
        )
    }


}