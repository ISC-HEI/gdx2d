package ch.hevs.gdx2d.demos.perf_tests

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/**
 * Torture test for graphics primitives. *
 * Test all graphic primitives in different order, colors, zoom...
 *
 * @author Marc Pignat (pim)
 */
class GdxGraphicsTest : PortableApplication() {

    var zoom = 1.0f
    var zoom_up = true

    override fun onInit() {
        setTitle(this.javaClass.getSimpleName())
    }

    internal fun manage_zoom() {
        if (zoom_up) {
            zoom *= 1.01f
            if (zoom > 1.2) {
                zoom_up = false
            }
        } else {
            zoom *= 0.99f
            if (zoom < 0.8) {
                zoom_up = true
            }
        }
    }

    override fun onGraphicRender(g: GdxGraphics) {

        manage_zoom()

        g.clear()
        g.zoom(zoom)

        for (i in 0..19) {
            g.setPixel((40 + i).toFloat(), (0 + i).toFloat(), Color.BLUE)
        }
        g.drawCircle(10f, 10f, 10f)
        g.drawFilledCircle(30f, 10f, 10f, Color.RED)

        for (i in 0..19) {
            g.setPixel((60 - i).toFloat(), (0 + i).toFloat(), Color.BLUE)
        }

        g.drawRectangle(70f, 10f, 10f, 10f, 30f)
        g.drawFilledRectangle(90f, 10f, 10f, 10f, 60f, Color.CYAN)
        g.drawSchoolLogoUpperRight()
        g.drawFPS()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            GdxGraphicsTest()
        }
    }

}
