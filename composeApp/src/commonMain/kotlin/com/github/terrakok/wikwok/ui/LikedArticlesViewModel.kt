package com.github.terrakok.wikwok.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.wikwok.data.LikedArticlesStore
import com.github.terrakok.wikwok.data.WikipediaArticle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for handling Wikipedia articles pagination
 */
class LikedArticlesViewModel(
    private val likedArticlesStore: LikedArticlesStore
) : ViewModel() {
    val likedArticles = likedArticlesStore.likedArticles
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    /**
     * Toggle like status for an article
     */
    fun toggleLike(article: WikipediaArticle) {
        viewModelScope.launch {
            likedArticlesStore.toggleLike(article)
        }
    }
}
