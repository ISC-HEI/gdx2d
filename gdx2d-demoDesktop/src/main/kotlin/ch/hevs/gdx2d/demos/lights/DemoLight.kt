package ch.hevs.gdx2d.demos.lights

import box2dLight.ConeLight
import box2dLight.PointLight
import box2dLight.RayHandler
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

import java.util.ArrayList
import java.util.Random

/**
 * Demonstrates the usage of shadows and lights in GDX2D
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.05
 */
class DemoLight : PortableApplication() {

    // Physics-related attributes
    lateinit var rayHandler: RayHandler
    lateinit var world: World
    lateinit var debugRenderer: DebugRenderer
    var list = ArrayList<PhysicsCircle>()

    // Light related attributes
    lateinit var p: PointLight
    lateinit var c1: ConeLight
    lateinit var c2: ConeLight
    var width: Int = 0
    var height: Int = 0
    private var firstRun = true

    override fun onInit() {
        width = Gdx.graphics.width
        height = Gdx.graphics.height
        setTitle("Shadows and lights, mui 2013")

        Gdx.app.log("[DemoLights]", "Left click to create a new light")
        Gdx.app.log("[DemoLights]", "Right click disables normal light")

        world = PhysicsWorld.getInstance()
        world.gravity = Vector2(0f, 0f)

        // The light manager
        rayHandler = RayHandler(world)

        // This is the light controlled by the mouse click and drag
        p = PointLight(rayHandler, 200, Color.YELLOW, 10f,
                (width / 2 - 50).toFloat(),
                (height / 2 + 150).toFloat())

        p.distance = 10f
        p.color = Color(0.9f, 0f, 0.9f, 0.9f)
        p.isActive = false

        p.isSoft = true

        // The two light cones that are always present
        c1 = ConeLight(rayHandler, 300, Color(1f, 1f, 1f, 0.92f), 14f,
                0.2f * width.toFloat() * PhysicsConstants.PIXEL_TO_METERS,
                0.9f * height.toFloat() * PhysicsConstants.PIXEL_TO_METERS,
                270f, 40f)
        c2 = ConeLight(rayHandler, 300, Color(0.1f, 0.1f, 1f, 0.92f), 14f,
                0.8f * width.toFloat() * PhysicsConstants.PIXEL_TO_METERS,
                0.9f * height.toFloat() * PhysicsConstants.PIXEL_TO_METERS,
                270f, 40f)

        rayHandler.setCulling(true)
        rayHandler.setShadows(true)
        rayHandler.setBlur(true)
        rayHandler.setAmbientLight(0.4f)

        PhysicsScreenBoundaries(width.toFloat(), height.toFloat())
        createRandomCircles(10)

        debugRenderer = DebugRenderer()
    }

    /**
     * Creates n physics objects that will then cast shadows
     *
     * @param n The number of physics object to create
     */
    protected fun createRandomCircles(n: Int) {
        var n = n

        val r = Random()

        while (n > 0) {
            val position = Vector2((width * Math.random()).toFloat(), (height * Math.random()).toFloat())
            val circle = PhysicsCircle("circle", position, 10f, 1.2f, 1f, 0.01f)
            circle.setBodyLinearVelocity(r.nextFloat() * 3, r.nextFloat() * 3)
            n--

            // Only add the body, the rest is useless
            list.add(circle)
        }
    }

    override fun onGraphicRender(g: GdxGraphics) {
        if (firstRun) {
            val other = OrthographicCamera()
            other.setToOrtho(false)
            val cmb = other.combined.scl(PhysicsConstants.METERS_TO_PIXELS)
            rayHandler.setCombinedMatrix(cmb)
            firstRun = false
        }

        g.clear()

        // Render the blue spheres
        for (b in list) {
            val pos = b.bodyPosition
            g.drawFilledCircle(pos.x, pos.y, 12f, Color.MAGENTA)
        }

        // Render the lights
        g.beginCustomRendering()
        rayHandler.updateAndRender()
        g.endCustomRendering()

        // Update the physics
        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        g.drawSchoolLogo()
        g.drawFPS()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        if (button == Input.Buttons.RIGHT) {
            c1.isActive = !c1.isActive
            c2.isActive = !c2.isActive
        }

        // Turn on the light when pushing button
        if (button == Input.Buttons.LEFT) {
            p.isActive = true
            p.setPosition(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS)
        }
    }

    override fun onDrag(x: Int, y: Int) {
        p.setPosition(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS)
    }

    override fun onRelease(x: Int, y: Int, button: Int) {
        // Turn off the light when releasing button
        p.isActive = false
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoLight()
        }
    }
}
