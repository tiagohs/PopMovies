package br.com.tiagohs.features.auth.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tiagohs.core.helpers.extensions.isValidEmail
import br.com.tiagohs.core.helpers.extensions.isValidPassword
import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.auth.useCases.SignInFromEmailUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInFromEmailUserCase: SignInFromEmailUseCase
): ViewModel() {

    private val viewModelState = MutableStateFlow(SignInUIState())

    val uiState = viewModelState
                    .stateIn(
                        viewModelScope,
                        SharingStarted.Eagerly,
                        viewModelState.value
                    )

    private val email
        get() = viewModelState.value.email

    private val password
        get() = viewModelState.value.password

    fun onErrorDismiss(error: String) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessage.filterNot { it == error }

            currentUiState.copy(errorMessage = errorMessages)
        }
    }

    fun onEmailChange(newValue: String = viewModelState.value.email) {
        viewModelState.update {
            it.copy(
                email = newValue,
                isEmailFieldError = !newValue.isValidEmail()
            )
        }
    }

    fun onPasswordChange(newValue: String = viewModelState.value.password) {
        viewModelState.update {
            it.copy(
                password = newValue,
                isPasswordFieldError = !newValue.isValidPassword()
            )
        }
    }

    fun onSignInClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            onEmailChange()
            onPasswordChange()

            if (viewModelState.value.isPasswordFieldError ||
                viewModelState.value.isEmailFieldError) {

                viewModelState.update {
                    it.copy(
                        errorMessage = listOf("Alguns campos estão inválidos, tente novamente"),
                        isLoadingSignIn = false
                    )
                }
                return@launch
            }

            viewModelState.update { it.copy(isLoadingSignIn = true) }
            when (val result = signInFromEmailUserCase(email, password)) {
                is ResultState.Success -> {
                    viewModelState.update { it.copy(isLoadingSignIn = false) }

                    onSuccess()
                }
                is ResultState.Error -> {
                    viewModelState.update {
                        val message = result.error?.message ?: "Erro na autenticação, tente novamente"

                        it.copy(
                            errorMessage = listOf(message),
                            isLoadingSignIn = false
                        )
                    }
                }
            }
        }
    }
}