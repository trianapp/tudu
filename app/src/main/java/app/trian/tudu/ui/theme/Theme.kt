package app.trian.tudu.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import logcat.LogPriority
import logcat.logcat
import kotlin.math.log


private val LightColorScheme = lightColors(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = PrimaryDark,
    surface = Surface,
    onSurface = OnSurface,
    background = Background,
    onBackground = OnBackground
)
private val DarkColorScheme = darkColors(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = Primary,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark
)

@Composable
fun TuduTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }
    SideEffect {
        logcat("gee",LogPriority.ERROR){darkTheme.toString()}
    }

    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = content
    )
}