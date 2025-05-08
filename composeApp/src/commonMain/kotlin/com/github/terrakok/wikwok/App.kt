package com.github.terrakok.wikwok

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import com.github.terrakok.wikwok.theme.AppTheme
import com.github.terrakok.wikwok.ui.WikipediaScreen
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

internal val Log = KotlinLogging.logger("WikWok")
internal val LocalImageLoader = compositionLocalOf<ImageLoader> { error("ImageLoader not provided") }

@Preview
@Composable
internal fun App(
    navController: NavHostController = rememberNavController()
) = AppTheme {
    val context = LocalPlatformContext.current
    val imageLoader = remember(context) { ImageLoader(context) }
    CompositionLocalProvider(
        LocalImageLoader provides imageLoader,
    ) {
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
}


@Serializable
internal data object MainScreen