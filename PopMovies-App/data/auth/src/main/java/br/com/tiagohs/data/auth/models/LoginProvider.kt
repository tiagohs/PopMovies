package br.com.tiagohs.data.auth.models

import android.os.Parcelable
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import kotlinx.parcelize.Parcelize

@Parcelize
enum class LoginProvider: Parcelable {
    EMAIL,
    FACEBOOK,
    TWITTER,
    GOOGLE
}

internal fun LoginProvider.asFirebaseCredentialProvider(token: String, secret: String): AuthCredential? =
    when (this) {
        LoginProvider.GOOGLE -> GoogleAuthProvider.getCredential(token, null)
        LoginProvider.TWITTER -> TwitterAuthProvider.getCredential(token, secret)
        LoginProvider.FACEBOOK -> FacebookAuthProvider.getCredential(token)
        else -> null
    }