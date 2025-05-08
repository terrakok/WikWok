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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.github.terrakok.wikwok.LocalImageLoader
import com.github.terrakok.wikwok.LocalShareService
import com.github.terrakok.wikwok.clipEntryOf
import com.github.terrakok.wikwok.data.WikipediaArticle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
import wikwok.composeapp.generated.resources.Res
import wikwok.composeapp.generated.resources.ic_arrow_right
import wikwok.composeapp.generated.resources.ic_favorite
import wikwok.composeapp.generated.resources.ic_favorite_fill
import wikwok.composeapp.generated.resources.ic_share

@Composable
fun WikipediaArticleItem(
    article: WikipediaArticle,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = article.thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            clipToBounds = true,
            imageLoader = LocalImageLoader.current,
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
                    IconButton(onClick = onLikeClick) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector =
                                if (isLiked) vectorResource(Res.drawable.ic_favorite_fill)
                                else vectorResource(Res.drawable.ic_favorite),
                            contentDescription = if (isLiked) "Unlike" else "Like",
                            tint = if (isLiked) Color.Red else Color.White
                        )
                    }
                    val shareService = LocalShareService.current
                    val clipboard = LocalClipboard.current
                    val coroutineScope = rememberCoroutineScope()
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                shareService?.share(article.url)
                                    ?: clipboard.setClipEntry(clipEntryOf(article.url))
                            }
                        }
                    ) {
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
                fontSize = 14.sp,
                maxLines = 15,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))

            val uriHandler = LocalUriHandler.current
            OutlinedButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { uriHandler.openUri(article.url) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Read more", // Unicode right arrow
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_arrow_right),
                        contentDescription = "Read more",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp).padding(start = 4.dp, top = 2.dp),
                    )
                }
            }
        }
    }
}
