package br.com.tiagohs.features.auth.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tiagohs.data.auth.useCases.CheckUserStateUserCase
import kotlinx.coroutines.flow.*

class SignInViewModel(
    private val checkUserStateUserCase: CheckUserStateUserCase
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

    fun onEmailChange(newValue: String) {
        viewModelState.update {
            it.copy(
                email = newValue
            )
        }
    }

    fun onPasswordChange(newValue: String) {
        viewModelState.update {
            it.copy(
                email = newValue
            )
        }
    }

    fun onSignInClick() {

    }
}