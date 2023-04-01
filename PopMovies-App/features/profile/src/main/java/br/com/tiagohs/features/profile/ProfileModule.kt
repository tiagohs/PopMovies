package br.com.tiagohs.features.profile

import br.com.tiagohs.features.profile.ui.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    // Profile
    viewModel {
        ProfileViewModel()
    }
}