package br.com.tiagohs.data.auth

import br.com.tiagohs.data.auth.repository.AuthRepository
import br.com.tiagohs.data.auth.repository.AuthRepositoryImpl
import br.com.tiagohs.data.auth.useCases.CheckUserStateUserCase
import br.com.tiagohs.data.auth.useCases.CheckUserStateUserCaseImpl
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val authModule = module {

    single {
        FirebaseAuth.getInstance()
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    // Use Cases
    factory<CheckUserStateUserCase> {
        CheckUserStateUserCaseImpl(get())
    }
}