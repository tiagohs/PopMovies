package br.com.tiagohs.features.signin

import br.com.tiagohs.features.signin.ui.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signInModule = module {

    // SignIn
    viewModel {
        SignInViewModel()
    }
}