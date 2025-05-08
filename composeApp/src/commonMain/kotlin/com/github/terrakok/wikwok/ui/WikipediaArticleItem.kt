package com.github.terrakok.wikwok.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.github.terrakok.wikwok.data.WikipediaArticle
import org.jetbrains.compose.resources.vectorResource
import wikwok.composeapp.generated.resources.Res
import wikwok.composeapp.generated.resources.ic_favorite
import wikwok.composeapp.generated.resources.ic_share

@Composable
fun WikipediaArticleItem(
    article: WikipediaArticle,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = article.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            clipToBounds = true,
            imageLoader = ImageLoader(LocalPlatformContext.current),
            modifier = Modifier.fillMaxSize(),
        )
        // Main content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.7f))
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal)
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Row {
                    IconButton(onClick = {}) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = vectorResource(Res.drawable.ic_favorite),
                            contentDescription = "Like",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = vectorResource(Res.drawable.ic_share),
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.extract,
                color = Color.White.copy(alpha = 0.8f), // Slightly less opaque white
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            val uriHandler = LocalUriHandler.current
            OutlinedButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { uriHandler.openUri(article.url) }
            ) {
                Text(
                    text = "Read more \u2192", // Unicode right arrow
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
