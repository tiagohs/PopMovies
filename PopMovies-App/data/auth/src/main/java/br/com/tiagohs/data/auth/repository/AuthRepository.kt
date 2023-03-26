package br.com.tiagohs.data.auth.repository

import br.com.tiagohs.data.auth.models.LoginProvider
import br.com.tiagohs.data.auth.models.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): User
    suspend fun signIn(
        loginProvider: LoginProvider,
        token: String,
        secret: String = ""
    ): User
}