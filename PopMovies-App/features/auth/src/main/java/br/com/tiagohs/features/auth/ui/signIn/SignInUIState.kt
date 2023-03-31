package br.com.tiagohs.features.auth.ui.signIn

data class SignInUIState(
    val email: String = "",
    val password: String = "",

    val isEmailFieldError: Boolean = false,
    val isPasswordFieldError: Boolean = false,

    val isLoadingSignIn: Boolean = false,
    val errorMessage: List<String> = emptyList()
)