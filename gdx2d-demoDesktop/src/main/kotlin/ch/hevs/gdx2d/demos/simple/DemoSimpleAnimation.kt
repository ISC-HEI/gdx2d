package ch.hevs.gdx2d.demos.simple

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color

/**
 * A very simple demonstration on how to display something animated with the
 * library
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoSimpleAnimation : PortableApplication() {
    var radius = 5
    var speed = 1
    var screenHeight: Int = 0
    var screenWidth: Int = 0

    override fun onInit() {
        // Sets the window title
        setTitle("Simple demo, mui 2013")

        screenHeight = Gdx.graphics.height
        screenWidth = Gdx.graphics.width
    }

    override fun onGraphicRender(g: GdxGraphics) {

        // Clears the screen
        g.clear()
        g.drawAntiAliasedCircle((screenWidth / 2).toFloat(), (screenHeight / 2).toFloat(), radius.toFloat(), Color.BLUE)

        // If reaching max or min size, invert the growing direction
        if (radius >= 100 || radius <= 3) {
            speed *= -1
        }

        // Modify the radius
        radius += speed

        g.drawSchoolLogo()
        g.drawFPS()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        if (onAndroid())
            androidResolver.showAboutBox()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            /**
             * Note that the constructor parameter is used to determine if running
             * on Android or not. As we are in main there, it means we are on
             * desktop computer.
             */
            DemoSimpleAnimation()
        }
    }

}
