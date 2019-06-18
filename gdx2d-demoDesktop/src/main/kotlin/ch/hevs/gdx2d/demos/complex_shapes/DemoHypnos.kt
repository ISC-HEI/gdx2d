package ch.hevs.gdx2d.demos.complex_shapes

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import java.util.ArrayList

/**
 * First try at reproducing http://lab.hakim.se/hypnos/
 * Not done yet...
 *
 * @author Pierre-André Mudry, mui
 * @version 1.0, April 2015
 */

class Coord(var x: Float, var y: Float, var r: Float)

class DemoHypnos : PortableApplication() {

    val points = ArrayList<Coord>()
    private val quality = 100f
    private var layerSize: Float = 0.toFloat()
    private var radius: Float = 0.toFloat()
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var angle = 0f

    protected fun generateObjects() {
        var i = 0
        while (i < quality) {
            val x = screenWidth / 2 + Math.sin((i / quality).toDouble() * 2.0 * Math.PI).toFloat() * radius - layerSize
            val y = screenHeight / 2 + Math.cos((i / quality).toDouble() * 2.0 * Math.PI).toFloat() * radius - layerSize
            val r = (i / quality * Math.PI).toFloat()
            val c = Coord(x, y, r)
            points.add(c)
            i++
        }
    }

    override fun onInit() {
        this.setTitle("Demo shapes, mui 2013")
        screenWidth = windowWidth
        screenHeight = windowHeight
        radius = Math.min(screenWidth, screenHeight) * 0.2f
        layerSize = radius * 0.25f
        generateObjects()
    }

    private fun update() {
        for (c in points) {
            c.r += 0.3f
        }
    }

    override fun onGraphicRender(g: GdxGraphics) {
        angle = if (angle >= 360) 0f else angle + 0.2f
        g.clear(Color.WHITE)
        update()

        for (c in points) {
            g.setColor(Color.WHITE)
            g.drawFilledRectangle(c.x, c.y, 50f, 50f, c.r)
            g.setColor(Color.BLACK)
            g.drawRectangle(c.x, c.y, 50f, 50f, c.r)
        }

        val beg = points[0]
        g.setColor(Color.WHITE)
        g.drawFilledRectangle(beg.x, beg.y, 50f, 50f, beg.r)
        g.setColor(Color.BLACK)
        g.drawRectangle(beg.x, beg.y, 50f, 50f, beg.r)


        var i = (quality - 30).toInt()
        while (i < quality) {
            val c = points[i]
            g.setColor(Color.WHITE)
            g.drawFilledRectangle(c.x, c.y, 50f, 50f, c.r)
            g.setColor(Color.BLACK)
            g.drawRectangle(c.x, c.y, 50f, 50f, c.r)
            i++
        }

        g.drawSchoolLogo()
        g.drawFPS()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoHypnos()
        }
    }
}
