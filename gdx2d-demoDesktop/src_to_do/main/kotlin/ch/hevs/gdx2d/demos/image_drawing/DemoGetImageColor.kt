package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * Demonstrates the use of the [BitmapImage.getColor] function on
 * a real case.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoGetImageColor : PortableApplication() {
    lateinit var imgBitmap: BitmapImage

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
        setTitle("Get image color, mui 2014")

        Logger.log("Move the mouse on the image to get their color")

        // Loads the image that will be displayed in the middle of the screen
        imgBitmap = BitmapImage("images/color_pattern.png")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Clear the screen
        g.clear()

        /**
         * Position of the image on the screen
         */
        val imagePosition = Vector2(100f, 100f)

        /**
         * Position we want to read the color of
         */
        val sampledPixel = Vector2(Gdx.input.x.toFloat(), (Gdx.graphics.height - Gdx.input.y).toFloat())

        g.drawPicture(imagePosition.x, imagePosition.y, imgBitmap)

        /**
         * To get the color of a pixel in an image, we
         * must get translate screen coordinates to image coordinates
         * This is the role of the following function
         */
        val imgPixel = imgBitmap.pixelInScreenSpace(sampledPixel, imagePosition)

        if (imgBitmap.isContained(imgPixel)) {
            val c = imgBitmap.getColor(imgPixel.x.toInt(), imgPixel.y.toInt())

            // Draw a circle corresponding to the read color
            g.drawStringCentered(300f, "Color read from the image")
            g.drawFilledCircle(250f, 250f, 20f, c)
        }

        g.drawFPS()        // Draws the number of frame per second
        g.drawSchoolLogo() // Draws the school logo
    }

    override fun onDispose() {
        super.onDispose()
        imgBitmap.dispose()
    }
}

fun main(args: Array<String>) {
  DemoGetImageColor()
}
