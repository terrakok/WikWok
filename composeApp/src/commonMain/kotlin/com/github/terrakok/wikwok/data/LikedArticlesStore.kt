package com.github.terrakok.wikwok.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

/**
 * Store for managing liked Wikipedia articles
 */
class LikedArticlesStore {
    // Using a simple in-memory implementation with MutableStateFlow
    private val _likedArticlesState = MutableStateFlow(LikedArticles(emptyList()))

    /**
     * Get all liked articles
     */
    val likedArticles: Flow<List<WikipediaArticle>> = _likedArticlesState.map { it.articles }

    /**
     * Toggle like status for an article
     * @return true if article is now liked, false if unliked
     */
    suspend fun toggleLike(article: WikipediaArticle): Boolean = withContext(Dispatchers.Default) {
        val liked = _likedArticlesState.value
        val isCurrentlyLiked = liked.articles.any { it.id == article.id }

        if (isCurrentlyLiked) {
            // Remove from liked
            _likedArticlesState.value = liked.copy(
                articles = liked.articles.filter { it.id != article.id }
            )
            false
        } else {
            // Add to liked
            _likedArticlesState.value = liked.copy(
                articles = liked.articles + article
            )
            true
        }
    }
}

/**
 * Data class for storing liked articles
 */
@Serializable
data class LikedArticles(
    val articles: List<WikipediaArticle>
)
