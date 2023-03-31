package br.com.tiagohs.features.auth.ui.signIn

data class SignInUIState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null
)