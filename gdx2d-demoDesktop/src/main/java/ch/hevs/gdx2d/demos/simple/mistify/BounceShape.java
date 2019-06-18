package ch.hevs.gdx2d.demos.simple.mistify

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import java.util.Random

/**
 * A classical Mistify screen saver clone.
 *
 *
 * Code adapted from http://r3dux.org/2010/11/mystify-2-0/
 *
 * @author Pierre-André Mudry
 * @version 1.0
 */
class BounceShape(width: Int, height: Int) {
    private val colourChangeSpeed = 0.001
    private val xSpeedMultiplier = 6.0
    private val ySpeedMultiplier = 6.0
    private val xSpeedAdder = 1.0
    private val ySpeedAdder = 1.0
    private val x = DoubleArray(4)
    private val y = DoubleArray(4)
    private val xSpeed = DoubleArray(4)
    private val ySpeed = DoubleArray(4)
    private val c: Color
    private var targetC: Color? = null

    init {
        // Initialise the x/y and xSpeeds/ySpeeds
        for (loop in 0..3) {
            x[loop] = r.nextInt(width).toDouble()
            y[loop] = r.nextInt(height).toDouble()

            xSpeed[loop] = r.nextDouble() * xSpeedMultiplier
            ySpeed[loop] = r.nextDouble() * ySpeedMultiplier
        }

        // Pick a random colour for the current colour and target colour
        c = pickColour()
        targetC = pickColour()
    }

    internal fun drawShape(g: GdxGraphics) {
        g.setColor(c)

        for (pointLoop in 0..3) {
            g.drawLine(x[pointLoop].toFloat(), y[pointLoop].toFloat(),
                    x[(pointLoop + 1) % 4].toFloat(),
                    y[(pointLoop + 1) % 4].toFloat())
        }
    }

    internal fun moveShape(theScreenWidth: Int, theScreenHeight: Int) {
        for (loop in 0..3) {
            // Move the particles
            x[loop] += xSpeed[loop]
            y[loop] += ySpeed[loop]

            // Bounce the particles when they hit the edge of the window
            if (x[loop] > theScreenWidth) {
                x[loop] = theScreenWidth.toDouble()
                xSpeed[loop] = -xSpeedAdder + r.nextFloat() * (xSpeedMultiplier * -1)
            }
            if (x[loop] < 0.0f) {
                x[loop] = 0.0
                xSpeed[loop] = xSpeedAdder + r.nextFloat() * xSpeedMultiplier
            }

            if (y[loop] > theScreenHeight) {
                y[loop] = theScreenHeight.toDouble()
                ySpeed[loop] = -ySpeedAdder + r.nextFloat() * (ySpeedMultiplier * -1)
            }
            if (y[loop] < 0.0f) {
                y[loop] = 0.0
                ySpeed[loop] = ySpeedAdder + r.nextFloat() * ySpeedMultiplier
            }

        } // End of for loop
    }

    private fun pickColour(): Color {
        return Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1.0f)
    }

    fun shiftShapeColour(frameCount: Int) {
        // Change the target colour every 500 frames
        if (frameCount % 500 == 0)
            targetC = pickColour()

        // Shift red component close to red target
        if (c.r < targetC!!.r)
            c.r += colourChangeSpeed.toFloat()
        if (c.r > targetC!!.r)
            c.r -= colourChangeSpeed.toFloat()

        // Shift green component closer to green target
        if (c.g < targetC!!.g)
            c.g += colourChangeSpeed.toFloat()
        if (c.g > targetC!!.g)
            c.g -= colourChangeSpeed.toFloat()

        // Shift blue component closer to blue target
        if (c.b < targetC!!.b)
            c.b += colourChangeSpeed.toFloat()
        if (c.b > targetC!!.b)
            c.b -= colourChangeSpeed.toFloat()
    }

    companion object {
        private val r = Random()
    }
}
