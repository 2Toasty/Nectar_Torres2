package com.example.nectartorres.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Definir los nuevos colores primario y secundario
val PrimaryColor = Color(0xFF53B175)
val SecondaryColor = Color(0xFF489E67)  // Color secundario que mencionaste

// Esquema de colores para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = Pink80
)

// Esquema de colores para el tema claro
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = Pink40,
    surface = Color(0xFFFFFFFF),  // Color blanco para el 'surface'
    background = Color(0xFFFFFFFF),  // Color blanco para el 'background'
    onSurface = Color(0xFF000000),  // Texto en color negro sobre 'surface'
    onBackground = Color(0xFF000000)  // Texto en color negro sobre 'background'
    /* Otros colores por defecto que puedes sobrescribir
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    */
)



@Composable
fun NectarTorresTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
