package com.github.terrakok.wikwok

import com.github.terrakok.wikwok.data.LikedArticles
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf


actual fun createStore(name: String): KStore<LikedArticles> {
    return storeOf(name)
}