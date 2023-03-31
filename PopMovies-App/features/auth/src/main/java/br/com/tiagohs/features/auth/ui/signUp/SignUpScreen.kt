package br.com.tiagohs.features.auth.ui.signUp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.tiagohs.core.components.ui.preview.AnyDevicePreview
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.core.theme.ui.Screen
import br.com.tiagohs.features.auth.R
import br.com.tiagohs.features.auth.ui.components.AuthBackground
import br.com.tiagohs.features.auth.ui.components.AuthInput
import br.com.tiagohs.features.auth.ui.components.SignInUpHeader
import br.com.tiagohs.features.auth.ui.components.accentStyle
import org.koin.androidx.compose.koinViewModel

private const val TERMS_LINK_ID = "terms"
private const val POLICY_LINK_ID = "policy"

@Composable
fun SignUpRoute(
    onBackPressed: () -> Unit,
    screenName: String,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit,
    onClickClose: () -> Unit,
    signUpViewModel: SignUpViewModel = koinViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by signUpViewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        onBackPressed = onBackPressed,
        screenName = screenName,
        onClickSignUp = onClickSignUp,
        onClickSignIn = onClickSignIn,
        onClickClose = onClickClose,
    )
}

@Composable
fun SignUpScreen(
    uiState: SignUpUIState,
    snackBarHostState: SnackbarHostState,
    onBackPressed: () -> Unit,
    screenName: String,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit,
    onClickClose: () -> Unit
) {
    Screen(
        screenName = screenName,
        onBackPressed = onBackPressed,
        errorMessage = uiState.errorMessage,
        snackBarHostState = snackBarHostState
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
                    titleResource = R.string.sign_up_title,
                    onClickClose = onClickClose
                )

                SignUpBody(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 32.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    onClickSignIn = onClickSignIn,
                    onClickSignUp = onClickSignUp
                )
            }
        }
    }
}

@Composable
fun SignUpBody(
    modifier: Modifier = Modifier,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var name by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var confirmPassword by rememberSaveable { mutableStateOf("") }

        AuthInput(
            value = name,
            labelResource = R.string.sign_in_input_name_label,
            onValueChange = { name = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .padding(
                    top = 42.dp,
                )
                .fillMaxWidth()
        )

        AuthInput(
            value = email,
            labelResource = R.string.sign_in_input_email_label,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                )
                .fillMaxWidth()
        )

        AuthInput(
            value = password,
            labelResource = R.string.sign_in_input_password_label,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                )
                .fillMaxWidth()
        )

        AuthInput(
            value = confirmPassword,
            labelResource = R.string.sign_in_input_confirm_password_label,
            onValueChange = { confirmPassword = it },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                )
                .fillMaxWidth()
        )

        Button(
            onClick = onClickSignUp,
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 32.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.btn_sign_up),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        AlreadyHasAccount(
            onClickSignIn = onClickSignIn,
            modifier = Modifier
                .padding(
                    top = 5.dp,
                    bottom = 12.dp
                )
        )

        Terms(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    end = 32.dp,
                    start = 32.dp
                )
        )
    }
}

@Composable
fun AlreadyHasAccount(
    modifier: Modifier = Modifier,
    onClickSignIn: () -> Unit
) {
    val signUpText = buildAnnotatedString {
        append(stringResource(R.string.already_has_account_1))

        withStyle(accentStyle) {
            append(stringResource(R.string.already_has_account_2))
        }
    }
    TextButton(
        onClick = onClickSignIn,
        modifier = modifier
    ) {
        Text(
            text = signUpText,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}

@Composable
fun Terms(
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val termsText = buildAnnotatedString {
        append(stringResource(R.string.terms_1))

        pushStringAnnotation(
            tag = POLICY_LINK_ID,
            annotation = stringResource(R.string.policy_link)
        )
        withStyle(accentStyle) {
            append(stringResource(R.string.terms_2))
        }
        pop()

        append(stringResource(R.string.terms_3))

        pushStringAnnotation(
            tag = TERMS_LINK_ID,
            annotation = stringResource(R.string.terms_link)
        )
        withStyle(accentStyle) {
            append(stringResource(R.string.terms_4))
        }
        pop()
    }
    ClickableText(
        text = termsText,
        onClick = {offset ->
            termsText.getStringAnnotations(tag = POLICY_LINK_ID, start = offset, end = offset).firstOrNull()?.let { stringAnnotation ->
                uriHandler.openUri(stringAnnotation.item)
            }
            termsText.getStringAnnotations(tag = TERMS_LINK_ID, start = offset, end = offset).firstOrNull()?.let { stringAnnotation ->
                uriHandler.openUri(stringAnnotation.item)
            }
        },
        style = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Center,
            color = Color.White
        ),
        modifier = modifier
    )
}

@AnyDevicePreview
@Composable
fun SignUpScreenPreview() {
    PopMoviesTheme {
        SignUpRoute(
            screenName = "SignUp",
            onBackPressed = {},
            onClickSignUp = {},
            onClickSignIn = {},
            onClickClose = {}
        )
    }
}