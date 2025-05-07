package com.github.terrakok.wikwok

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.terrakok.wikwok.theme.AppTheme
import com.github.terrakok.wikwok.theme.LocalThemeIsDark
import com.github.terrakok.wikwok.ui.WikipediaScreen
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import wikwok.composeapp.generated.resources.*

@Preview
@Composable
internal fun App() = AppTheme {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        WikipediaScreen()

        // Theme toggle button in the top-right corner
        var isDark by LocalThemeIsDark.current
        val icon = remember(isDark) {
            if (isDark) Res.drawable.ic_light_mode
            else Res.drawable.ic_dark_mode
        }

        IconButton(
            onClick = { isDark = !isDark },
            modifier = Modifier.padding(16.dp).align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = vectorResource(icon),
                contentDescription = "Toggle theme"
            )
        }
    }
}
