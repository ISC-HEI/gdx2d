package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * This demo is similar to [DemoSimpleImage] but demonstrates
 * how to use the alpha parameter to draw images
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoAlphaImage : PortableApplication() {
    lateinit var imgBitmap: BitmapImage
    lateinit var backgroundBitmap: BitmapImage

    var alpha1 = 0.06f
    var alpha2 = 0.3f
    var alpha3 = 0.6f
    var alpha4 = 0.94f
    var dir1 = 1
    var dir2 = 1
    var dir3 = 1
    var dir4 = 1

    override fun onInit() {
        // Sets the window title
        setTitle("Alpha transparency modification for images, mui 2013")

        // Loads the image that will be displayed in the middle of the screen
        imgBitmap = BitmapImage("images/Android_PI_48x48.png")

        // Load the background image
        backgroundBitmap = BitmapImage("images/back1_512.png")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        /**
         * Rendering
         */
        // Clear the screen
        g.clear()

        // Render an with mirror
        g.drawBackground(backgroundBitmap, 0f, 0f)

        if (alpha1 <= 0.05f || alpha1 >= 0.95f) dir1 *= -1
        if (alpha2 <= 0.05f || alpha2 >= 0.95f) dir2 *= -1
        if (alpha3 <= 0.05f || alpha3 >= 0.95f) dir3 *= -1
        if (alpha4 <= 0.05f || alpha4 >= 0.95f) dir4 *= -1

        alpha1 += if (dir1 > 0) 0.01f else -0.01f
        alpha2 += if (dir2 > 0) 0.01f else -0.01f
        alpha3 += if (dir3 > 0) 0.01f else -0.01f
        alpha4 += if (dir4 > 0) 0.01f else -0.01f

        g.drawAlphaPicture(windowWidth / 3.0f, windowHeight / 3.0f, alpha1, imgBitmap)
        g.drawAlphaPicture(windowWidth * 2.0f / 3.0f, windowHeight / 3.0f, alpha2, imgBitmap)
        g.drawAlphaPicture(windowWidth / 3.0f, windowHeight * 2.0f / 3.0f, alpha3, imgBitmap)
        g.drawAlphaPicture(windowWidth * 2.0f / 3.0f, windowHeight * 2.0f / 3.0f, alpha4, imgBitmap)

        g.drawFPS()        // Draws the number of frame per second
        g.drawSchoolLogo() // Draws the school logo
    }

    override fun onDispose() {
        super.onDispose()
        imgBitmap.dispose()
        backgroundBitmap.dispose()
    }
}

fun main(args: Array<String>) {
  DemoAlphaImage()
}
