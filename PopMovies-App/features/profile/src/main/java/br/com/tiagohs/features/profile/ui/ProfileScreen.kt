package br.com.tiagohs.features.profile.ui

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
fun ProfileRoute(
    onBackPressed: () -> Unit,
    screenName: String,
    profileViewModel: ProfileViewModel = koinViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        uiState = uiState,
        onErrorDismiss = profileViewModel::onErrorDismiss,
        onBackPressed = onBackPressed,
        screenName = screenName,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUIState,
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
            Text("Hello Profile")
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SignInScreenPreview() {
    PopMoviesTheme {
        ProfileRoute(
            screenName = "Início",
            onBackPressed = {},
            profileViewModel = viewModel()
        )
    }
}