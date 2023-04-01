package br.com.tiagohs.features.search

import br.com.tiagohs.features.search.ui.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    // Search
    viewModel {
        SearchViewModel()
    }
}