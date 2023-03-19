package br.com.tiagohs.popmovies.di

import br.com.tiagohs.core.network.networkModule
import br.com.tiagohs.data.movies.moviesModule

object Injection {
    val modules = listOf(
        networkModule,
        moviesModule
    )
}