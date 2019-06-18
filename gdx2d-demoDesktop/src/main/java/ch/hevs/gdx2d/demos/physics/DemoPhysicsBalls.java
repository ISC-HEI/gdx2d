package ch.hevs.gdx2d.demos.physics

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Orientation
import com.badlogic.gdx.Input.Peripheral
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import java.util.LinkedList

/**
 * A demo for physics, based on box2d
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoPhysicsBalls : PortableApplication() {

    private val SMOOTHING = 30.0 // This value changes the dampening effect of the low-pass
    internal var list = LinkedList<PhysicsCircle>()
    // A world with gravity, pointing down
    internal var world = PhysicsWorld.getInstance()
    internal lateinit var debugRenderer: DebugRenderer
    internal lateinit var img: BitmapImage
    internal var hasAccelerometers: Boolean = false
    // For low-pass filtering accelerometer
    private var smoothedValue = 0.0

    override fun onInit() {

        setTitle("Physics demo with box2d, mui 2013")
        Logger.log("Click to create a ball")

        hasAccelerometers = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)

        world.gravity = Vector2(0f, -10f)

        // The walls
        PhysicsScreenBoundaries(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        // Used to display debug information about the physics
        debugRenderer = DebugRenderer()
        img = BitmapImage("images/soccer.png")

        addBall(windowWidth / 2, windowHeight / 2)
    }

    /**
     * Adds a ball at a given location
     *
     * @param x x location to put the ball to
     * @param y y location to put the ball to
     */
    fun addBall(x: Int, y: Int) {
        // If there exists enough ball already, remove the oldest one
        if (list.size > 50) {
            val b = list.poll()
            b.destroy()
        }

        val size = (Math.random() * 15.0f).toFloat() + 15f
        val b = PhysicsCircle(null, Vector2(x.toFloat(), y.toFloat()), size)

        // Add the ball to the list of existing balls
        list.add(b)
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)

        if (button == Input.Buttons.LEFT)
            addBall(x, y)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        //		debugRenderer.render(world, g.getCamera().combined);

        // For every body in the world
        val it = list.iterator()
        while (it.hasNext()) {
            val c = it.next()
            val radius = c.bodyRadius
            val pos = c.bodyPosition
            g.drawTransformedPicture(pos.x, pos.y, c.bodyAngleDeg, radius, radius, img)
            c.isBodyAwake = true
        }

        if (hasAccelerometers) {
            // On tablet, orientation is different than on phone
            val nativeOrientation = Gdx.input.nativeOrientation

            val accel: Float

            if (nativeOrientation == Orientation.Landscape)
                accel = -Gdx.input.accelerometerY
            else
                accel = Gdx.input.accelerometerX

            // Low pass filtering of the value
            smoothedValue += (accel - smoothedValue) / SMOOTHING
            world.gravity = Vector2(-smoothedValue.toFloat(), -10f)
        }

        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        g.drawSchoolLogoUpperRight()
        g.drawFPS()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoPhysicsBalls()
        }
    }
}
