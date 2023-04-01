package br.com.tiagohs.popmovies.di

import br.com.tiagohs.core.components.componentsModule
import br.com.tiagohs.data.auth.authModule
import br.com.tiagohs.data.movies.moviesModule
import br.com.tiagohs.data.movies.moviesNetworkModule
import br.com.tiagohs.features.auth.featureAuthModule
import br.com.tiagohs.features.home.homeModule
import br.com.tiagohs.features.profile.profileModule
import br.com.tiagohs.features.search.searchModule
import br.com.tiagohs.features.weekfeatures.weekFeaturesModule
import br.com.tiagohs.popmovies.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Injection {
    private val appModule = module {

        viewModel {
            SplashViewModel(get())
        }
    }

    val modules = listOf(
        appModule,
        componentsModule,
        moviesModule,
        moviesNetworkModule,
        authModule,
        featureAuthModule,
        homeModule,
        profileModule,
        searchModule,
        weekFeaturesModule
    )
}