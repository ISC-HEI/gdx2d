package ch.hevs.gdx2d.demos.physics.rocket

import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * Control a spaceship with your keyboard.
 *
 *
 * Demonstrate how to apply forces to physics objects and how to draw images
 * (textures) for them.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.1
 */
class DemoPhysicsRocket : PortableApplication() {

    // Physics related
    internal lateinit var dbgRenderer: DebugRenderer
    internal var world = PhysicsWorld.getInstance()

    // Drawing related
    internal lateinit var ship: Spaceship

    override fun onInit() {
        setTitle("Rocket with physics")
        Logger.log("Use the arrows keys to move the spaceship.")

        // No gravity in this world
        world.gravity = Vector2(0f, 0f)

        dbgRenderer = DebugRenderer()

        // Create the obstacles in the scene
        PhysicsScreenBoundaries(windowWidth.toFloat(), windowHeight.toFloat())

        // Our spaceship
        ship = Spaceship(Vector2((windowWidth / 2).toFloat(),
                (windowHeight / 2).toFloat()))

    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()
        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        // Draw the DebugRenderer as helper
        dbgRenderer.render(world, g.camera.combined)

        ship.draw(g) // Draw the spaceship image

        g.drawStringCentered(400f, "Use the keys to control the rocket")
        g.drawFPS()
        g.drawSchoolLogo()
    }

    override fun onKeyUp(keycode: Int) {
        when (keycode) {
            Input.Keys.LEFT -> ship.thrustLeft = false
            Input.Keys.RIGHT -> ship.thrustRight = false
            Input.Keys.UP -> ship.thrustUp = 0f
            else -> {
            }
        }

    }

    override fun onKeyDown(keycode: Int) {
        when (keycode) {
            Input.Keys.LEFT -> ship.thrustLeft = true
            Input.Keys.RIGHT -> ship.thrustRight = true
            Input.Keys.UP -> ship.thrustUp = Spaceship.MAX_THRUST
            else -> {
            }
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoPhysicsRocket()
        }
    }
}
