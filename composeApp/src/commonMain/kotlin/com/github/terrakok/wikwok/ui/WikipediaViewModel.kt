package com.github.terrakok.wikwok.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val wikipediaService: WikipediaService = WikipediaService()
) : ViewModel() {

    private val _uiState = MutableStateFlow(WikipediaUiState())
    val uiState: StateFlow<WikipediaUiState> = _uiState.asStateFlow()

    init {
        loadMoreArticles()
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
}

/**
 * UI state for Wikipedia articles screen
 */
data class WikipediaUiState(
    val articles: List<WikipediaArticle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)