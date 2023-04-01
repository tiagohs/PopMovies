package br.com.tiagohs.features.weekfeatures.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class WeekFeaturesViewModel: ViewModel() {

    private val viewModelState = MutableStateFlow(WeekFeaturesUIState(isLoading = true))

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    fun onErrorDismiss(error: String) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessage.filterNot { it == error }

            currentUiState.copy(errorMessage = errorMessages)
        }
    }

}