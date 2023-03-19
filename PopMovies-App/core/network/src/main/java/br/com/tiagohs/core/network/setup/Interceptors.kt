package br.com.tiagohs.core.network.setup

import br.com.tiagohs.core.network.BuildConfig
import br.com.tiagohs.core.network.tmdb.QUERY_API_KEY
import okhttp3.Interceptor

fun addTMDBInterceptor() =
    Interceptor { chain ->
        val original = chain.request()
        val url = chain
            .request()
            .url
            .newBuilder()
            .addQueryParameter(QUERY_API_KEY, BuildConfig.TMDB_API_KEY)
            .build()
        val requestBuilder = original.newBuilder()
            .url(url)
            .build()

        chain.proceed(requestBuilder)
    }