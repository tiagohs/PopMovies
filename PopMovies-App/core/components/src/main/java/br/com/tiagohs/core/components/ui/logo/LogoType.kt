package br.com.tiagohs.core.components.ui.logo

import androidx.annotation.StringDef
import androidx.compose.ui.tooling.preview.Devices

object LogoTypes {
    const val LOGO = "logo"
    const val NAME = "name"
}

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    open = true,
    value = [
        LogoTypes.LOGO,
        LogoTypes.NAME
    ]
)
annotation class LogoType