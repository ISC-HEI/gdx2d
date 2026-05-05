package ch.hevs.gdx2d.demos.physics.joints

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.colors.Palette
import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Align

import java.util.LinkedList
import java.util.Random

/**
 * A box that will be used as a rotor for the demo
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class Rotor(name: String, position: Vector2, private val w: Float, private val h: Float) : PhysicsBox(name, position, w, h), DrawableObject {

    /**
     * Used for drawing the rotor
     */
    override fun draw(g: GdxGraphics) {
        val x = bodyPosition.x
        val y = bodyPosition.y
        val angle = bodyAngleDeg
        g.drawFilledRectangle(x, y, w, h, angle, Palette.pastel1[1])
        g.drawTransformedPicture(x, y, angle, 0.2f, screw)
    }

    companion object {
        val screw = BitmapImage("images/screw.png")
    }
}

/**
 * A demo on how to use PhysicsMotor with anchor points
 *
 * @author Pierre-André Mudry, mui
 * @version 1.3
 */
class DemoMixer : PortableApplication() {
    // The number of balls generated
    val N_PARTICLES = 100
    var world = PhysicsWorld.getInstance()
    lateinit var debugRenderer: DebugRenderer
    lateinit var box1: Body
    lateinit var box2: Body
    lateinit var physicMotor: PhysicsMotor
    // Linked List to store all particles
    var particles = LinkedList<CircleParticle>()
    lateinit var random: Random
    var width: Float = 0.toFloat()
    var height: Float = 0.toFloat()
    lateinit var rotor: Rotor

    override fun onInit() {
        setTitle("Particle mixer, mui 2014")
        Logger.log("Press left mouse button to enable/disable the motor.")

        // A renderer that displays physics objects things simply
        debugRenderer = DebugRenderer()
        random = Random()

        width = windowWidth.toFloat()
        height = windowHeight.toFloat()

        // Create fixed boundaries at each side of the screen
        PhysicsScreenBoundaries(width, height)

        val stator = PhysicsStaticBox("stator",
                Vector2(width / 2, height / 2), 5f, 5f)
        box1 = stator.body

        /**
         * Create the stator (moving) part. It is also located in the center of
         * the frame. It is not static, as it can rotate
         */
        rotor = Rotor("rotor", Vector2(width / 2, height / 2), width * 0.85f, height * 0.02f)
        box2 = rotor.body

        /**
         * Create a motor that will make the moving box move and rotate around
         * the anchor point (which is the center of the first box)
         */
        physicMotor = PhysicsMotor(box1, box2, box1.worldCenter)

        // Initialize the motor with a speed and torque
        physicMotor.initializeMotor(1.0f, 8000.0f, false)

        createParticles()
    }

    /**
     * Generate random particles to fill the screen
     */
    private fun createParticles() {
        for (i in 0 until N_PARTICLES) {
            val x = width / 5 + random.nextFloat() * (width - 2 * width / 5)
            val y = random.nextFloat() * height
            val position = Vector2(x, y)
            val c = if (rnd.nextBoolean() == true)
                Color.DARK_GRAY
            else
                Color.LIGHT_GRAY
            val p = CircleParticle(position,
                    10 + rnd.nextInt(5), c, 0.002f, 1f)
            particles.add(p)
        }
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        // Draws the particles
        for (particle in particles) {
            particle.draw(g)
        }

        rotor.draw(g)

        // Render the scene using the debug renderer
        // debugRenderer.render(world, g.getCamera().combined);
        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        g.drawString(5f, height - 20, "Left Mouse button: Motor ON/OFF",
                Align.left)
        g.drawString(5f, height - 40,
                "Motor is " + if (physicMotor.isMotorEnabled) "ON" else "OFF",
                Align.left)

        g.drawSchoolLogoUpperRight()
        g.drawFPS(Color.CYAN)
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        if (button == Input.Buttons.LEFT) {
            physicMotor.enableMotor(!physicMotor.isMotorEnabled)
        }
    }
}

val rnd = Random()
fun main(args: Array<String>) {
  DemoMixer()
}
