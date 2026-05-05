package ch.hevs.gdx2d.demos.physics.collisions

import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.math.Vector2

/**
 * Demonstrates how to implements collisions detection with box2d.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class BumpyBall(name: String, position: Vector2, radius: Int)
  extends PhysicsCircle(name, position, radius.toFloat) with DrawableObject {

  private var lastCollision = 0.5f

  /** Called for every collision. */
  override def collision(other: AbstractPhysicsObject, energy: Float): Unit = {
    Logger.log(s"$name collided ${other.name} with energy $energy")
    lastCollision = 1.0f
  }

  override def draw(g: GdxGraphics): Unit = {
    val position = getBodyPosition
    val radius = getBodyRadius

    // The color is dependent from the last collision time
    val c = ColorUtils.hsvToColor(0.8f, 0.98f, lastCollision)
    g.drawFilledCircle(position.x, position.y, radius, c)

    // Return to the normal color after having been excited
    if (lastCollision >= 0.5f) {
      lastCollision -= 0.01f
    }
  }
}
