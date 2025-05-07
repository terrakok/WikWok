package com.github.terrakok.wikwok.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.terrakok.wikwok.data.WikipediaArticle
import org.jetbrains.compose.resources.stringResource
import wikwok.composeapp.generated.resources.Res

/**
 * Composable for displaying a single Wikipedia article item
 */
@Composable
fun WikipediaArticleItem(
    article: WikipediaArticle,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Extract
            if (article.extract.isNotEmpty()) {
                Text(
                    text = article.extract,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Button to open article
            Button(
                onClick = { uriHandler.openUri(article.url) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Open article")
            }
        }
    }
}
