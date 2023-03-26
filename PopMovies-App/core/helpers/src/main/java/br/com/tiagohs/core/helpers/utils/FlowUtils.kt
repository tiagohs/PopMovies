package br.com.tiagohs.core.helpers.utils

object FlowUtils {
    const val INITIAL_BACKOFF_TIME = 2000L
    const val MAS_RETRIES = 3L

    fun getBackoffDelay(attempt: Long) = INITIAL_BACKOFF_TIME * (attempt + 1)
}