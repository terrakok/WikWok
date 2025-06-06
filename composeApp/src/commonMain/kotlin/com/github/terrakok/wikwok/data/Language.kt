package com.github.terrakok.wikwok.data

import kotlinx.serialization.Serializable

/**
 * Data class representing a language with its name, locale code, and RTL flag
 */
@Serializable
data class Language(
    val name: String,
    val code: String,
    val isRtl: Boolean = false
)

val EnglishLanguage = Language("English", "en")

/**
 * List of the 50 most popular languages with their locale codes (sorted alphabetically)
 */
val popularLanguages = listOf(
    Language("Arabic", "ar", isRtl = true),
    Language("Bengali", "bn"),
    Language("Bulgarian", "bg"),
    Language("Catalan", "ca"),
    Language("Chinese", "zh"),
    Language("Croatian", "hr"),
    Language("Czech", "cs"),
    Language("Danish", "da"),
    Language("Dutch", "nl"),
    EnglishLanguage,
    Language("Estonian", "et"),
    Language("Filipino", "tl"),
    Language("Finnish", "fi"),
    Language("French", "fr"),
    Language("German", "de"),
    Language("Greek", "el"),
    Language("Gujarati", "gu"),
    Language("Hebrew", "he", isRtl = true),
    Language("Hindi", "hi"),
    Language("Hungarian", "hu"),
    Language("Indonesian", "id"),
    Language("Italian", "it"),
    Language("Japanese", "ja"),
    Language("Kannada", "kn"),
    Language("Korean", "ko"),
    Language("Latvian", "lv"),
    Language("Lithuanian", "lt"),
    Language("Malay", "ms"),
    Language("Malayalam", "ml"),
    Language("Marathi", "mr"),
    Language("Nepali", "ne"),
    Language("Norwegian", "no"),
    Language("Persian", "fa", isRtl = true),
    Language("Polish", "pl"),
    Language("Portuguese", "pt"),
    Language("Punjabi", "pa"),
    Language("Romanian", "ro"),
    Language("Russian", "ru"),
    Language("Serbian", "sr"),
    Language("Slovak", "sk"),
    Language("Slovenian", "sl"),
    Language("Spanish", "es"),
    Language("Swedish", "sv"),
    Language("Tamil", "ta"),
    Language("Telugu", "te"),
    Language("Thai", "th"),
    Language("Turkish", "tr"),
    Language("Ukrainian", "uk"),
    Language("Urdu", "ur", isRtl = true),
    Language("Vietnamese", "vi")
)
