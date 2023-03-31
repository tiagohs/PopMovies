package br.com.tiagohs.core.helpers.extensions

import android.util.Patterns

const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= MIN_PASS_LENGTH
}

fun String.passwordMatches(repeated: String): Boolean {
    return this.isNotEmpty() && this == repeated
}