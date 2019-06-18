package ch.hevs.gdx2d.demos.simple

import com.badlogic.gdx.graphics.Color

import ch.hevs.gdx2d.components.graphics.Turtle
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * A very simple demonstration on how to display something animated with the
 * turtle paradigm
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoTurtle : PortableApplication() {
    internal var t: Turtle? = null

    internal var flakeSize = 2
    internal var animDirection = 1

    /**
     * Draws the snowflake
     * @param length Length of the side of the flake
     * @param level The complexity level of the recursion
     */
    internal fun drawFlake(length: Double, level: Int) {
        drawSegment(length, level)
        t!!.turn(120.0)
        drawSegment(length, level)
        t!!.turn(120.0)
        drawSegment(length, level)
        t!!.turn(120.0)
    }

    /**
     * Draws a side of the flake
     * @param length length of the segment
     * @param level complexity level for the recursion
     */
    internal fun drawSegment(length: Double, level: Int) {
        //Recursion shall end somewhere
        if (level == 0) {
            t!!.forward(length)
            return
        }

        val currentLevel = length / 3.0
        drawSegment(currentLevel, level - 1)
        t!!.turn(-60.0)
        drawSegment(currentLevel, level - 1)
        t!!.turn(120.0)
        drawSegment(currentLevel, level - 1)
        t!!.turn(-60.0)
        drawSegment(currentLevel, level - 1)
    }

    override fun onInit() {
        setTitle("Moving turtle demo, mui 2016")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (t == null)
            t = Turtle(g, g.screenWidth.toFloat(), g.screenHeight.toFloat())

        // Clears the screen
        g.clear(Color.DARK_GRAY)

        // Go to the top of the screen
        t!!.changeColor(Color.WHITE)

        /**
         * Locate the turtle at the bottom of the screen
         */
        t!!.penDown()
        t!!.setAngle(60.0)
        t!!.jump((g.screenWidth * 0.5).toFloat(), (g.screenHeight * 0.25).toFloat())

        // Displays the snowflake
        drawFlake((20 + flakeSize * 2).toDouble(), 4)

        // Animate size
        flakeSize += animDirection

        if (flakeSize == 100 || flakeSize == 2)
            animDirection *= -1

        g.drawFPS()
        g.drawSchoolLogo()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoTurtle()
        }
    }
}
