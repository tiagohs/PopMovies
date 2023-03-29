package br.com.tiagohs.features.auth.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import br.com.tiagohs.core.components.ui.preview.Pixel4Preview
import br.com.tiagohs.core.theme.ui.PopMoviesTheme
import br.com.tiagohs.features.auth.R

@Composable
fun AuthBackground(
    imageResource: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(imageResource),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.8f)
            .background(MaterialTheme.colorScheme.onBackground)
    )
}

@Pixel4Preview
@Composable
fun SignInUpBackground() {
    PopMoviesTheme {
        AuthBackground(
            imageResource = R.drawable.img_sign_inup_background
        )
    }
}

@Pixel4Preview
@Composable
fun LogInBackground() {
    PopMoviesTheme {
        AuthBackground(
            imageResource = R.drawable.img_log_in_background
        )
    }
}


