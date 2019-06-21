package ch.hevs.gdx2d.demos.physics.car

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

import ch.hevs.gdx2d.demos.physics.car.components.Car


/**
 * Shows how to make a top-down car with physics.
 * Unfortunately, it is not as simple as it seems..
 *
 * @author Pierre-André Mudry
 * @version 1.1
 */
class DemoCarDriving : PortableApplication() {

    lateinit var dbgRenderer: DebugRenderer
    var world = PhysicsWorld.getInstance()
    lateinit var c1: Car

    override fun onInit() {
        setTitle("Car driving")
        Logger.log("Use the arrows to move the car")

        // No gravity in this world
        world.gravity = Vector2(0f, 0f)

        dbgRenderer = DebugRenderer()

        // Create the obstacles in the scene
        PhysicsScreenBoundaries(windowWidth.toFloat(), windowHeight.toFloat())

        // Our car
        c1 = Car(30f, 70f, Vector2(200f, 200f), Math.PI.toFloat(), 10f, 30f, 15f)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        // Physics update
        PhysicsWorld.updatePhysics(Gdx.graphics.deltaTime)

        /**
         * Move the car according to key presses
         */
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
            c1.accelerate = true
        else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
            c1.brake = true
        else {
            c1.accelerate = false
            c1.brake = false
        }

        /**
         * Turn the car according to key presses
         */
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            c1.steer_left = true
            c1.steer_right = false
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            c1.steer_right = true
            c1.steer_left = false
        } else {
            c1.steer_left = false
            c1.steer_right = false
        }

        c1.update(Gdx.graphics.deltaTime)
        c1.draw(g)

        dbgRenderer.render(world, g.camera.combined)

        g.drawFPS()
        g.drawSchoolLogo()
    }
}

fun main(args: Array<String>) {
  DemoCarDriving()
}
