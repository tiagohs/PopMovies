package br.com.tiagohs.features.auth.ui.signUp

data class SignUpUIState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val confirmPassword: String = "",

    val isNameFieldError: Boolean = false,
    val isEmailFieldError: Boolean = false,
    val isPasswordFieldError: Boolean = false,
    val isConfirmPasswordFieldError: Boolean = false,

    val isLoadingSignUp: Boolean = false,
    val errorMessage: List<String> = emptyList()
)