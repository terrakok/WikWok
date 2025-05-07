package com.github.terrakok.wikwok.data

import kotlinx.serialization.Serializable

/**
 * Data model representing a Wikipedia article
 */
@Serializable
data class WikipediaArticle(
    val id: Int,
    val title: String,
    val url: String,
    val extract: String = "",
    val thumbnail: String? = null
)
