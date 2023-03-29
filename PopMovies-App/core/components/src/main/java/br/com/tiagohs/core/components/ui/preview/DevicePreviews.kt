package br.com.tiagohs.core.components.ui.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4,
)
annotation class Pixel4Preview


@Preview(
    showBackground = true
)
annotation class AnyDevicePreview