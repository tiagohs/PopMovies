package br.com.tiagohs.features.search.ui

data class SearchUIState(
    val isLoading: Boolean = false,
    val errorMessage: List<String> = emptyList()
)
