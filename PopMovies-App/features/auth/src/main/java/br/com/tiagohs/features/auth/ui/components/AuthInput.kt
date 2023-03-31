package br.com.tiagohs.features.auth.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.tiagohs.core.components.ui.preview.AnyDevicePreview
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.features.auth.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthInput(
    modifier: Modifier = Modifier,
    value: String,
    labelResource: Int,
    isError: Boolean = false,
    onValueChange: (String) -> Unit = {_ -> },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = value,
        label = { Text(stringResource(id = labelResource)) },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Campo Inválido",
                    textAlign = TextAlign.End,
                )
            }
        },
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorTextColor = MaterialTheme.colorScheme.outlineVariant,
            focusedTextColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.outlineVariant,
            focusedSupportingTextColor = MaterialTheme.colorScheme.outlineVariant,
            focusedLabelColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedLabelColor = MaterialTheme.colorScheme.outlineVariant
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        maxLines = 1,
        singleLine = true,
        modifier = modifier,
        keyboardActions = keyboardActions
    )
}

@AnyDevicePreview
@Composable
fun AuthInputPreview() {
    PopMoviesTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            AuthBackground(imageResource = R.drawable.img_sign_inup_background)

            var name by rememberSaveable {
                mutableStateOf("")
            }

            AuthInput(
                value = name,
                labelResource = R.string.sign_in_input_name_label,
                onValueChange = { name = it },
                modifier = Modifier
                    .padding(
                        top = 42.dp,
                    )
                    .fillMaxWidth()
            )
        }
    }
}