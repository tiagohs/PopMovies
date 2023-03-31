package br.com.tiagohs.features.auth.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.tiagohs.core.components.ui.logo.AppLogo
import br.com.tiagohs.core.components.ui.preview.Pixel4Preview
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.features.auth.R

@Composable
fun SignInUpHeader(
    modifier: Modifier = Modifier,
    titleResource: Int,
    onClickClose: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = titleResource),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            AppLogo(
                modifier = Modifier
                    .fillMaxWidth(fraction = .5f)
                    .padding(top = 5.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer)
            )
        }

        TextButton(
            onClick = onClickClose
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}

@Pixel4Preview
@Composable
fun SignInHeaderPreview() {
    PopMoviesTheme {
        Box {
            AuthBackground(imageResource = R.drawable.img_sign_inup_background)
            SignInUpHeader(titleResource = R.string.sign_in_title)
        }
    }
}


@Pixel4Preview
@Composable
fun SignUnHeaderPreview() {
    PopMoviesTheme {
        Box {
            AuthBackground(imageResource = R.drawable.img_sign_inup_background)
            SignInUpHeader(titleResource = R.string.sign_up_title)
        }
    }
}