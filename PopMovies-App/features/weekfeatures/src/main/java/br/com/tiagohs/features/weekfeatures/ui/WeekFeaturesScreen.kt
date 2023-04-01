package br.com.tiagohs.features.weekfeatures.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.core.theme.ui.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeekFeaturesRoute(
    onBackPressed: () -> Unit,
    screenName: String,
    viewModel: WeekFeaturesViewModel = koinViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WeekFeaturesScreen(
        uiState = uiState,
        onErrorDismiss = viewModel::onErrorDismiss,
        onBackPressed = onBackPressed,
        screenName = screenName,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun WeekFeaturesScreen(
    uiState: WeekFeaturesUIState,
    onErrorDismiss: (String) -> Unit,
    onBackPressed: () -> Unit,
    screenName: String,
    snackBarHostState: SnackbarHostState
) {
    Screen(
        screenName = screenName,
        onErrorDismiss = onErrorDismiss,
        onBackPressed = onBackPressed,
        snackBarHostState = snackBarHostState,
        errorMessage = uiState.errorMessage,
        withTopBar = true
    ) {
        Column(Modifier.padding(it)) {
            Text("Hello Week Features")
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SignInScreenPreview() {
    PopMoviesTheme {
        WeekFeaturesRoute(
            screenName = "Lançamentos",
            onBackPressed = {},
            viewModel = viewModel()
        )
    }
}