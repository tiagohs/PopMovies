package br.com.tiagohs.core.theme.ui

import android.app.Activity
import android.graphics.Color
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun Screen(
    darkTheme: Boolean = isSystemInDarkTheme(),
    statusBarTransparent: Boolean = false,
    innerPadding: PaddingValues,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    (view.context as? Activity)?.window?.let { window ->
        if (!view.isInEditMode) {
            SideEffect {
                if (statusBarTransparent) {
                    //window.statusBarColor = Color.TRANSPARENT
                } else {
                    window.statusBarColor = LightColors.primary.toArgb()
                }

                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            }
        }
    }

    if (statusBarTransparent) {
        content()
    } else {
        Surface(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }

}