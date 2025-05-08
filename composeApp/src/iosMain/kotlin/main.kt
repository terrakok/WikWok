import androidx.compose.ui.window.ComposeUIViewController
import com.github.terrakok.wikwok.App
import com.github.terrakok.wikwok.Log
import com.github.terrakok.wikwok.data.ShareService
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIDevice
import platform.UIKit.UIUserInterfaceIdiomPad
import platform.UIKit.UIViewController
import platform.UIKit.popoverPresentationController

@OptIn(ExperimentalForeignApi::class)
fun MainViewController(): UIViewController {
    var viewController: UIViewController? = null

    val shareService = object : ShareService {
        override fun share(text: String) {
            val self = viewController ?: return
            val vc = UIActivityViewController(
                activityItems = listOf(text),
                applicationActivities = null
            )
            if (isIpad()) {
                Log.debug { "share on iPad" }
                vc.popoverPresentationController?.apply {
                    sourceView = self.view
                    sourceRect = self.view.center.useContents { CGRectMake(x, y, 0.0, 0.0) }
                    permittedArrowDirections = 0uL
                }
            }
            self.presentViewController(vc, true, null)
        }

    }

    viewController = ComposeUIViewController {
        App(shareService = shareService)
    }

    return viewController
}

private fun isIpad(): Boolean {
    val device = UIDevice.currentDevice
    return device.userInterfaceIdiom == UIUserInterfaceIdiomPad
}
