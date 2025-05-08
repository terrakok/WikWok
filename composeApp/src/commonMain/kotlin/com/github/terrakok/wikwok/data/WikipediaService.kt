package com.github.terrakok.wikwok.data

import com.github.terrakok.wikwok.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class WikipediaArticle(
    val id: Int,
    val title: String,
    val url: String,
    val extract: String,
    val thumbnail: String?
)

/**
 * Service for fetching Wikipedia articles
 */
class WikipediaService {
    private val client: HttpClient = HttpClient()

    /**
     * Fetches a batch of random Wikipedia articles
     * @param count Number of articles to fetch (ignored, API always returns 20)
     * @return List of Wikipedia articles
     */
    suspend fun getRandomArticles(count: Int): List<WikipediaArticle> {
        try {
            val responseText = client.get("https://en.wikipedia.org/w/api.php?" +
                    "action=query" +
                    "&format=json" +
                    "&generator=random" +
                    "&grnnamespace=0" +
                    "&prop=extracts|info|pageimages" +
                    "&inprop=url|varianttitles" +
                    "&grnlimit=${count}" +
                    "&exintro=1" +
                    "&exlimit=max" +
                    "&exsentences=5" +
                    "&explaintext=1" +
                    "&piprop=thumbnail" +
                    "&pithumbsize=800" +
                    "&origin=*" +
                    "&variant=en"
            ).body<String>()
            val jsonElement = Json.parseToJsonElement(responseText)

            // Parse the JSON response manually
            val articles = mutableListOf<WikipediaArticle>()

            val query = jsonElement.jsonObject["query"]?.jsonObject
            val pages = query?.get("pages")?.jsonObject

            pages?.forEach { (_, pageElement) ->
                val page = pageElement.jsonObject
                val id = page["pageid"]?.jsonPrimitive?.int ?: 0
                val title = page["title"]?.jsonPrimitive?.content ?: "Unknown"
                val extract = page["extract"]?.jsonPrimitive?.content ?: ""
                val url = page["fullurl"]?.jsonPrimitive?.content ?: "https://en.wikipedia.org/wiki?curid=$id"

                val thumbnail = page["thumbnail"]?.jsonObject?.get("source")?.jsonPrimitive?.content

                articles.add(
                    WikipediaArticle(
                        id = id,
                        title = title,
                        url = url,
                        extract = extract,
                        thumbnail = thumbnail
                    )
                )
            }

            return articles.filter { it.thumbnail != null && it.extract.length > 15 }
        } catch (e: Exception) {
            // Log the error in a real app
            Log.error(e) { "Error fetching Wikipedia articles" }
            throw e
        }
    }
}
