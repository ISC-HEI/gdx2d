package ch.hevs.gdx2d.demos.scrolling

import ch.hevs.gdx2d.demos.scrolling.objects.*
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input


import java.util.Vector

/**
 * Demonstrates how to scroll and zoom on a scene. Also demonstrates how to
 * delegate render to other objects through the [DrawableObject]
 * interface.
 *
 *
 * For some reason, running in windowed mode displays stuttering problems. Image
 * stuttering can be removed by playing the demo full screen.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.04
 */
// Displays the demo in full-screen using the display's native resolution
class DemoScrolling : PortableApplication(0, 0, true) {

    internal var toDraw = Vector<DrawableObject>()

    // Default zoom factor
    internal var zoom = 1.0
    internal var travelSpeed = 2f
    internal var scrolling = true

    override fun onInit() {
        setTitle("Scrolling demo, mui 2013")
        Logger.log("Press s or w for zooming in or out")

        toDraw.add(Sky())

        // Some pipe for a nice 'Mario' like atmosphere
        toDraw.add(Pipe(100, 60))
        toDraw.add(Pipe(600, 80))
        toDraw.add(Pipe(1500, 90))

        // First layer (bottom)
        for (i in 0..59) {
            toDraw.add(Brick(-500 + 64 * i, 20))
        }

        // Coins
        for (i in 0..4) {
            toDraw.add(Coin(250 + 64 * i, 120))
        }

        // Some clouds
        toDraw.add(Cloud(100, 450))
        toDraw.add(Cloud(250, 600))
        toDraw.add(Cloud(450, 250))
        toDraw.add(Cloud(700, 350))
        toDraw.add(Cloud(1000, 370))
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        scrolling = !scrolling
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()
        g.zoom(zoom.toFloat())

        /**
         * Handle input (not done using the inherited method onKeyDown) because
         * we don't want to release the key for the zoom to occur
         */
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            zoom += 0.02
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            zoom -= 0.02

        // We scroll only if the camera is not too big
        if (scrolling && g.camera.viewportWidth < 600) {
            // If we've reached one of the borders, change the speed's direction
            if (g.camera.position.x > 600 || g.camera.position.x < 200) {
                travelSpeed *= -1f
            }

            // Travel the camera left-right
            g.scroll(travelSpeed, 0f)
        }

        //
        // Draw all objects
        for (obj in toDraw) {
            obj.draw(g)
        }

        g.drawSchoolLogoUpperRight()
        g.drawFPS()
    }

    companion object {

        // Create a default-sized application
        @JvmStatic
        fun main(args: Array<String>) {
            DemoScrolling()
        }
    }
}
