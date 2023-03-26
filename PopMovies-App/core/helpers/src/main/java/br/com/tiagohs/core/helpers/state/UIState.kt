package br.com.tiagohs.core.helpers.state

sealed class UIState<T> {
    class Success<T>(
        val data: T? = null
    ) : UIState<T>()

    class Error<T>(
        val message: String? = null
    ) : UIState<T>()

    class Loading<T> : UIState<T>()
}