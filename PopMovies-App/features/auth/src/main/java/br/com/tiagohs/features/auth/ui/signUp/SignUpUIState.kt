package br.com.tiagohs.features.auth.ui.signUp

data class SignUpUIState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null
)