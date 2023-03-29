package br.com.tiagohs.features.auth

import br.com.tiagohs.features.auth.ui.signIn.SignInViewModel
import br.com.tiagohs.features.auth.ui.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAuthModule = module {

    // SignIn
    viewModel {
        SignInViewModel()
    }

    // SignUp
    viewModel {
        SignUpViewModel()
    }
}