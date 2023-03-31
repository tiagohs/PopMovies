package br.com.tiagohs.data.auth.repository

import br.com.tiagohs.data.auth.models.LoginProvider
import br.com.tiagohs.data.auth.models.User

interface AuthRepository {
    suspend fun isUserAuthenticate(): User?
    suspend fun signIn(email: String, password: String): User
    suspend fun signIn(
        loginProvider: LoginProvider,
        token: String,
        secret: String = ""
    ): User
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        loginProvider: LoginProvider
    ): User
    suspend fun createBasicUser(name: String, email: String, loginProvider: LoginProvider): User

    suspend fun getUserInfo(email: String): User?
}