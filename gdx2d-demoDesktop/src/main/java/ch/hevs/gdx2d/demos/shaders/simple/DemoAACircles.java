package ch.hevs.gdx2d.demos.shaders.simple

import ch.hevs.gdx2d.components.colors.Palette
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

import java.util.Random

/**
 * Demonstrates the use of anti-aliased circles function
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoAACircles : PortableApplication() {
    internal var r = Random()
    // To store the various circles informations
    internal var x = IntArray(Q)
    internal var y = IntArray(Q)
    internal var rad = IntArray(Q)
    internal var p = IntArray(Q)
    internal var done = false

    override fun onInit() {
        /**
         * Create all those circles, at various locations and using different
         * colors from a palette
         */
        for (i in 0 until Q) {
            x[i] = r.nextInt(500)
            y[i] = r.nextInt(500)
            rad[i] = r.nextInt(30) + 10
            p[i] = r.nextInt(Palette.pastel2.size)
        }
    }

    override fun onGraphicRender(g: GdxGraphics) {

        /**
         * Draws all those circles. TODO : this is slow and performance should
         * be improved, using texture?
         */
        if (!done) {
            g.clear()
            for (i in 0 until Q) {
                g.drawAntiAliasedCircle(x[i].toFloat(), y[i].toFloat(), rad[i].toFloat(),
                        Palette.pastel2[p[i]])
            }
            done = false

            g.drawFPS()
            g.drawSchoolLogo()
        }

    }

    companion object {

        // The number of circles to generate
        internal val Q = 5

        @JvmStatic
        fun main(args: Array<String>) {
            DemoAACircles()
        }
    }
}
