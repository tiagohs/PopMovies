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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
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
    errorMessage: String? = null,
    snackBarHostState: SnackbarHostState,
    content: @Composable (PaddingValues) -> Unit
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
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = modifier
                    .systemBarsPadding(),
                snackbar = { snackbarData ->
                    Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                }
            )
        }
    )

    if (!errorMessage.isNullOrEmpty()) {
        val message = remember { errorMessage }

        LaunchedEffect(message, snackBarHostState) {
            snackBarHostState.showSnackbar(
                message = message,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    screenName: String,
    onBackPressed: () -> Unit,
    withTopBar: Boolean
) {
    if (!withTopBar) {
        return Unit
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


