package br.com.tiagohs.popmovies.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.auth.useCases.CheckUserStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkUserStateUseCase: CheckUserStateUseCase
): ViewModel() {

    fun checkSignIn(onSuccess: (isLogged: Boolean) -> Unit) {
        viewModelScope.launch {
            when (checkUserStateUseCase()) {
                is ResultState.Success -> onSuccess(true)
                is ResultState.Error -> onSuccess(false)
            }
        }
    }
}