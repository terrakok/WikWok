package com.github.terrakok.wikwok

import androidx.compose.ui.platform.ClipEntry
import com.github.terrakok.wikwok.data.LikedArticles
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf


actual fun createStore(name: String): KStore<LikedArticles> {
    return storeOf(name)
}

actual fun clipEntryOf(text: String): ClipEntry = ClipEntry.withPlainText(text)