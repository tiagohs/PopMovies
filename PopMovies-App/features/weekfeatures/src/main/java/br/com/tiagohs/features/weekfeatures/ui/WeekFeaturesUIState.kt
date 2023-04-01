package br.com.tiagohs.features.weekfeatures.ui

data class WeekFeaturesUIState(
    val isLoading: Boolean = false,
    val errorMessage: List<String> = emptyList()
)
