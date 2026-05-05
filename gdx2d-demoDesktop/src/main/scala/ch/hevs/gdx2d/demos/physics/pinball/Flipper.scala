package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsStaticBox}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

class Flipper(name: String, position: Vector2, width: Float, height: Float, angle: Float, angleVar: Float, protected var sprites: Array[TextureRegion]) {
  protected var flipper: Sub = _
  protected var motor: PhysicsMotor = _

  class Sub(name: String, position: Vector2, private val w: Float, private val h: Float, angle: Float)
    extends PhysicsBox(name, position, w, h, 4.0f, 0.3f, 0.6f, angle) with DrawableObject {

    override def draw(g: GdxGraphics): Unit = {
      val currentFrame = math.abs(getBodyAngleDeg).toInt % sprites.length
      val sw = sprites(currentFrame).getRegionWidth.toFloat
      val sh = sprites(currentFrame).getRegionHeight.toFloat
      g.draw(sprites(currentFrame), getBodyPosition.x - sw / 2, getBodyPosition.y - sh / 2, sw / 2, sh / 2, sw, sh, w / sw, h / sh, getBodyAngleDeg)
    }
  }

  init()

  private def init(): Unit = {
    val frame = new PhysicsStaticBox(name + "_frame", position, .1f, .1f)
    val flipperPos = new Vector2(position).add(new Vector2(width / 2, 0f).rotate(angle))

    flipper = new Sub(name + "_flipper", flipperPos, width, height, math.toRadians(angle.toDouble).toFloat)
    motor = new PhysicsMotor(frame.getBody, flipper.getBody, frame.getBody.getWorldCenter)

    if (angleVar > 0) {
      motor.setLimits(motor.getAngle, motor.getAngle + math.toRadians(angleVar.toDouble).toFloat)
      motor.initializeMotor(50f, 50f, false)
    } else {
      motor.setLimits(motor.getAngle + math.toRadians(angleVar.toDouble).toFloat, motor.getAngle)
      motor.initializeMotor(-50f, 50f, false)
    }
    motor.enableLimit(true)
  }

  def power(on: Boolean): Unit = {
    motor.enableMotor(on)
  }
}
