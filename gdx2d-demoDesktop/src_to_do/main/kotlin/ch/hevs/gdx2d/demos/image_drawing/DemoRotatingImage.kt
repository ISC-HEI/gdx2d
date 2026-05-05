package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Demo application to demonstrate how to rotate an image
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoRotatingImage : DemoSimpleImage() {

    override fun onInit() {
        super.onInit()
        this.setTitle("Rotating image, mui 2013")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        /**
         * Rendering
         */
        // Clear the screen
        g.clear()

        // Render an image which turns with scale
        g.drawTransformedPicture((windowWidth / 2).toFloat(), (windowHeight / 2).toFloat(),
                counter,
                1f,
                imgBitmap)

        g.drawFPS()        // Draws the number of frame per second
        g.drawSchoolLogo() // Draws the school logo

        /**
         * Logic update
         */
        if (counter >= 360)
            counter = 0f

        // Make the angle bigger
        counter += 1f
    }
}


fun main(args: Array<String>) {
  DemoRotatingImage()
}
