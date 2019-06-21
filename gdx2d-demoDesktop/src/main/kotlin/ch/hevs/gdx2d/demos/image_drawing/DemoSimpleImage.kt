package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Demo application to demonstrate how to load a file which
 * should be located in a `data` folder at the root
 * of the project.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
open class DemoSimpleImage : PortableApplication() {
    var counter = 0f
    lateinit var imgBitmap: BitmapImage

    override fun onInit() {
        // Sets the window title
        setTitle("Simple image drawing, mui 2013")

        // Loads the image that will be displayed in the middle of the screen
        imgBitmap = BitmapImage("images/compass_150.png")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        /**
         * Rendering
         */
        // Clear the screen
        g.clear()

        // Render an image which turns with scale
        g.drawPicture((windowWidth / 2).toFloat(), (windowHeight / 2).toFloat(), imgBitmap)

        g.drawFPS()        // Draws the number of frame per second
        g.drawSchoolLogo() // Draws the school logo
    }
}

fun main(args: Array<String>) {
  DemoSimpleImage()
}
