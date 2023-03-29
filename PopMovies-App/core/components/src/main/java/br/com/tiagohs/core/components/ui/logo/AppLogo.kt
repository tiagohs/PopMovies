package br.com.tiagohs.core.components.ui.logo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.tiagohs.core.components.R
import br.com.tiagohs.core.components.ui.preview.Pixel4Preview
import br.com.tiagohs.core.theme.ui.PopMoviesTheme

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    @LogoType logoType: String = LogoTypes.NAME,
    colorFilter: ColorFilter? = null
) {
    val imageResource = if (logoType == LogoTypes.LOGO) R.drawable.ic_logo else R.drawable.img_logo_name

    Box {
        Image(
            painter = painterResource(imageResource),
            contentDescription = stringResource(R.string.app_logo_content_description),
            modifier = modifier,
            colorFilter = colorFilter
        )
    }
}

@Pixel4Preview
@Composable
fun AppLogoIconPreview() {
    PopMoviesTheme {
        AppLogo(
            modifier = Modifier
                .size(
                    width = 30.dp,
                    height = 30.dp
                ),
            logoType = LogoTypes.LOGO
        )
    }
}

@Pixel4Preview
@Composable
fun AppLogoNamePreview() {
    PopMoviesTheme {
        AppLogo(
            modifier = Modifier
                .width(200.dp)
        )
    }
}