package br.com.tiagohs.core.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.tiagohs.core.theme.ui.PopMoviesTheme

@Composable
fun AppTheme(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    PopMoviesTheme {
        Scaffold(
            modifier = modifier
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}