package ch.hevs.gdx2d.demos.tween.interpolatorengine

import ch.hevs.gdx2d.demos.tween.Ball
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation

/**
 * Demonstrates the usage of interpolation (tweening) for animation using the
 * [Interpolation] class of libgdx.
 *
 * @author Christopher Metrailler (mei)
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
class DemoPositionInterpolator : PortableApplication() {

    val ANIMATION_LENGTH = 1.3f // Animation length (in seconds)
    var currentTime = 0f // In seconds
    var direction = 1 // Direction of movement
    private lateinit var balls: Array<Ball>
    private var height: Int = 0
    private var width: Int = 0
    private var margin: Int = 0

    override fun onInit() {
        setTitle("Position interpolators, mei/mui 2013")

        margin = Gdx.graphics.width / 8
        height = Gdx.graphics.height
        width = Gdx.graphics.width

        balls = Array(8) {i -> Ball(margin.toFloat(), height * (i + 1) / 10f)}
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Create an infinite "Yoyo effect"
        val animationPercentage = computePercentage()

        // Apply different types of interpolation to the balls between start position and
        // end position of the X attribute of the ball
        val start = margin.toFloat()
        val end = (width - margin).toFloat()

        balls!![0].posx = Interpolation.linear.apply(start, end, animationPercentage)
        balls!![1].posx = Interpolation.elastic.apply(start, end, animationPercentage)
        balls!![2].posx = Interpolation.sine.apply(start, end, animationPercentage)
        balls!![3].posx = Interpolation.bounce.apply(start, end, animationPercentage)
        balls!![4].posx = Interpolation.circle.apply(start, end, animationPercentage)
        balls!![5].posx = Interpolation.swing.apply(start, end, animationPercentage)
        balls!![6].posx = Interpolation.pow2.apply(start, end, animationPercentage)
        balls!![7].posx = Interpolation.exp10.apply(start, end, animationPercentage)

        // Do the drawing
        g.clear()

        // Draw the two red boundaries
        g.setColor(Color.RED)
        g.drawLine(margin.toFloat(), height * 1 / 10f, margin.toFloat(), height * 8 / 10f)
        g.drawLine((width - margin).toFloat(), height * 1 / 10f, (width - margin).toFloat(), height * 8 / 10f)

        // Draw the balls
        for (i in balls!!.indices) {
            balls!![i].draw(g, 0.5f)
        }

        g.drawFPS()
        g.drawSchoolLogoUpperRight()
    }

    private fun computePercentage(): Float {
        if (direction == 1)
            currentTime += Gdx.graphics.deltaTime
        else
            currentTime -= Gdx.graphics.deltaTime

        if (currentTime >= ANIMATION_LENGTH || currentTime <= 0)
            direction *= -1

        return currentTime / ANIMATION_LENGTH
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoPositionInterpolator()
        }
    }
}
