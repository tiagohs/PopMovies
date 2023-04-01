package br.com.tiagohs.data.auth.repository

import br.com.tiagohs.data.auth.models.LoginProvider
import br.com.tiagohs.data.auth.models.User
import br.com.tiagohs.data.auth.models.asFirebaseCredentialProvider
import br.com.tiagohs.data.auth.models.asUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

internal const val COLLECTION_USERS = "users"

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
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

    override suspend fun signUp(name: String, email: String, password: String, loginProvider: LoginProvider): User {
        return auth.createUserWithEmailAndPassword(email, password)
            .await()
            .asUser(loginProvider = loginProvider)
    }

    override suspend fun isUserAuthenticate(): User? {
        if (auth.currentUser != null) {
            return User(
                email = auth.currentUser?.email ?: ""
            )
        }

        return null
    }

    override suspend fun createBasicUser(name: String, email: String, loginProvider: LoginProvider): User {
        val user = User(
            name = name,
            email = email,
            loginProvider = loginProvider
        )

        firestore.collection(COLLECTION_USERS)
                .document(email)
                .set(user)
                .await()

        return user
    }

    override suspend fun getUserInfo(email: String): User? {
        return firestore.collection(COLLECTION_USERS)
            .document(email)
            .get()
            .await()
            ?.toObject(User::class.java)
    }
}