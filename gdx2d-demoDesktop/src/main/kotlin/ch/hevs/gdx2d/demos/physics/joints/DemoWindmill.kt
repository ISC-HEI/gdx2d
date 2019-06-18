package ch.hevs.gdx2d.demos.physics.joints

import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Align
import java.util.LinkedList
import java.util.Random

/**
 * A demo on how to use [PhysicsMotor] and anchor points
 *
 *
 *
 * Based on ex 5.9 from the Nature of code book
 *
 * @author Thierry Hischier, hit 2014
 * @version 1.2
 * @see [The
 * nature of code example](http://natureofcode.com/book/chapter-5-physics-libraries/)
 */
class DemoWindmill : PortableApplication() {
    var world = PhysicsWorld.getInstance()
    lateinit var debugRenderer: DebugRenderer
    lateinit var physicMotor: PhysicsMotor

    // Linked List to store all particles
    var particles = LinkedList<CircleParticle>()
    lateinit var random: Random

    var width: Float = 0.toFloat()
    var height: Float = 0.toFloat()

    var time = 0

    // The rate at which the balls are generated
    var GENERATION_RATE = 2
    var generate = false

    override fun onInit() {
        setTitle("Windmill simulation, hit 2014")

        Logger.log("Press left mouse button to enable/disable the motor.")
        Logger.log("Press right mouse button to generate particles.")

        // A renderer that displays physics objects things simply
        debugRenderer = DebugRenderer()
        random = Random()

        width = windowWidth.toFloat()
        height = windowHeight.toFloat()

        // Create PhysicStaticBox where the windmill will be fixed. It is
        // located in the center of the frame
        val staticBox = PhysicsStaticBox("box1", Vector2(width / 2, height / 2), 20f, 80f)

        val box1 = staticBox.body

        // Create the windmill wing. It is also located in the center of the frame
        // This is is not static, as it can rotate
        val movingBox = PhysicsBox("box2", Vector2(width / 2, height / 2), 240f, 20f)

        val box2 = movingBox.body

        /**
         * Create a motor that will make the moving box move and rotate
         * around the anchor point (which is the center of the first box)
         */
        physicMotor = PhysicsMotor(box1, box2, box1.worldCenter)

        // Initialize the motor with a speed and torque
        physicMotor.initializeMotor(2.0f, 200.0f, false)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        // Draw all the added particles and destroy the particles which are no
        // longer in the visible
        val iter = particles.iterator()
        while (iter.hasNext()) {
            val particle = iter.next()
            particle.draw(g)

            val p = particle.bodyPosition

            // If a particle is not visible anymore, it should be destroyed
            if (p.y > height || p.y < 0 || p.x > width || p.x < 0) {
                particle.destroy()
                // Remove the particle from the collection as well
                iter.remove()
            }
        }

        /**
         * Generate particles when right mouse pressed
         */
        if (generate) {
            for (i in 0 until GENERATION_RATE) {
                val x = width / 5 + random.nextFloat() * (width - 2 * width / 5)
                val y = height * 0.9f + random.nextInt(30)
                val position = Vector2(x, y)
                val newParticle = CircleParticle("a particle", position, 5)
                particles.add(newParticle)
            }
        } else {
            /**
             * Add new particles from time to time
             */
            if (time % 25 == 0) {
                val x = random.nextInt(100)
                val position = Vector2((windowWidth / 2 - 50 + x).toFloat(), (windowHeight - 5).toFloat())
                val newParticle = CircleParticle("a particle", position, 5)
                particles.add(newParticle)
            }
        }

        time++ // Used for generating particles sporadically

        /**
         * Render the physics and draw the logo, fps information and the status
         * of the motor
         */
        debugRenderer.render(world, g.camera.combined)
        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        g.drawString(width - 5, 60f, "Left Mouse button: Motor ON/OFF", Align.right)
        g.drawString(width - 5, 40f, "Right Mouse button: Generate particles", Align.right)
        g.drawString(width - 5, 20f, "Motor is " + if (physicMotor.isMotorEnabled) "ON" else "OFF", Align.right)

        g.drawSchoolLogoUpperRight()
        g.drawFPS()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        if (button == Input.Buttons.LEFT) {
            physicMotor.enableMotor(!physicMotor.isMotorEnabled)
        } else if (button == Input.Buttons.RIGHT) {
            generate = true
        }
    }

    override fun onRelease(x: Int, y: Int, button: Int) {
        super.onRelease(x, y, button)
        generate = false
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoWindmill()
        }
    }
}
