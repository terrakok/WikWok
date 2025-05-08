package com.github.terrakok.wikwok.data

import kotlinx.serialization.Serializable

/**
 * Data class representing a language with its name and locale code
 */
@Serializable
data class Language(
    val name: String,
    val code: String
)

/**
 * List of the 50 most popular languages with their locale codes
 */
val popularLanguages = listOf(
    Language("English", "en"),
    Language("Spanish", "es"),
    Language("French", "fr"),
    Language("German", "de"),
    Language("Russian", "ru"),
    Language("Japanese", "ja"),
    Language("Chinese", "zh"),
    Language("Italian", "it"),
    Language("Portuguese", "pt"),
    Language("Arabic", "ar"),
    Language("Hindi", "hi"),
    Language("Korean", "ko"),
    Language("Dutch", "nl"),
    Language("Turkish", "tr"),
    Language("Polish", "pl"),
    Language("Swedish", "sv"),
    Language("Vietnamese", "vi"),
    Language("Indonesian", "id"),
    Language("Greek", "el"),
    Language("Thai", "th"),
    Language("Hebrew", "he"),
    Language("Czech", "cs"),
    Language("Romanian", "ro"),
    Language("Hungarian", "hu"),
    Language("Danish", "da"),
    Language("Finnish", "fi"),
    Language("Norwegian", "no"),
    Language("Ukrainian", "uk"),
    Language("Croatian", "hr"),
    Language("Slovak", "sk"),
    Language("Serbian", "sr"),
    Language("Bulgarian", "bg"),
    Language("Malay", "ms"),
    Language("Lithuanian", "lt"),
    Language("Slovenian", "sl"),
    Language("Estonian", "et"),
    Language("Latvian", "lv"),
    Language("Persian", "fa"),
    Language("Bengali", "bn"),
    Language("Catalan", "ca"),
    Language("Filipino", "tl"),
    Language("Urdu", "ur"),
    Language("Tamil", "ta"),
    Language("Telugu", "te"),
    Language("Malayalam", "ml"),
    Language("Kannada", "kn"),
    Language("Marathi", "mr"),
    Language("Gujarati", "gu"),
    Language("Punjabi", "pa"),
    Language("Nepali", "ne")
)