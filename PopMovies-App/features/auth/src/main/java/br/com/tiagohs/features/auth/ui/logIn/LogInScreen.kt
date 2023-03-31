package br.com.tiagohs.features.auth.ui.logIn

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.tiagohs.core.components.ui.logo.AppLogo
import br.com.tiagohs.core.components.ui.preview.Pixel4Preview
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.core.theme.ui.Screen
import br.com.tiagohs.features.auth.R
import br.com.tiagohs.features.auth.ui.components.AuthBackground

@Composable
fun LogInRoute(
    onBackPressed: () -> Unit,
    screenName: String,
    onClickHelp: () -> Unit = {},
    onClickSignIn: () -> Unit = {},
    onClickSignUp: () -> Unit = {},
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    LogInScreen(
        onBackPressed = onBackPressed,
        screenName = screenName,
        onClickHelp = onClickHelp,
        onClickSignIn = onClickSignIn,
        onClickSignUp = onClickSignUp,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun LogInScreen(
    onBackPressed: () -> Unit,
    screenName: String,
    onClickHelp: () -> Unit,
    onClickSignIn: () -> Unit,
    onClickSignUp: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Screen(
        statusBarTransparent = true,
        screenName = screenName,
        onBackPressed = onBackPressed,
        snackBarHostState = snackBarHostState
    ) { innerPadding ->
        Box {
            AuthBackground(
                imageResource = R.drawable.img_log_in_background
            )

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                LogInHeader(
                    onClickHelp = onClickHelp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                        .padding(24.dp)
                )

                LogInBody(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp)
                )

                LogInFooter(
                    onClickSignIn = onClickSignIn,
                    onClickSignUp = onClickSignUp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(24.dp)
                )
            }
        }
    }
}

@Composable
fun LogInHeader(
    modifier: Modifier = Modifier,
    onClickHelp: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppLogo(
            modifier = Modifier
                .fillMaxWidth(fraction = .5f),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer)
        )

        TextButton(
            onClick = onClickHelp
        ) {
            Text(
                text = stringResource(id = R.string.btn_help),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun LogInBody(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Navegue e encontre seus filmes favoritos!",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Não fique de fora!",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
        )
        Text(
            text = "Encontre os lançamentos da semana, filmografias, curiosidades e muito mais.",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LogInFooter(
    modifier: Modifier = Modifier,
    onClickSignIn: () -> Unit = {},
    onClickSignUp: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Button(
            onClick = onClickSignIn,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.btn_sign_in))
        }
        TextButton(
            onClick = onClickSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_sign_up),
                color = Color.White
            )
        }
    }
}

@Pixel4Preview
@Composable
fun PreviewLogInScreen() {
    PopMoviesTheme {
        LogInRoute(
            screenName = "Login",
            onBackPressed = {}
        )
    }
}