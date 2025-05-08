package com.github.terrakok.wikwok

import androidx.compose.ui.platform.ClipEntry
import com.github.terrakok.wikwok.data.LikedArticles
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.resolve
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import java.awt.datatransfer.StringSelection

actual fun createStore(name: String): KStore<LikedArticles> {
    return storeOf(
        kotlinx.io.files.Path(FileKit.filesDir.resolve("$name.json").path)
    )
}

actual fun clipEntryOf(text: String): ClipEntry = ClipEntry(StringSelection(text))
