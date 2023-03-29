package br.com.tiagohs.features.auth.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

val accentStyle
    @Composable
    get() = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
        color = MaterialTheme.colorScheme.secondaryContainer
    )