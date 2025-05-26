import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToNavigation
import com.github.terrakok.wikwok.App
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont
import wikwok.composeapp.generated.resources.NotoNastaliqUrdu_Regular
import wikwok.composeapp.generated.resources.NotoSansArabic_Regular
import wikwok.composeapp.generated.resources.NotoSansBengali_Regular
import wikwok.composeapp.generated.resources.NotoSansGujarati_Regular
import wikwok.composeapp.generated.resources.NotoSansHebrew_Regular
import wikwok.composeapp.generated.resources.NotoSansJP_Regular
import wikwok.composeapp.generated.resources.NotoSansKR_Regular
import wikwok.composeapp.generated.resources.NotoSansMalayalam_Regular
import wikwok.composeapp.generated.resources.NotoSansTC_Regular
import wikwok.composeapp.generated.resources.Res

@OptIn(ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class, ExperimentalResourceApi::class)
fun main() {
    val body = document.body ?: return
    ComposeViewport(body) {
        App(
            onNavHostReady = { window.bindToNavigation(it) }
        )

        val fontNotoNastaliqUrdu by preloadFont(Res.font.NotoNastaliqUrdu_Regular)
        val fontNotoSansArabic by preloadFont(Res.font.NotoSansArabic_Regular)
        val fontNotoSansBengali by preloadFont(Res.font.NotoSansBengali_Regular)
        val fontNotoSansGujarati by preloadFont(Res.font.NotoSansGujarati_Regular)
        val fontNotoSansHebrew by preloadFont(Res.font.NotoSansHebrew_Regular)
        val fontNotoSansJP by preloadFont(Res.font.NotoSansJP_Regular)
        val fontNotoSansKR by preloadFont(Res.font.NotoSansKR_Regular)
        val fontNotoSansMalayalam by preloadFont(Res.font.NotoSansMalayalam_Regular)
        val fontNotoSansTC by preloadFont(Res.font.NotoSansTC_Regular)

        val loaded = fontNotoNastaliqUrdu != null &&
                fontNotoSansArabic != null &&
                fontNotoSansBengali != null &&
                fontNotoSansGujarati != null &&
                fontNotoSansHebrew != null &&
                fontNotoSansJP != null &&
                fontNotoSansKR != null &&
                fontNotoSansMalayalam != null &&
                fontNotoSansTC != null

        val fontFamilyResolver = LocalFontFamilyResolver.current
        LaunchedEffect(fontFamilyResolver, loaded) {
            if (!loaded) return@LaunchedEffect
            fontFamilyResolver.preload(
                FontFamily(
                    fontNotoNastaliqUrdu!!,
                    fontNotoSansArabic!!,
                    fontNotoSansBengali!!,
                    fontNotoSansGujarati!!,
                    fontNotoSansHebrew!!,
                    fontNotoSansJP!!,
                    fontNotoSansKR!!,
                    fontNotoSansMalayalam!!,
                    fontNotoSansTC!!
                )
            )
        }
    }
}
