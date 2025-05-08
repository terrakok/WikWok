package com.github.terrakok.wikwok.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.wikwok.data.LikedArticlesStore
import com.github.terrakok.wikwok.data.WikipediaArticle
import com.github.terrakok.wikwok.data.WikipediaService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for handling Wikipedia articles pagination
 */
class WikipediaViewModel(
    private val wikipediaService: WikipediaService = WikipediaService(),
    private val likedArticlesStore: LikedArticlesStore = LikedArticlesStore()
) : ViewModel() {

    private val _uiState = MutableStateFlow(WikipediaUiState())
    val uiState: StateFlow<WikipediaUiState> = _uiState.asStateFlow()

    // Map of article IDs to their liked status
    private val _likedArticles = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val likedArticles: StateFlow<Map<Int, Boolean>> = _likedArticles.asStateFlow()

    init {
        loadMoreArticles()
        // Load initial liked status
        viewModelScope.launch {
            likedArticlesStore.likedArticles.collect { likedArticlesList ->
                val likedMap = likedArticlesList.associate { it.id to true }
                _likedArticles.value = likedMap
            }
        }
    }

    /**
     * Loads more articles when user scrolls to the end of the list
     */
    fun loadMoreArticles() {
        if (_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val newArticles = wikipediaService.getRandomArticles(30)
                _uiState.update { currentState ->
                    currentState.copy(
                        articles = currentState.articles + newArticles,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    /**
     * Toggle like status for an article
     */
    fun toggleLike(article: WikipediaArticle) {
        viewModelScope.launch {
            val isLiked = likedArticlesStore.toggleLike(article)
            // Update the local liked status immediately for UI responsiveness
            _likedArticles.update { currentMap ->
                val newMap = currentMap.toMutableMap()
                if (isLiked) {
                    newMap[article.id] = true
                } else {
                    newMap.remove(article.id)
                }
                newMap
            }
        }
    }
}

/**
 * UI state for Wikipedia articles screen
 */
data class WikipediaUiState(
    val articles: List<WikipediaArticle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
