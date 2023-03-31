package br.com.tiagohs.features.auth.ui.signIn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.core.theme.ui.Screen
import br.com.tiagohs.features.auth.R
import br.com.tiagohs.features.auth.ui.components.AuthBackground
import br.com.tiagohs.features.auth.ui.components.AuthInput
import br.com.tiagohs.features.auth.ui.components.SignInUpHeader
import br.com.tiagohs.features.auth.ui.components.accentStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInRoute(
    onBackPressed: () -> Unit,
    screenName: String,
    navigateToHome: () -> Unit = {},
    navigateToSignUp: () -> Unit = {},
    navigateToForgotPassword: () -> Unit = {},
    signInViewModel: SignInViewModel = koinViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by signInViewModel.uiState.collectAsStateWithLifecycle()

    SignInScreen(
        uiState = uiState,
        screenName = screenName,
        onErrorDismiss = signInViewModel::onErrorDismiss,
        onBackPressed = onBackPressed,
        onClickClose = onBackPressed,
        onClickSignIn = {
            signInViewModel.onSignInClick {
                navigateToHome()
            }
        },
        onClickSignUp = { navigateToSignUp() },
        onClickForgotPassword = { navigateToForgotPassword() },
        onPasswordChange = signInViewModel::onPasswordChange,
        onEmailChange = signInViewModel::onEmailChange,
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun SignInScreen(
    uiState: SignInUIState,
    onBackPressed: () -> Unit,
    screenName: String,
    onErrorDismiss: (String) -> Unit,
    onClickSignIn: () -> Unit,
    onClickForgotPassword: () -> Unit,
    onClickSignUp: () -> Unit,
    onClickClose: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Screen(
        statusBarTransparent = true,
        screenName = screenName,
        onBackPressed = onBackPressed,
        onErrorDismiss = onErrorDismiss,
        snackBarHostState = snackBarHostState,
        errorMessage = uiState.errorMessage
    ) { innerPadding ->
        Box {
            AuthBackground(
                imageResource = R.drawable.img_sign_inup_background
            )

            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                SignInUpHeader(
                    titleResource = R.string.sign_in_title,
                    onClickClose = onClickClose
                )

                SignInBody(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 32.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    uiState = uiState,
                    onClickSignIn = onClickSignIn,
                    onClickSignUp = onClickSignUp,
                    onClickForgotPassword = onClickForgotPassword,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInBody(
    modifier: Modifier = Modifier,
    uiState: SignInUIState,
    onClickSignIn: () -> Unit,
    onClickForgotPassword: () -> Unit,
    onClickSignUp: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {
        AuthInput(
            value = uiState.email,
            isError = uiState.isEmailFieldError,
            labelResource = R.string.sign_in_input_email_label,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(
                    top = 42.dp,
                )
                .fillMaxWidth()
        )

        AuthInput(
            value = uiState.password,
            isError = uiState.isPasswordFieldError,
            labelResource = R.string.sign_in_input_password_label,
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onClickSignIn()

                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                )
                .fillMaxWidth()
        )

        TextButton(
            onClick = {
                keyboardController?.hide()

                onClickForgotPassword()

            },
        ) {
            Text(
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }

        Button(
            onClick = {
                keyboardController?.hide()

                onClickSignIn()
            },
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    end = 16.dp,
                    start = 16.dp
                )
                .fillMaxWidth()
        ) {
            if (uiState.isLoadingSignIn) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Text(
                    text = stringResource(R.string.btn_sign_in),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        DoesntHasAccount(
            onClickSignUp = {
                keyboardController?.hide()

                onClickSignUp()
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DoesntHasAccount(
    modifier: Modifier = Modifier,
    onClickSignUp: () -> Unit = {}
) {
    val signUpText = buildAnnotatedString {
        append(stringResource(R.string.doesnt_has_account_1))

        withStyle(accentStyle) {
            append(stringResource(R.string.doesnt_has_account_2))
        }
    }
    TextButton(
        onClick = onClickSignUp,
        modifier = modifier
    ) {
        Text(
            text = signUpText,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White
            )
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SignInScreenPreview() {
    PopMoviesTheme {
        SignInRoute(
            screenName = "SignIn",
            onBackPressed = {},
            navigateToForgotPassword = {},
            navigateToHome = {},
            navigateToSignUp = {}
        )
    }
}