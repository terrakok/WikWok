package com.github.terrakok.wikwok.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.terrakok.wikwok.LikedArticlesDestination
import com.github.terrakok.wikwok.data.popularLanguages
import com.github.terrakok.wikwok.likedArticlesStore
import com.github.terrakok.wikwok.settings
import com.github.terrakok.wikwok.wikipediaService
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import wikwok.composeapp.generated.resources.Res
import wikwok.composeapp.generated.resources.error_loading
import wikwok.composeapp.generated.resources.ic_family_star
import wikwok.composeapp.generated.resources.ic_favorite_fill
import wikwok.composeapp.generated.resources.loading
import wikwok.composeapp.generated.resources.retry

/**
 * Main screen for displaying Wikipedia articles in a TikTok-like fullscreen format
 */
@Composable
fun WikipediaScreen(
    navController: NavController
) {
    val viewModel = viewModel { WikipediaViewModel(wikipediaService, likedArticlesStore, settings) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val likedArticles by viewModel.likedArticles.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { uiState.articles.size + 1 }

    // Check if we need to load more articles when user scrolls to the end
    val shouldLoadMore = remember {
        derivedStateOf {
            // Load more when we're 3 pages away from the end
            pagerState.currentPage >= uiState.articles.size - 3
        }
    }

    // Load more articles when needed
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !uiState.isLoading) {
            viewModel.loadMoreArticles()
        }
    }

    // Update pager size when articles change
    LaunchedEffect(uiState.articles.size) {
        if (uiState.articles.isNotEmpty()) {
            pagerState.animateScrollToPage(
                pagerState.currentPage.coerceAtMost(uiState.articles.size - 1)
            )
        } else {
            pagerState.scrollToPage(0)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.articles.isEmpty() && uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(Res.string.loading),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            uiState.error != null && uiState.articles.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(Res.string.error_loading),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = { viewModel.loadMoreArticles() }
                        ) {
                            Text(stringResource(Res.string.retry))
                        }
                    }
                }
            }

            else -> {

                // TikTok-like fullscreen pager using VerticalPager
                VerticalPager(
                    state = pagerState,
                    beyondViewportPageCount = 1,
                    modifier = Modifier.fillMaxSize()
                ) { pageIndex ->
                    if (pageIndex < uiState.articles.size) {
                        val article = uiState.articles[pageIndex]
                        val isLiked = likedArticles.contains(article.id)
                        WikipediaArticleItem(
                            article = article,
                            modifier = Modifier.fillMaxSize(),
                            isLiked = isLiked,
                            onLikeClick = { viewModel.toggleLike(article) }
                        )
                    } else if (uiState.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
            }
        }

        // Language selector bottom sheet
        var isBottomSheetVisible by remember { mutableStateOf(false) }
        val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()

        LanguageSelectorBottomSheet(
            isVisible = isBottomSheetVisible,
            selectedLanguage = selectedLanguage,
            languages = popularLanguages,
            onLanguageSelected = {
                viewModel.changeLanguage(it)
                isBottomSheetVisible = false
            },
            onDismiss = { isBottomSheetVisible = false }
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val uriHandler = LocalUriHandler.current
            // App page button
            IconButton(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(50))
                    .size(36.dp),
                onClick = { uriHandler.openUri("https://github.com/terrakok/Wikwok") }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = vectorResource(Res.drawable.ic_family_star),
                    contentDescription = "App page",
                    tint = Color.White
                )
            }

            // Language selector button
            IconButton(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(50))
                    .size(36.dp),
                onClick = { isBottomSheetVisible = true }
            ) {
                Text(
                    text = selectedLanguage.code.uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Favorites button
            IconButton(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(50))
                    .size(36.dp),
                onClick = { navController.navigate(LikedArticlesDestination) }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = vectorResource(Res.drawable.ic_favorite_fill),
                    contentDescription = "Favorites",
                    tint = Color.White
                )
            }
        }

    }
}
