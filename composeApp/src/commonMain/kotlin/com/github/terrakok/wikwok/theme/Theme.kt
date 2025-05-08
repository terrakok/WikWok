package com.github.terrakok.wikwok.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.rememberDynamicColorScheme

@Composable
internal fun AppTheme(
    content: @Composable () -> Unit
) {
    SystemAppearance(false)
    val scheme = rememberDynamicColorScheme(
        seedColor = Color(0xFF5FBA9A),
        isDark = true,
        isAmoled = false,
        style = PaletteStyle.Monochrome
    )
    MaterialTheme(
        colorScheme = scheme,
        content = { Surface(content = content) }
    )
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
