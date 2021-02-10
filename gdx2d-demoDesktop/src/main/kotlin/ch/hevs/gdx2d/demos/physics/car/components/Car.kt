package ch.hevs.gdx2d.demos.physics.car.components

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import java.util.ArrayList

/**
 * A top-view physics car Original example adapted from
 * http://www.level1gamer.com/2012/10/24/top-down-car-using-libgdx-and-box2d/
 *
 * @author Pierre-André Mudry
 * @version 1.1
 */
class Car
/**
 * Builds a physically "accurate" car
 *
 * @param width         The width of the body of the car
 * @param length        The length of the body of the car
 * @param position      The start position of the car
 * @param angle         The starting angle of the car, in radians
 * @param power         The maximum horse power of the car
 * @param maxSteerAngle The maximum steering angle for the wheels, in degrees
 * @param maxSpeed      The maximum speed for the car
 */
(width: Float, length: Float, position: Vector2, angle: Float,
 protected var power: Float, protected var maxSteerAngle: Float, protected var maxSpeed: Float) : DrawableObject {

    val wheelWidth = 16
    val wheelHeight = 60
    /**
     * Use this to control the car
     */
    var steer_left: Boolean = false
    var steer_right: Boolean = false
    var accelerate: Boolean = false
    var brake: Boolean = false
    protected var wheelAngle: Float = 0.toFloat()
    var carbox: PhysicsBox
    protected var wheels: MutableList<Wheel>
    /**
     * How the car energy is dissipated when no longer
     * accelerating. 0 would not brake the car at all
     * and 1 will make it stop very quickly.
     */
    var slowingFactor = 0.5f

    /**
     * @return car's velocity vector relative to the car
     */
    protected//return carbox.getBodyLocalVector(carbox.getBodyLinearVelocityFromLocalPoint(new Vector2(0,0)));//).getLocalVector(carBox.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
    val localVelocity: Vector2
        get() = carbox.body.getLocalVector(carbox.body.getLinearVelocityFromLocalPoint(Vector2(0f, 0f)))

    protected val revolvingWheels: List<Wheel>
        get() {
            val revolvingWheels = ArrayList<Wheel>()
            for (wheel in this.wheels) {
                if (wheel.revolving)
                    revolvingWheels.add(wheel)
            }
            return revolvingWheels
        }

    protected val poweredWheels: List<Wheel>
        get() {
            val poweredWheels = ArrayList<Wheel>()
            for (wheel in this.wheels) {
                if (wheel.powered)
                    poweredWheels.add(wheel)
            }
            return poweredWheels
        }

    /**
     * @return The current speed of the car, in km/h
     */
    val speedKMH: Float
        get() {
            val velocity = carbox.bodyLinearVelocity
            val len = velocity.len()
            return len / 1000 * 3600
        }

    init {

        carbox = PhysicsBox("carCenter", position, width, length, angle)
        carbox.setCollisionGroup(-1)

        // Initialize wheels
        this.wheels = ArrayList()

        val wheelOffset = Vector2(25f, 35f)

        // Topleft wheel
        this.wheels.add(Wheel(this, wheelOffset.cpy().scl(-1f, -1f), wheelWidth.toFloat(), wheelHeight.toFloat(), true, true))
        // Topright wheel
        this.wheels.add(Wheel(this, wheelOffset.cpy().scl(1f, -1f), wheelWidth.toFloat(), wheelHeight.toFloat(), true, true))
        // Backleft wheel
        this.wheels.add(Wheel(this, wheelOffset.cpy().scl(-1f, 1f), wheelWidth.toFloat(), wheelHeight.toFloat(), false, false))
        // Backright wheel
        this.wheels.add(Wheel(this, wheelOffset.cpy().scl(1f, 1f), wheelWidth.toFloat(), wheelHeight.toFloat(), false, false))
    }

    /**
     * Sets the speed of the car in kilometers per hour
     *
     * @param speed The speed of the car, in km/h
     */
    fun setSpeed(speed: Float) {
        /*
		 * speed - speed in kilometers per hour
		 */
        var velocity = carbox.bodyLinearVelocity
        velocity = velocity.nor()
        velocity = Vector2(velocity.x * (speed * 1000.0f / 3600.0f),
                velocity.y * (speed * 1000.0f / 3600.0f))
        carbox.bodyLinearVelocity = velocity
    }

    /**
     * Updates some physical parameters which are specific to a car
     *
     * @param deltaTime
     */
    fun update(deltaTime: Float) {

        // 1. KILL SIDEWAYS VELOCITY
        for (wheel in wheels) {
            wheel.killSidewaysVelocity()
        }

        // 2. SET WHEEL ANGLE
        // calculate the change in wheel's angle for this update
        val incr = this.maxSteerAngle * deltaTime * 5f

        if (steer_left) {
            /**
             * Augment angle and check max steering
             */
            this.wheelAngle = Math.min(Math.max(this.wheelAngle, 0f) + incr,
                    this.maxSteerAngle)
        } else if (steer_right) {
            /**
             * Diminish angle and check min steering
             */
            this.wheelAngle = Math.max(Math.min(this.wheelAngle, 0f) - incr,
                    -this.maxSteerAngle)
        } else {
            this.wheelAngle = 0f
        }

        // update revolving wheels
        for (wheel in this.revolvingWheels) {
            wheel.setAngle(this.wheelAngle)
        }

        // 3. APPLY FORCE TO WHEELS
        /**
         * Vector pointing in the force direction. Will be applied to
         * the wheel and is relative to the wheel
         */
        var baseVector = Vector2.Zero

        // if accelerator is pressed down and speed limit has not been reached,
        // go forwards
        if (accelerate && this.speedKMH < this.maxSpeed) {
            baseVector = Vector2(0f, -1f)
        } else if (brake) {
            // braking, but still moving forwards - increased force
            if (this.localVelocity.y < 0)
                baseVector = Vector2(0f, 1.3f)
            else
            // Limit maximal reverse speed
                if (speedKMH < maxSpeed)
                    baseVector = Vector2(0f, 0.2f)// going in reverse - less force
        } else {
            // slow down if not accelerating
            baseVector = Vector2(0f, 0f)

            // Stop the car when it is going slow
            if (this.speedKMH < 4)
                this.setSpeed(0f)
            else {
                if (this.localVelocity.y < 0)
                    baseVector = Vector2(0f, slowingFactor)
                else if (this.localVelocity.y > 0)
                    baseVector = Vector2(0f, -slowingFactor)
            }
        }

        // multiply by engine power, which gives us a force vector relative to
        // the wheel
        val forceVector = baseVector.scl(power)

        // Apply force to each wheel
        for (wheel in this.poweredWheels) {
            val position = wheel.body.worldCenter
            wheel.body.applyForce(wheel.body.getWorldVector(Vector2(
                    forceVector.x, forceVector.y)), position, true)
        }
    }

    /**
     * Draws the car
     */
    override fun draw(g: GdxGraphics) {
        val pos = carbox.bodyPosition
        g.drawFilledCircle(pos.x, pos.y, 10f, Color.BLUE)

        //		Vector2 v = getLocalVelocity();
        //		v.scl(100);
        //		g.drawLine(100, 100, 100 + v.x, 100+ v.y);
    }

}
