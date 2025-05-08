package com.github.terrakok.wikwok

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.toClipEntry
import com.github.terrakok.wikwok.data.LikedArticles
import com.github.terrakok.wikwok.data.ShareService
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.manualFileKitCoreInitialization
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.resolve
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        FileKit.manualFileKitCoreInitialization(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val shareService = object : ShareService {
            override fun share(text: String) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                this@AppActivity.startActivity(shareIntent)
            }

        }

        setContent { App(shareService = shareService) }
    }
}

actual fun createStore(name: String): KStore<LikedArticles> {
    return storeOf(
        kotlinx.io.files.Path(FileKit.filesDir.resolve("$name.json").path)
    )
}

actual fun clipEntryOf(text: String): ClipEntry =
    ClipData.newPlainText("Share", text).toClipEntry()