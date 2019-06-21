package ch.hevs.gdx2d.demos.physics.particle

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import java.util.Random

/**
 * Demo for particle physics. There are no collisions in the physics and
 * no boundaries.
 *
 * @author Pierre-Andre Mudry (mui)
 * @version 1.2
 */
class DemoParticlePhysics : PortableApplication {
    val MAX_AGE = 35
    var CREATION_RATE = 3
    lateinit var dbgRenderer: DebugRenderer
    var world = PhysicsWorld.getInstance()
    // Particle creation related
    var mouseActive = false
    lateinit var position: Vector2

    constructor() : super() {}

    constructor(x: Int, y: Int) : super(x, y) {}

    override fun onInit() {
        setTitle("Particle physics, mui 2013")
        dbgRenderer = DebugRenderer()
        world.gravity = Vector2(0f, -0.6f)
        Gdx.app.log("[DemoParticlePhysics]", "Click on screen to create particles")
        Gdx.app.log("[DemoParticlePhysics]", "a/s change the creation rate of particles")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        val bodies = Array<Body>()
        world.getBodies(bodies)

        val it = bodies.iterator()


        while (it.hasNext()) {
            val p = it.next()

            if (p.userData is Particle) {
                val particle = p.userData as Particle
                particle.step()
                particle.render(g)

                if (particle.shouldbeDestroyed()) {
                    particle.destroy()
                }
            }
        }

        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        if (mouseActive)
            createParticles()

        g.drawSchoolLogo()
        g.drawFPS()
    }

    internal fun createParticles() {
        for (i in 0 until CREATION_RATE) {
            val c = Particle(position, 10, MAX_AGE + rand.nextInt(MAX_AGE / 2))

            // Apply a vertical force with some random horizontal component
            val force = Vector2()
            force.x = rand.nextFloat() * 0.00095f * (if (rand.nextBoolean() == true) -1 else 1).toFloat()
            force.y = rand.nextFloat() * 0.00095f * (if (rand.nextBoolean() == true) -1 else 1).toFloat()
            c.applyBodyLinearImpulse(force, position, true)
        }
    }

    override fun onDrag(x: Int, y: Int) {
        super.onDrag(x, y)
        position.x = x.toFloat()
        position.y = y.toFloat()
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        super.onClick(x, y, button)
        mouseActive = true
        position = Vector2(x.toFloat(), y.toFloat())
    }

    override fun onRelease(x: Int, y: Int, button: Int) {
        super.onRelease(x, y, button)
        position.x = x.toFloat()
        position.y = y.toFloat()
        mouseActive = false
    }

    override fun onKeyDown(keycode: Int) {
        super.onKeyDown(keycode)
        if (keycode == Input.Keys.A) {
            CREATION_RATE++
        }
        if (keycode == Input.Keys.S) {
            CREATION_RATE = if (CREATION_RATE > 1) CREATION_RATE - 1 else CREATION_RATE
        }
        Gdx.app.log("[DemoParticlePhysics]", "Creation rate is now $CREATION_RATE")
    }
}

val rand = Random()
fun main(args: Array<String>) {
  DemoParticlePhysics(1000, 600)
}
