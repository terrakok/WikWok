package com.github.terrakok.wikwok.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.terrakok.wikwok.likedArticlesStore

/**
 * Screen that displays all liked articles
 */
@Composable
fun LikedArticlesScreen(
    navController: NavController
) {
    val viewModel = viewModel { LikedArticlesViewModel(likedArticlesStore) }
    val likedArticles by viewModel.likedArticles.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { likedArticles.size }

    // Update pager size when articles change
    LaunchedEffect(likedArticles.size) {
        if (likedArticles.isNotEmpty()) {
            pagerState.animateScrollToPage(
                pagerState.currentPage.coerceAtMost(likedArticles.size - 1)
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        if (likedArticles.isEmpty()) {
            // Show a message when there are no liked articles
            Text(
                text = "No liked articles yet",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        } else {
            // TikTok-like fullscreen pager using VerticalPager
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                val article = likedArticles[pageIndex]
                WikipediaArticleItem(
                    article = article,
                    modifier = Modifier.fillMaxSize(),
                    isLiked = true,
                    onLikeClick = { viewModel.toggleLike(article) }
                )
            }
        }
    }
}