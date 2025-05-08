package com.github.terrakok.wikwok.data

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

/**
 * Store for managing liked Wikipedia articles
 */
class LikedArticlesStore(private val store: KStore<LikedArticles>) {
    /**
     * Get all liked articles
     */
    val likedArticles: Flow<List<WikipediaArticle>> = store.updates.map { it?.articles.orEmpty() }

    /**
     * Toggle like status for an article
     * @return true if article is now liked, false if unliked
     */
    suspend fun toggleLike(article: WikipediaArticle): Unit = withContext(Dispatchers.Default) {
        store.update {
            val liked = it?.articles.orEmpty()
            val isCurrentlyLiked = liked.any { it.id == article.id }

            if (isCurrentlyLiked) {
                LikedArticles(articles = liked.filter { it.id != article.id })
            } else {
                LikedArticles(articles = liked + article)
            }
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
