package br.com.tiagohs.data.auth

import br.com.tiagohs.data.auth.repository.AuthRepository
import br.com.tiagohs.data.auth.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val authModule = module {

    single {
        FirebaseAuth.getInstance()
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(get())
    }
}