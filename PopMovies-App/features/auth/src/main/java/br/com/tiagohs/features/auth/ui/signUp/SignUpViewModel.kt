package br.com.tiagohs.features.auth.ui.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tiagohs.core.helpers.extensions.isValidEmail
import br.com.tiagohs.core.helpers.extensions.isValidPassword
import br.com.tiagohs.core.helpers.extensions.passwordMatches
import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.auth.useCases.CreateUserFromEmailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val createUserFromEmailUseCase: CreateUserFromEmailUseCase
): ViewModel() {

    private val viewModelState = MutableStateFlow(SignUpUIState())

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    private val name
        get() = viewModelState.value.name

    private val email
        get() = viewModelState.value.email

    private val password
        get() = viewModelState.value.password

    private val confirmPassword
        get() = viewModelState.value.confirmPassword

    fun onErrorDismiss(error: String) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessage.filterNot { it == error }

            currentUiState.copy(errorMessage = errorMessages)
        }
    }

    fun onNameChange(newValue: String = viewModelState.value.name) {
        viewModelState.update {
            it.copy(
                name = newValue,
                isNameFieldError = newValue.isEmpty()
            )
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
                isPasswordFieldError = !newValue.isValidPassword() && !newValue.passwordMatches(confirmPassword)
            )
        }
    }

    fun onConfirmPasswordChange(newValue: String = viewModelState.value.confirmPassword) {
        viewModelState.update {
            it.copy(
                confirmPassword = newValue,
                isConfirmPasswordFieldError = !newValue.passwordMatches(password)
            )
        }
    }

    fun onClickSignUp(onSuccess: () -> Unit) {
        viewModelScope.launch {
            onNameChange()
            onEmailChange()
            onPasswordChange()
            onConfirmPasswordChange()

            if (viewModelState.value.isNameFieldError ||
                viewModelState.value.isEmailFieldError ||
                viewModelState.value.isPasswordFieldError ||
                viewModelState.value.isConfirmPasswordFieldError) {

                viewModelState.update {
                    it.copy(
                        errorMessage = listOf("Alguns campos estão inválidos, tente novamente"),
                        isLoadingSignUp = false
                    )
                }
                return@launch
            }

            viewModelState.update { it.copy(isLoadingSignUp = true) }
            when (val result = createUserFromEmailUseCase(name, email, password)) {
                is ResultState.Success -> {
                    viewModelState.update { it.copy(isLoadingSignUp = false) }

                    onSuccess()
                }
                is ResultState.Error -> {
                    viewModelState.update {
                        val message = result.error?.message ?: "Erro no cadastro, tente novamente"

                        it.copy(
                            errorMessage = listOf(message),
                            isLoadingSignUp = false
                        )
                    }
                }
            }
        }
    }
}