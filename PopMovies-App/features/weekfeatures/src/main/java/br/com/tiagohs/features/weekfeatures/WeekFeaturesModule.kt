package br.com.tiagohs.features.weekfeatures

import br.com.tiagohs.features.weekfeatures.ui.WeekFeaturesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weekFeaturesModule = module {

    // Week Features
    viewModel {
        WeekFeaturesViewModel()
    }
}