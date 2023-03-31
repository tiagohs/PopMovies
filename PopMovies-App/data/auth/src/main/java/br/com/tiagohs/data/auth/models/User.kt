package br.com.tiagohs.data.auth.models

import android.os.Parcelable
import com.google.firebase.auth.AuthResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String = "",
    val name: String = "",
    val loginProvider: LoginProvider = LoginProvider.EMAIL
): Parcelable

fun AuthResult?.asUser(loginProvider: LoginProvider): User =
    User(
        email = this?.user?.email ?: "",
        loginProvider = loginProvider
    )