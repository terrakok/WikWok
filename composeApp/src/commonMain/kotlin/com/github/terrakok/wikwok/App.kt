package com.github.terrakok.wikwok

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import com.github.terrakok.wikwok.data.LikedArticles
import com.github.terrakok.wikwok.data.LikedArticlesStore
import com.github.terrakok.wikwok.data.WikipediaService
import com.github.terrakok.wikwok.theme.AppTheme
import com.github.terrakok.wikwok.ui.LikedArticlesScreen
import com.github.terrakok.wikwok.ui.WikipediaScreen
import com.russhwolf.settings.Settings
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.xxfast.kstore.KStore
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import wikwok.composeapp.generated.resources.Res
import wikwok.composeapp.generated.resources.app_name
import wikwok.composeapp.generated.resources.ic_arrow_back

internal val Log = KotlinLogging.logger("WikWok")
internal val LocalImageLoader = compositionLocalOf<ImageLoader> { error("ImageLoader not provided") }
internal val wikipediaService = WikipediaService()
internal val likedArticlesStore = LikedArticlesStore(createStore("liked_articles_store"))
internal val settings = Settings()

internal expect fun createStore(name: String): KStore<LikedArticles>

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
            startDestination = MainDestination,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable<MainDestination> { WikipediaScreen(navController) }
            composable<LikedArticlesDestination> { LikedArticlesScreen(navController) }
        }

        val bs = navController.currentBackStack.collectAsStateWithLifecycle()
        val isBackVisible = bs.value.size > 2


        Row(
            modifier = Modifier
                .padding(16.dp)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
                )
                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(50))
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(isBackVisible) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }
    }
}


@Serializable
internal data object MainDestination

@Serializable
internal data object LikedArticlesDestination
