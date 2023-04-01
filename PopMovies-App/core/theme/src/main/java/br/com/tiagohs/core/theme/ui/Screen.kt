package br.com.tiagohs.core.theme.ui

import android.app.Activity
import android.graphics.Color
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.view.WindowCompat

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    statusBarTransparent: Boolean = false,
    withTopBar: Boolean = false,
    screenName: String = "PopMovies",
    onBackPressed: () -> Unit,
    onErrorDismiss: (String) -> Unit = {},
    errorMessage: List<String>? = null,
    snackBarHostState: SnackbarHostState,
    content: @Composable (PaddingValues) -> Unit
) {
    StatusBarTransparent(
        statusBarTransparent = statusBarTransparent,
        darkTheme = darkTheme
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                screenName = screenName,
                onBackPressed = onBackPressed,
                withTopBar = withTopBar
            )
        },
        content = { innerPadding ->
            content(innerPadding)
        },
        snackbarHost = {
            AppSnackBarHost(
                snackBarHostState = snackBarHostState
            )
        }
    )

    AppSnackBar(
        onErrorDismiss = onErrorDismiss,
        errorMessage = errorMessage,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun AppSnackBarHost(
    snackBarHostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = snackBarHostState,
        modifier = Modifier
            .systemBarsPadding(),
        snackbar = { snackbarData ->
            Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
        }
    )
}

@Composable
fun AppSnackBar(
    onErrorDismiss: (String) -> Unit = {},
    errorMessage: List<String>? = null,
    snackBarHostState: SnackbarHostState
) {
    if (!errorMessage.isNullOrEmpty()) {
        val message = remember { errorMessage.first() }
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        LaunchedEffect(message, snackBarHostState) {
            snackBarHostState.showSnackbar(
                message = message,
            )

            onErrorDismissState(message)
        }
    }
}

@Composable
fun StatusBarTransparent(
    statusBarTransparent: Boolean = false,
    darkTheme: Boolean
) {
    val view = LocalView.current
    (view.context as? Activity)?.window?.let { window ->
        if (!view.isInEditMode) {
            SideEffect {
                if (statusBarTransparent) {
                    //window.statusBarColor = Color.TRANSPARENT
                } else {
                    window.statusBarColor = LightColors.primary.toArgb()
                }

                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    screenName: String,
    onBackPressed: () -> Unit,
    withTopBar: Boolean = true
) {
    if (!withTopBar) {
        return
    }

    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { onBackPressed() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                text = screenName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


