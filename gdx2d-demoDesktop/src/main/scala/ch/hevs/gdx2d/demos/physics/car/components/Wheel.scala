package ch.hevs.gdx2d.demos.physics.car.components

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.joints.{PrismaticJointDef, RevoluteJointDef}

/**
 * A wheel of the car.
 *
 * @author Pierre-André Mudry
 * @version 2.0
 */
class Wheel(val car: Car, wheelPos: Vector2, width: Float, length: Float, val revolving: Boolean, val powered: Boolean) {
  var body: Body = _

  init()

  private def init(): Unit = {
    val world = PhysicsWorld.getInstance()
    val x = PhysicsConstants.coordPixelsToMeters(wheelPos)
    val pos = car.carbox.getBody.getWorldPoint(x)

    val wheel = new PhysicsBox("wheel", PhysicsConstants.coordMetersToPixels(pos), width, length / 2, car.carbox.getBodyAngle)
    body = wheel.getBody

    if (revolving) {
      val jointdef = new RevoluteJointDef()
      jointdef.initialize(car.carbox.getBody, body, body.getWorldCenter)
      jointdef.enableMotor = false
      world.createJoint(jointdef)
    } else {
      val jointdef = new PrismaticJointDef()
      jointdef.initialize(car.carbox.getBody, body, body.getWorldCenter, new Vector2(1f, 0f))
      jointdef.enableLimit = true
      jointdef.upperTranslation = 0f
      jointdef.lowerTranslation = 0f
      world.createJoint(jointdef)
    }
  }

  def localVelocity: Vector2 =
    car.carbox.getBody.getLocalVector(car.carbox.getBody.getLinearVelocityFromLocalPoint(body.getPosition))

  def directionVector: Vector2 = {
    val dir = if (localVelocity.y > 0) new Vector2(0f, 1f) else new Vector2(0f, -1f)
    dir.rotate(math.toDegrees(body.getAngle.toDouble).toFloat)
  }

  def killVelocityVector: Vector2 = {
    val velocity = body.getLinearVelocity
    val sidewaysAxis = directionVector
    val dotprod = velocity.dot(sidewaysAxis)
    new Vector2(sidewaysAxis.x * dotprod, sidewaysAxis.y * dotprod)
  }

  def setAngle(angle: Float): Unit = {
    body.setTransform(body.getPosition, car.carbox.getBodyAngle + math.toRadians(angle.toDouble).toFloat)
  }

  def killSidewaysVelocity(): Unit = {
    body.setLinearVelocity(killVelocityVector)
  }
}
