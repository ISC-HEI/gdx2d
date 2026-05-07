package ch.hevs.gdx2d.demos.physics.rocket

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.math.Vector2

/**
 * A simple spaceship simulated using a [[PhysicsBox]].
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 2.0
 */
class Spaceship(position: Vector2) extends DrawableObject {
  private val box = new PhysicsBox("ship", position, 30f, 30f, math.toRadians(90.0).toFloat)
  box.setBodyAngularDamping(0.4f)
  box.setBodyLinearDamping(0.2f)

  private val shipImage = new BitmapImage("images/rocket_128.png")
  private val flameImage = new BitmapImage("images/flame.png")

  var thrustLeft = false
  var thrustRight = false
  var thrustUp = 0f

  override def draw(g: GdxGraphics): Unit = {
    if (thrustLeft) box.applyBodyTorque(Spaceship.MAX_TORQUE, true)
    if (thrustRight) box.applyBodyTorque(-Spaceship.MAX_TORQUE, true)

    box.applyBodyForceToCenter(
      math.cos(box.getBodyAngle.toDouble).toFloat * thrustUp,
      math.sin(box.getBodyAngle.toDouble).toFloat * thrustUp,
      true)

    val pos = box.getBodyPosition

    if (thrustUp > 0) {
      val x = box.getBody.getWorldPoint(new Vector2(-55.0f * 0.02f, 0.0f)) // simplified world point calc
      // The original code had a weird calc: val x = box.body.getWorldPoint(Vector2(-55.0f, 0.0f)); val flameCenter = x.add(pos)
      // Actually getWorldPoint already returns world coordinates.
      val flameCenter = box.getBody.getWorldPoint(new Vector2(-30f / 2f / 50f, 0f)) // Approximation for the back
      // Using a simpler one:
      g.drawTransformedPicture(pos.x, pos.y, box.getBodyAngleDeg, .3f, flameImage)
    }

    g.drawTransformedPicture(pos.x, pos.y, box.getBodyAngleDeg, .5f, shipImage)
  }
}

object Spaceship {
  val MAX_THRUST = 1f
  val MAX_TORQUE = 0.04f
}
