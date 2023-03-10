package br.com.tiagohs.features.signin

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.tiagohs.core.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.1f
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Bem-vindo",
                style = MaterialTheme.typography.headlineLarge
            )

            val appTitleStyle = MaterialTheme.typography.headlineLarge.toSpanStyle().copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            val text = buildAnnotatedString {
                append("ao ")

                withStyle(appTitleStyle) {
                    append("PopMovies")
                }
            }
            Text(
                text = text,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var email by rememberSaveable {
                mutableStateOf("")
            }
            var password by rememberSaveable {
                mutableStateOf("")
            }
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
                    .padding(horizontal = 32.dp)
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

            TextButton(
                onClick = { /*TODO*/ },
            ) {
                Text(
                    text = "Esqueceu a senha?",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Entrar",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        val textSignUpStyle = MaterialTheme.typography.bodySmall.toSpanStyle().copy(
            fontWeight = FontWeight.Bold
        )
        val signUpText = buildAnnotatedString {
            append("Novo por aqui? ")

            withStyle(textSignUpStyle) {
                append("Cadastre-se!")
            }
        }
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        ) {
            Text(
                text = signUpText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun SigninScreenPreview() {
    AppTheme {
        SignInScreen(
            modifier = Modifier.padding(it)
        )
    }
}