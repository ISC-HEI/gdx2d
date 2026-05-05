package ch.hevs.gdx2d.demos.physics.collisions

import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World


/**
 * Simple collision handling in box2d
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoSlowCollisions : PortableApplication() {
    var world = PhysicsWorld.getInstance()
    lateinit var dbgRenderer: DebugRenderer

    lateinit var b1: BumpyBall
    lateinit var b2: BumpyBall

    override fun onInit() {
        dbgRenderer = DebugRenderer()
        setTitle("Slow collisions for box2d, mui 2013")

        PhysicsScreenBoundaries(windowWidth.toFloat(), windowHeight.toFloat())

        // A BumpyBall has redefined its collision method.
        b1 = BumpyBall("ball 1", Vector2(150f, 250f), 30)
        b2 = BumpyBall("ball 2", Vector2(350f, 250f), 30)

        // Indicate that the ball should be informed for collisions
        b1.enableCollisionListener()
        b2.enableCollisionListener()
        b1.setBodyLinearVelocity(1f, 0f)
        b2.setBodyLinearVelocity(-1f, 0f)

        world.gravity = Vector2.Zero
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        b1.draw(g)
        b2.draw(g)

        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        g.drawSchoolLogoUpperRight()
        g.drawFPS()
    }
}

fun main(args: Array<String>) {
  DemoSlowCollisions()
}
