import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import com.github.terrakok.wikwok.App
import io.github.vinceglb.filekit.FileKit

fun main() = application {
    FileKit.init(appId = "WikWok")
    Window(
        title = "WikWok",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        App()
    }
}

