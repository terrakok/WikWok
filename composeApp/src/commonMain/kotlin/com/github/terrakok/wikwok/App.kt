package com.github.terrakok.wikwok

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.terrakok.wikwok.theme.AppTheme
import com.github.terrakok.wikwok.theme.LocalThemeIsDark
import com.github.terrakok.wikwok.ui.WikipediaScreen
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import wikwok.composeapp.generated.resources.Res
import wikwok.composeapp.generated.resources.ic_dark_mode
import wikwok.composeapp.generated.resources.ic_light_mode

internal val Log = KotlinLogging.logger("WikWok")

@Preview
@Composable
internal fun App(
    navController: NavHostController = rememberNavController()
) = AppTheme {
    NavHost(
        navController = navController,
        startDestination = MainScreen,
        modifier = Modifier.fillMaxSize(),
    ) {
       composable<MainScreen> { 
           WikipediaScreen()
       }
    }
}


@Serializable
internal data object MainScreen