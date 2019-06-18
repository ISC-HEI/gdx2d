package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.GL20


/**
 * This demo is similar to [DemoSimpleImage] but demonstrates
 * how to use the alpha parameter to draw images
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoBlendingFunctions : PortableApplication() {
    internal lateinit var imgBitmap: BitmapImage
    internal lateinit var backgroundBitmap: BitmapImage

    internal var w: Float = 0.toFloat()
    internal var h: Float = 0.toFloat()
    internal var src_functions = intArrayOf(GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_SRC_COLOR, GL20.GL_ONE_MINUS_SRC_COLOR, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

    internal var dst_functions = intArrayOf(GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_DST_COLOR, GL20.GL_ONE_MINUS_DST_COLOR, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

    override fun onInit() {
        // Sets the window title
        setTitle("Blending functions for images, mui 2013")

        // Loads the image that will be displayed in the middle of the screen
        imgBitmap = BitmapImage("images/texture.png")

        // Load the background image
        backgroundBitmap = BitmapImage("images/back1_512.png")

        // Dimensions of the image
        w = imgBitmap.image.width.toFloat()
        h = imgBitmap.image.height.toFloat()
    }

    override fun onGraphicRender(g: GdxGraphics) {
        /**
         * Rendering of the scene
         */
        g.clear()

        g.drawBackground(backgroundBitmap, 0f, 0f)

        //g.spriteBatch.setBlendFunction(GL11.GL_ZERO, GL11.GL_ZERO);

        // Use the different blending mode combinations
        for (i in 0..5) {
            for (j in 0..5) {
                g.setBlendFunction(src_functions[i], dst_functions[j])
                g.drawPicture(60f + w * i + (10 * i).toFloat(), 60f + h * j + (10 * j).toFloat(), imgBitmap)
            }
        }

        g.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        g.drawFPS()        // Draws the number of frame per second
        g.drawSchoolLogo() // Draws the school logo
    }

    override fun onDispose() {
        super.onDispose()
        imgBitmap.dispose()
        backgroundBitmap.dispose()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoBlendingFunctions()
        }
    }
}
