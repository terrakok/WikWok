package com.github.terrakok.wikwok.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.wikwok.data.Language
import com.github.terrakok.wikwok.data.LikedArticlesStore
import com.github.terrakok.wikwok.data.WikipediaArticle
import com.github.terrakok.wikwok.data.WikipediaService
import com.github.terrakok.wikwok.data.popularLanguages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for handling Wikipedia articles pagination
 */
class WikipediaViewModel(
    private val wikipediaService: WikipediaService,
    private val likedArticlesStore: LikedArticlesStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(WikipediaUiState())
    val uiState: StateFlow<WikipediaUiState> = _uiState.asStateFlow()

    // Map of article IDs to their liked status
    val likedArticles: StateFlow<Set<Int>> = likedArticlesStore.likedArticles
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    // Language selection state
    private val _selectedLanguage = MutableStateFlow(popularLanguages.first())
    val selectedLanguage: StateFlow<Language> = _selectedLanguage.asStateFlow()

    init {
        loadMoreArticles()
    }

    /**
     * Changes the selected language and reloads articles
     */
    fun changeLanguage(language: Language) {
        if (_selectedLanguage.value.code != language.code) {
            _selectedLanguage.value = language
            _uiState.value = WikipediaUiState() // Reset UI state
            loadMoreArticles()
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
                val languageCode = _selectedLanguage.value.code
                val newArticles = wikipediaService.getRandomArticles(30, languageCode)
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
            likedArticlesStore.toggleLike(article)
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
