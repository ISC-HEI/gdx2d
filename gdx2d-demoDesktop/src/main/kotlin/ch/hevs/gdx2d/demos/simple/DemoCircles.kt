package ch.hevs.gdx2d.demos.simple

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * A very simple demonstration on how to display something animated with the
 * library
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoCircles : PortableApplication() {
    private val radius = IntArray(8)
    private val speed = IntArray(8)

    override fun onInit() {
        // Sets the window title
        setTitle("Moving circles demo, mui 2013")

        for (i in 0..7) {
            radius[7 - i] = (i + 1) * (40 / 8)
            speed[i] = 1
        }
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Clears the screen
        g.clear(Color.DARK_GRAY)

        // Draws the circles
        for (i in 0..7) {
            if (radius[i] >= 40 || radius[i] <= 3) {
                speed[i] *= -1
            }

            radius[i] += speed[i]

            g.drawFilledCircle(
                    (windowWidth / 10 + i * (windowWidth - windowWidth / 10) / 8).toFloat(),
                    (windowHeight / 2).toFloat(),
                    radius[i].toFloat(),
                    Color(radius[i] / 40.0f, 0f, 0f, 1f))
        }

        g.drawFPS()
        g.drawSchoolLogo()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        if (onAndroid())
            androidResolver.showAboutBox()
    }
}

fun main(args: Array<String>) {
  /**
   * Note that the constructor parameter is used to determine if running
   * on Android or not. As we are in main there, it means we are on
   * desktop computer.
   */
  DemoCircles()
}
