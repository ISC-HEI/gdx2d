package ch.hevs.gdx2d.demos.physics.joints

import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * A simple class to display physics circles with a color.
 *
 * @author Pierre-André Mudry
 * @version 2.0
 */
class CircleParticle(name: String, position: Vector2, radius: Int, var c: Color, restitution: Float, friction: Float)
  extends PhysicsCircle(name, position, radius.toFloat, 1f, restitution, friction) with DrawableObject {

  def this(name: String, position: Vector2, radius: Int) =
    this(name, position, radius, Color.WHITE, 0.001f, 60.2f)

  def this(position: Vector2, radius: Int, c: Color, restitution: Float, friction: Float) =
    this("", position, radius, c, restitution, friction)

  override def draw(g: GdxGraphics): Unit = {
    val pos = getBodyPosition
    val r = getBodyRadius
    g.drawFilledCircle(pos.x, pos.y, r, c)
  }
}
