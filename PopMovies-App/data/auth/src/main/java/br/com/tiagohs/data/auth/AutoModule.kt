package br.com.tiagohs.data.auth

import br.com.tiagohs.data.auth.repository.AuthRepository
import br.com.tiagohs.data.auth.repository.AuthRepositoryImpl
import br.com.tiagohs.data.auth.useCases.*
import br.com.tiagohs.data.auth.useCases.CheckUserStateUseCaseImpl
import br.com.tiagohs.data.auth.useCases.SignInFromEmailUseCaseImpl
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val authModule = module {

    single {
        FirebaseAuth.getInstance().apply {
            setLanguageCode("pt")
        }
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    // Use Cases
    factory<CheckUserStateUseCase> {
        CheckUserStateUseCaseImpl(get())
    }

    factory<SignInFromEmailUseCase> {
        SignInFromEmailUseCaseImpl(get())
    }

    factory<CreateUserFromEmailUseCase> {
        CreateUserFromEmailUseCaseImpl(get())
    }
}