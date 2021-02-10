package ch.hevs.gdx2d.demos.physics.rocket

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import com.badlogic.gdx.math.Vector2


/**
 * A simple spaceship simulated using a [PhysicsBox].
 *
 *
 * The spaceship can move and turn. A nice image is used to draw the spaceship.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 1.1
 */
class Spaceship(position: Vector2) : DrawableObject {
    protected var box: AbstractPhysicsObject
    // Motor related
    var thrustLeft = false
    var thrustRight = false
    var thrustUp = 0f

    init {
        // The front of the rocket is up by default
        box = PhysicsBox("ship", position, 30f, 30f, Math.toRadians(90.0).toFloat())
        box.bodyAngularDamping = 0.4f
        box.bodyLinearDamping = 0.2f

        // The spaceship image
        shipImage = BitmapImage("images/rocket_128.png")
        flameImage = BitmapImage("images/flame.png")
    }

    override fun draw(g: GdxGraphics) {
        // Make the ship turn if required
        if (thrustLeft)
            box.applyBodyTorque(MAX_TORQUE, true)

        if (thrustRight)
            box.applyBodyTorque(-MAX_TORQUE, true)

        // Let's move the ship with a force. The force
        // is always directed in the direction of the tip of the rocket
        box.applyBodyForceToCenter(Math.cos(box.bodyAngle.toDouble()).toFloat() * thrustUp, Math.sin(box.bodyAngle.toDouble()).toFloat() * thrustUp,
                true)

        val pos = box.bodyPosition

        if (thrustUp > 0) {
            // Draw the flame
            val x = box.body.getWorldPoint(Vector2(-55.0f, 0.0f))
            val flameCenter = x.add(pos) // rotation matrix

            // TODO: change the flame size depending on the spaceship thrust
            g.drawTransformedPicture(flameCenter.x, flameCenter.y,
                    box.bodyAngleDeg, .3f, flameImage)
        }

        // Draws the ship
        g.drawTransformedPicture(pos.x, pos.y, box.bodyAngleDeg, .5f, shipImage)
    }

    companion object {
        val MAX_THRUST = 1f
        val MAX_TORQUE = 0.04f
        // Drawing related
        protected lateinit var shipImage: BitmapImage
        protected lateinit var flameImage: BitmapImage
    }

}
