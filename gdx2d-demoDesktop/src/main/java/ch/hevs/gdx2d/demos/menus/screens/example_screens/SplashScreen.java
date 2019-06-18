package ch.hevs.gdx2d.demos.menus.screens.example_screens

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * A simple @SplashScreen to demonstrate transition between screens
 */
class SplashScreen : RenderingScreen() {
    internal lateinit var imgBitmap: BitmapImage

    override fun onInit() {
        // Loads the image that will be displayed in the middle of the screen
        imgBitmap = BitmapImage("images/Android_PI_48x48.png")
    }

    public override fun onGraphicRender(g: GdxGraphics) {
        g.clear(Color.NAVY)
        g.drawStringCentered((g.screenHeight / 2).toFloat(), "1 - Splash screen")
        g.drawPicture(g.screenWidth / 2.0f, g.screenHeight / 3.0f, imgBitmap)
        g.drawSchoolLogo()
    }

    override fun dispose() {
        imgBitmap.dispose()
    }
}
