package br.com.tiagohs.features.auth.ui.signIn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.tiagohs.core.theme.AppContent
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.core.theme.ui.Screen
import br.com.tiagohs.features.auth.R
import br.com.tiagohs.features.auth.ui.components.AuthBackground
import br.com.tiagohs.features.auth.ui.components.AuthInput
import br.com.tiagohs.features.auth.ui.components.SignInUpHeader
import br.com.tiagohs.features.auth.ui.components.accentStyle

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onClickSignIn: () -> Unit = {},
    onClickForgotPassword: () -> Unit = {},
    onClickSignUp: () -> Unit = {},
    onClickClose: () -> Unit
) {
    Screen(
        statusBarTransparent = true,
        innerPadding = innerPadding
    ) {
        Box(
            modifier = modifier
        ) {
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
                    onClickSignIn = onClickSignIn,
                    onClickSignUp = onClickSignUp,
                    onClickForgotPassword = onClickForgotPassword
                )
            }
        }
    }
}

@Composable
fun SignInBody(
    modifier: Modifier = Modifier,
    onClickSignIn: () -> Unit = {},
    onClickForgotPassword: () -> Unit = {},
    onClickSignUp: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        var email by rememberSaveable {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }

        AuthInput(
            value = email,
            labelResource = R.string.sign_in_input_email_label,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .padding(
                    top = 42.dp,
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

        TextButton(
            onClick = onClickForgotPassword,
        ) {
            Text(
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }

        Button(
            onClick = onClickSignIn,
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    end = 16.dp,
                    start = 16.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.btn_sign_in),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        DoesntHasAccount(
            onClickSignUp = onClickSignUp,
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
    AppContent(onBackPressed = {}) {
        SignInScreen(
            innerPadding = it,
            onClickClose = {},
            onClickForgotPassword = {},
            onClickSignUp = {},
            onClickSignIn = {}
        )
    }
}