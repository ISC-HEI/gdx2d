package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2


class Flipper(name: String, position: Vector2, width: Float, height: Float, angle: Float, angle_var: Float, protected var sprites: Array<TextureRegion>) {
    protected var flipper: PhysicsBox
    protected var motor: PhysicsMotor

    internal inner class Sub(name: String, position: Vector2, private val width: Float, private val height: Float, angle: Float, density: Float, restitution: Float, friction: Float) : PhysicsBox("name" + "_flipper", position, width, height, 4.0f, 0.3f, 0.6f, angle), DrawableObject {

        override fun draw(g: GdxGraphics) {
            val currentLFFrame = Math.abs(bodyAngleDeg).toInt() % sprites.size
            val w = sprites[currentLFFrame].regionWidth.toFloat()
            val h = sprites[currentLFFrame].regionHeight.toFloat()
            g.draw(sprites[currentLFFrame], bodyPosition.x - w / 2, bodyPosition.y - h / 2, w / 2, h / 2, w, h, width / w, height / h, bodyAngleDeg)
        }
    }

    init {

        val frame = PhysicsStaticBox(name + "_frame", position, .1f, .1f)
        val flipper_pos = Vector2(position).add(Vector2(width / 2, 0f).rotate(angle))

        flipper = Sub("name" + "_flipper", flipper_pos, width, height, Math.toRadians(angle.toDouble()).toFloat(), 4.0f, 0.3f, 0.6f)
        motor = PhysicsMotor(frame.body, flipper.body, frame.body.worldCenter)

        if (angle_var > 0) {
            motor.setLimits(motor.angle, motor.angle + Math.toRadians(angle_var.toDouble()).toFloat())
            println("limist = " + Math.toDegrees(motor.angle.toDouble()) + " , " + angle_var)
            motor.initializeMotor(50f, 50f, false)
        } else {
            println("limist = " + Math.toDegrees(motor.angle.toDouble()) + " , " + angle_var)
            motor.setLimits(motor.angle + Math.toRadians(angle_var.toDouble()).toFloat(), motor.angle)
            motor.initializeMotor(-50f, 50f, false)
        }
        motor.enableLimit(true)
    }

    fun power(on: Boolean) {
        motor.enableMotor(on)
    }
}
