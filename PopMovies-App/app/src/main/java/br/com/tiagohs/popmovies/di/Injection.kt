package br.com.tiagohs.popmovies.di

import br.com.tiagohs.data.auth.authModule
import br.com.tiagohs.data.movies.moviesModule
import br.com.tiagohs.data.movies.moviesNetworkModule
import br.com.tiagohs.features.home.homeModule
import br.com.tiagohs.features.signin.signInModule
import br.com.tiagohs.features.signup.signUpModule

object Injection {
    val modules = listOf(
        moviesModule,
        moviesNetworkModule,
        authModule,
        signInModule,
        signUpModule,
        homeModule
    )
}