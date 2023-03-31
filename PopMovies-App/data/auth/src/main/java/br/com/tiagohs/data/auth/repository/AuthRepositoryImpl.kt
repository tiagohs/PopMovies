package br.com.tiagohs.data.auth.repository

import br.com.tiagohs.data.auth.models.LoginProvider
import br.com.tiagohs.data.auth.models.User
import br.com.tiagohs.data.auth.models.asFirebaseCredentialProvider
import br.com.tiagohs.data.auth.models.asUser
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
): AuthRepository {

    override suspend fun signIn(
        loginProvider: LoginProvider,
        token: String,
        secret: String
    ): User {
        val loginCredentials = loginProvider.asFirebaseCredentialProvider(token, secret) ?: run {
            throw Exception("Erro ao gerar as suas credenciais")
        }

        return auth.signInWithCredential(loginCredentials)
            .await()
            .asUser(loginProvider = LoginProvider.EMAIL)
    }

    override suspend fun signIn(email: String, password: String): User {
        return auth.signInWithEmailAndPassword(email, password)
            .await()
            .asUser(loginProvider = LoginProvider.EMAIL)
    }

    override suspend fun signUp(name: String, email: String, password: String): User {
        return auth.createUserWithEmailAndPassword(email, password)
            .await()
            .asUser(loginProvider = LoginProvider.EMAIL)
    }

    override suspend fun isUserAuthenticate(): Boolean = auth.currentUser != null
}