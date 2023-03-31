package br.com.tiagohs.features.auth.ui.signUp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
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
    navigateToHome: () -> Unit = {},
    navigateToSignIn: () -> Unit = {},
    signUpViewModel: SignUpViewModel = koinViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by signUpViewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        onErrorDismiss = signUpViewModel::onErrorDismiss,
        onBackPressed = onBackPressed,
        onClickClose = onBackPressed,
        screenName = screenName,
        onClickSignUp = {
            signUpViewModel.onClickSignUp {
                navigateToHome()
            }

        },
        onClickSignIn = {
            navigateToSignIn()
        },
        onNameChange = signUpViewModel::onNameChange,
        onEmailChange = signUpViewModel::onEmailChange,
        onPasswordChange = signUpViewModel::onPasswordChange,
        onConfirmPasswordChange = signUpViewModel::onConfirmPasswordChange
    )
}

@Composable
fun SignUpScreen(
    uiState: SignUpUIState,
    snackBarHostState: SnackbarHostState,
    onErrorDismiss: (String) -> Unit,
    onBackPressed: () -> Unit,
    screenName: String,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit,
    onClickClose: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
) {
    Screen(
        screenName = screenName,
        onBackPressed = onBackPressed,
        errorMessage = uiState.errorMessage,
        snackBarHostState = snackBarHostState,
        onErrorDismiss = onErrorDismiss
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
                    uiState = uiState,
                    onClickSignIn = onClickSignIn,
                    onClickSignUp = onClickSignUp,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onConfirmPasswordChange = onConfirmPasswordChange,
                    onNameChange = onNameChange
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpBody(
    uiState: SignUpUIState,
    modifier: Modifier = Modifier,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AuthInput(
            value = uiState.name,
            isError = uiState.isNameFieldError,
            labelResource = R.string.sign_in_input_name_label,
            onValueChange = onNameChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(
                    top = 42.dp,
                )
                .fillMaxWidth()
        )

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
                    top = 16.dp,
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
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                )
                .fillMaxWidth()
        )

        AuthInput(
            value = uiState.confirmPassword,
            isError = uiState.isConfirmPasswordFieldError,
            labelResource = R.string.sign_in_input_confirm_password_label,
            onValueChange = onConfirmPasswordChange,
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

        Button(
            onClick = {
                onClickSignUp()

                keyboardController?.hide()
            },
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 32.dp
                )
                .fillMaxWidth()
        ) {
            if (uiState.isLoadingSignUp) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Text(
                    text = stringResource(id = R.string.btn_sign_up),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        AlreadyHasAccount(
            onClickSignIn = {
                onClickSignIn()

                keyboardController?.hide()
            },
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
            navigateToHome = {},
            navigateToSignIn = {}
        )
    }
}