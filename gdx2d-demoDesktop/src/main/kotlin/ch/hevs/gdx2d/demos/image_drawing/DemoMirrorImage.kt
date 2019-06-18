package ch.hevs.gdx2d.demos.image_drawing


import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * This demo is similar to [DemoSimpleImage] but demonstrates
 * how to use the mirrorUpDown and mirrorLeftRight methods
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoMirrorImage : PortableApplication() {
    internal var counter = 0f
    internal lateinit var imgBitmap: BitmapImage

    override fun onInit() {
        // Sets the window title
        setTitle("Mirror image drawing, mui 2013")

        // Loads the image that will be displayed in the middle of the screen
        imgBitmap = BitmapImage("images/Android_PI_48x48.png")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        /**
         * Rendering
         */
        // Clear the screen
        g.clear()

        // Renders the image mirrored
        g.drawPicture(windowWidth / 3.0f, windowHeight / 3.0f, imgBitmap)
        imgBitmap.mirrorUpDown()
        g.drawPicture(windowWidth * 2.0f / 3.0f, windowHeight / 3.0f, imgBitmap)
        imgBitmap.mirrorUpDown()

        g.drawPicture(windowWidth / 3.0f, windowHeight * 2.0f / 3.0f, imgBitmap)
        imgBitmap.mirrorLeftRight()
        g.drawPicture(windowWidth * 2.0f / 3.0f, windowHeight * 2.0f / 3.0f, imgBitmap)
        imgBitmap.mirrorLeftRight()

        g.drawFPS()        // Draws the number of frame per second
        g.drawSchoolLogo() // Draws the school logo
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoMirrorImage()
        }
    }
}
