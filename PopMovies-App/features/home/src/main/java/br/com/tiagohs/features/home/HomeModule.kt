package br.com.tiagohs.features.home

import br.com.tiagohs.features.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    // Home
    viewModel {
        HomeViewModel(get(), get(), get(), get())
    }
}