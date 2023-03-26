package br.com.tiagohs.features.signup

import br.com.tiagohs.features.signup.ui.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signUpModule = module {

    // SignUp
    viewModel {
        SignUpViewModel()
    }
}