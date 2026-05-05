package ch.hevs.gdx2d.demos.physics.car.components

import scala.collection.mutable.ArrayBuffer

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * A top-view physics car. Original example adapted from
 * http://www.level1gamer.com/2012/10/24/top-down-car-using-libgdx-and-box2d/
 *
 * @author Pierre-André Mudry
 * @version 2.0
 */
class Car(width: Float, length: Float, position: Vector2, angle: Float,
          protected val power: Float, protected val maxSteerAngle: Float, protected val maxSpeed: Float) extends DrawableObject {

  private val wheelWidth = 16
  private val wheelHeight = 60

  var steer_left: Boolean = false
  var steer_right: Boolean = false
  var accelerate: Boolean = false
  var brake: Boolean = false
  protected var wheelAngle: Float = 0f
  val carbox: PhysicsBox = new PhysicsBox("carCenter", position, width, length, angle)
  carbox.setCollisionGroup(-1)

  protected var wheels: ArrayBuffer[Wheel] = new ArrayBuffer[Wheel]()
  var slowingFactor = 0.5f

  initWheels()

  private def initWheels(): Unit = {
    val wheelOffset = new Vector2(25f, 35f)
    // Topleft
    wheels += new Wheel(this, wheelOffset.cpy().scl(-1f, -1f), wheelWidth.toFloat, wheelHeight.toFloat, true, true)
    // Topright
    wheels += new Wheel(this, wheelOffset.cpy().scl(1f, -1f), wheelWidth.toFloat, wheelHeight.toFloat, true, true)
    // Backleft
    wheels += new Wheel(this, wheelOffset.cpy().scl(-1f, 1f), wheelWidth.toFloat, wheelHeight.toFloat, false, false)
    // Backright
    wheels += new Wheel(this, wheelOffset.cpy().scl(1f, 1f), wheelWidth.toFloat, wheelHeight.toFloat, false, false)
  }

  protected def getLocalVelocity: Vector2 = carbox.getBody.getLocalVector(carbox.getBody.getLinearVelocityFromLocalPoint(new Vector2(0f, 0f)))

  protected def getRevolvingWheels: Seq[Wheel] = wheels.filter(_.revolving).toSeq
  protected def getPoweredWheels: Seq[Wheel] = wheels.filter(_.powered).toSeq

  def speedKMH: Float = {
    val velocity = carbox.getBodyLinearVelocity
    velocity.len() / 1000f * 3600f
  }

  def setSpeed(speed: Float): Unit = {
    var velocity = carbox.getBodyLinearVelocity.nor()
    velocity = new Vector2(velocity.x * (speed * 1000.0f / 3600.0f), velocity.y * (speed * 1000.0f / 3600.0f))
    carbox.setBodyLinearVelocity(velocity)
  }

  def update(deltaTime: Float): Unit = {
    for (wheel <- wheels) wheel.killSidewaysVelocity()

    val incr = maxSteerAngle * deltaTime * 5f
    if (steer_left) {
      wheelAngle = math.min(math.max(wheelAngle, 0f) + incr, maxSteerAngle)
    } else if (steer_right) {
      wheelAngle = math.max(math.min(wheelAngle, 0f) - incr, -maxSteerAngle)
    } else {
      wheelAngle = 0f
    }

    for (wheel <- getRevolvingWheels) wheel.setAngle(wheelAngle)

    var baseVector = new Vector2(0f, 0f)
    if (accelerate && speedKMH < maxSpeed) {
      baseVector = new Vector2(0f, -1f)
    } else if (brake) {
      if (getLocalVelocity.y < 0) baseVector = new Vector2(0f, 1.3f)
      else if (speedKMH < maxSpeed) baseVector = new Vector2(0f, 0.2f)
    } else {
      if (speedKMH < 4) setSpeed(0f)
      else {
        if (getLocalVelocity.y < 0) baseVector = new Vector2(0f, slowingFactor)
        else if (getLocalVelocity.y > 0) baseVector = new Vector2(0f, -slowingFactor)
      }
    }

    val forceVector = baseVector.scl(power)
    for (wheel <- getPoweredWheels) {
      val pos = wheel.body.getWorldCenter
      wheel.body.applyForce(wheel.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), pos, true)
    }
  }

  override def draw(g: GdxGraphics): Unit = {
    val pos = carbox.getBodyPosition
    g.drawFilledCircle(pos.x, pos.y, 10f, Color.BLUE)
  }
}
