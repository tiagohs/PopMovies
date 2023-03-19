package br.com.tiagohs.features.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.tiagohs.core.theme.ui.PopMoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onClickSignUp: () -> Unit,
    onClickSignIn: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.1f
                )
            )
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cadastrar",
            style = MaterialTheme.typography.headlineLarge
        )
        val appTitleStyle = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        val textRegister = buildAnnotatedString {
            append("Cadastre-se e organize os seus ")
            withStyle(appTitleStyle) {
                append("filmes")
            }
            append(" favoritos!")
        }
        Text(
            text = textRegister,
            style = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    end = 32.dp,
                    start = 32.dp
                )
        )

        var name by rememberSaveable {
            mutableStateOf("")
        }
        var email by rememberSaveable {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }
        var confirmPassword by rememberSaveable {
            mutableStateOf("")
        }

        TextField(
            value = name,
            label = { Text("Digite o seu nome") },
            onValueChange = { name = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .padding(
                    top = 42.dp,
                    end = 32.dp,
                    start = 32.dp
                )
                .fillMaxWidth()
        )

        TextField(
            value = email,
            label = { Text("Digite o seu email") },
            onValueChange = { email = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    end = 32.dp,
                    start = 32.dp
                )
                .fillMaxWidth()
        )

        TextField(
            value = password,
            label = { Text("Digite a sua senha") },
            onValueChange = { password = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 16.dp
                )
        )

        TextField(
            value = confirmPassword,
            label = { Text("Confirme a sua senha") },
            onValueChange = { confirmPassword = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 16.dp
                )
        )

        val uriHandler = LocalUriHandler.current
        val termsText = buildAnnotatedString {
            append("Ao se cadastrar, você está concordando com a nossa ")

            pushStringAnnotation(
                tag = "policy",
                annotation = "https://google.com/policy"
            )
            withStyle(appTitleStyle) {
                append("política de privacidade")
            }
            pop()

            append(" e com nossos ")

            pushStringAnnotation(
                tag = "terms",
                annotation = "https://google.com/terms"
            )
            withStyle(appTitleStyle) {
                append("termos de uso")
            }
            pop()
        }
        ClickableText(
            text = termsText,
            onClick = {offset ->
                termsText.getStringAnnotations(tag = "policy", start = offset, end = offset).firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
                termsText.getStringAnnotations(tag = "terms", start = offset, end = offset).firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    end = 32.dp,
                    start = 32.dp
                )
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
                text = "Cadastrar",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        val textSignUpStyle = MaterialTheme.typography.bodySmall.toSpanStyle().copy(
            fontWeight = FontWeight.Bold
        )
        val signUpText = buildAnnotatedString {
            append("Ja possui um cadastro? ")

            withStyle(textSignUpStyle) {
                append("entre aqui!")
            }
        }
        TextButton(
            onClick = onClickSignIn,
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    bottom = 12.dp
                )
        ) {
            Text(
                text = signUpText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SignUpScreenPreview() {
    PopMoviesTheme {
        SignUpScreen(
            onClickSignUp = {},
            onClickSignIn = {}
        )
    }
}