package br.com.tiagohs.popmovies.di

import br.com.tiagohs.core.components.componentsModule
import br.com.tiagohs.data.auth.authModule
import br.com.tiagohs.data.movies.moviesModule
import br.com.tiagohs.data.movies.moviesNetworkModule
import br.com.tiagohs.features.auth.featureAuthModule
import br.com.tiagohs.features.home.homeModule

object Injection {
    val modules = listOf(
        componentsModule,
        moviesModule,
        moviesNetworkModule,
        authModule,
        featureAuthModule,
        homeModule
    )
}