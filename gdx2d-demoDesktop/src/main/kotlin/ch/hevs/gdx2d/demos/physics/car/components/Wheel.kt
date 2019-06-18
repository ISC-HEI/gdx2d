package ch.hevs.gdx2d.demos.physics.car.components

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef

/**
 * A wheel of the car, adapted from
 * http://www.level1gamer.com/2012/10/24/top-down-car-using-libgdx-and-box2d/
 *
 * @author Pierre-André Mudry
 * @version 1.2
 */
class Wheel(var car: Car//car this wheel belongs to
            , wheelPos: Vector2, width: Float, length: Float, var revolving: Boolean // does this wheel revolve when steering?
            , var powered: Boolean // is this wheel powered?
) {
    var body: Body

    /**
     * @return The velocity vector, relative to the car
     */
    val localVelocity: Vector2
        get() = car.carbox.body.getLocalVector(car.carbox.body.getLinearVelocityFromLocalPoint(body.position))

    /**
     * @return A world, unit vector pointing in the direction this wheel is currently moving
     */
    val directionVector: Vector2
        get() {
            val directionVector: Vector2

            if (this.localVelocity.y > 0)
                directionVector = Vector2(0f, 1f)
            else
                directionVector = Vector2(0f, -1f)

            return directionVector.rotate(Math.toDegrees(this.body.angle.toDouble()).toFloat())
        }

    /**
     * Subtracts sideways velocity from this wheel's velocity vector
     *
     * @return The remaining front-facing velocity vector
     */
    val killVelocityVector: Vector2
        get() {
            val velocity = body.linearVelocity
            val sidewaysAxis = directionVector
            val dotprod = velocity.dot(sidewaysAxis)
            return Vector2(sidewaysAxis.x * dotprod, sidewaysAxis.y * dotprod)
        }

    init {

        val world = PhysicsWorld.getInstance()

        val x = PhysicsConstants.coordPixelsToMeters(wheelPos)

        // Convert car position to pixels
        val pos = car.carbox.body.getWorldPoint(x)

        // Create the wheel
        val wheel = PhysicsBox("wheel", PhysicsConstants.coordMetersToPixels(pos), width, length / 2, car.carbox.bodyAngle)
        this.body = wheel.body

        // Create a revoluting joint to connect wheel to body
        if (this.revolving) {
            val jointdef = RevoluteJointDef()
            jointdef.initialize(car.carbox.body, this.body, this.body.worldCenter)
            jointdef.enableMotor = false //we'll be controlling the wheel's angle manually
            world.createJoint(jointdef)
        } else {
            val jointdef = PrismaticJointDef()
            jointdef.initialize(car.carbox.body, this.body, this.body.worldCenter, Vector2(1f, 0f))
            jointdef.enableLimit = true
            jointdef.upperTranslation = 0f
            jointdef.lowerTranslation = jointdef.upperTranslation
            world.createJoint(jointdef)
        }
    }

    /**
     * @param angle The wheel angle (in degrees), relative to the car
     */
    fun setAngle(angle: Float) {
        body.setTransform(body.position, car.carbox.bodyAngle + Math.toRadians(angle.toDouble()).toFloat())
    }

    /**
     * Removes sideways velocity from this wheel
     */
    fun killSidewaysVelocity() {
        val v = killVelocityVector
        body.linearVelocity = v
    }
}
