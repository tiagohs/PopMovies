package br.com.tiagohs.features.profile.ui

data class ProfileUIState(
    val isLoading: Boolean = false,
    val errorMessage: List<String> = emptyList()
)
