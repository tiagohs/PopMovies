package br.com.tiagohs.core.helpers.state

sealed class ResultState<out R> {
    class Success<out T>(
        val data: T? = null
    ) : ResultState<T>()

    class Error(
        val error: Throwable? = null
    ) : ResultState<Nothing>()
}
