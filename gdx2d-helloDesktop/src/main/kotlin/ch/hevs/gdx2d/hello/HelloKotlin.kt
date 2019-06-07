package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Interpolation

/**
 * Hello World demo.
 *
 * @author Christopher Métrailler (mei)
 * @author Pierre-André Mudry (mui)
 * @version 2.1
 */
class HelloKotlin : PortableApplication() {

    private var imgBitmap: BitmapImage? = null

    /**
     * Animation related variables
     */
    private var direction = 1
    private var currentTime = 0f
    private val ANIMATION_LENGTH = 2f // Animation length (in seconds)
    private val MIN_ANGLE = -20f
    private val MAX_ANGLE = 20f

    override fun onInit() {
        setTitle("Hello World - mei, mui 2016")

        // Load a custom image (or from the lib "res/lib/icon64.png")
        imgBitmap = BitmapImage("images/hei-pi.png")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Clears the screen
        g.clear()

        // Compute the angle of the image using an elastic interpolation
        val t = computePercentage()
        val angle = Interpolation.bounce.apply(MIN_ANGLE, MAX_ANGLE, t)

        // Draw everything
        g.drawTransformedPicture(windowWidth / 2.0f, windowHeight / 2.0f, angle, 1.0f, imgBitmap)
        g.drawStringCentered(windowHeight * 0.8f, "Welcome to gdx2d !")
        g.drawFPS()
        g.drawSchoolLogo()
    }

    /**
     * Compute time percentage for making a looping animation
     *
     * @return the current normalized time
     */
    private fun computePercentage(): Float {
        if (direction == 1) {
            currentTime += Gdx.graphics.deltaTime
            if (currentTime > ANIMATION_LENGTH) {
                currentTime = ANIMATION_LENGTH
                direction *= -1
            }
        } else {
            currentTime -= Gdx.graphics.deltaTime
            if (currentTime < 0) {
                currentTime = 0f
                direction *= -1
            }
        }

        return (currentTime / ANIMATION_LENGTH)
    }
//
//  companion object {
//    @JvmStatic
//    fun main(args: Array<String>) {
//      HelloKotlin()
//    }
//  }
}


fun main(args: Array<String>) {
  HelloKotlin()
}
