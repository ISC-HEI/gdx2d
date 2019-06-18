package ch.hevs.gdx2d.demos.simple

import ch.hevs.gdx2d.components.graphics.Polygon
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * A very simple demonstration on how to
 * display something with the library
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoSimpleShapes : PortableApplication() {

    override fun onInit() {
        this.setTitle("Simple shapes, mui 2013")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        // Draws a yellow circle
        g.setColor(Color.YELLOW)
        g.drawCircle(250f, 400f, 20f)

        // Draws a green rectangle
        g.setColor(Color.GREEN)
        g.drawRectangle(20f, 250f, 40f, 40f, 0f)

        g.drawFilledCircle(50f, 50f, 20f, Color.PINK)
        g.drawFilledRectangle(80f, 30f, 20f, 20f, 0f, Color(0.5f, 0.5f, 0.5f, 0.5f))

        // Draws a blue polygon
        val points = arrayOf(Vector2(200f, 200f), Vector2(250f, 250f), Vector2(300f, 200f))

        val p = Polygon(points)
        g.drawFilledPolygon(p, Color.BLUE)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoSimpleShapes()
        }
    }
}
