package ch.hevs.gdx2d.demos.simple.mistify

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * A classical Mistify screen saver clone.<br></br>
 *
 *
 * Code adapted from [ http://r3dux.org/2010/11/mystify-2-0](http://r3dux.org/2010/11/mystify-2-0/)
 *
 * @author Pierre-André Mudry
 * @version 1.0
 * @see [ http://r3dux.org/2010/11/mystify-2-0](http://r3dux.org/2010/11/mystify-2-0/) for the original source code
 */
class DemoLines : PortableApplication() {
    val N_SHAPES = 3
    lateinit var s: Array<BounceShape>
    var frame = 0

    override fun onInit() {
        // Sets the window title
        setTitle("Mistfy screensaver clone, mui 2013")

        // Allocate size for the shapes
        s = Array(N_SHAPES){BounceShape(this.windowWidth, this.windowHeight)}
    }

    override fun onGraphicRender(g: GdxGraphics) {

        // Clears the screen
        g.clear()

        // Draws and moves the shapes
        for (shape in s) {
            shape.drawShape(g)
            shape.moveShape(g.screenWidth, g.screenHeight)
            shape.shiftShapeColour(frame++)
        }

        /**
         * TODO it would be nice to have something like an accumulation buffer that fades here
         */
        g.drawFPS()
        g.drawSchoolLogo()
    }
}

fun main(args: Array<String>) {
  DemoLines()
}
