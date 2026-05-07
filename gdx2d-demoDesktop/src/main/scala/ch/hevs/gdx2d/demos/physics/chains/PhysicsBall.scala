package ch.hevs.gdx2d.demos.physics.chains

import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * A simple physics ball that can be drawn.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class PhysicsBall(name: String, position: Vector2, radius: Int, var c: Color)
  extends PhysicsCircle(name, position, radius.toFloat) with DrawableObject {

  def this(position: Vector2, radius: Int, c: Color) = this("ball", position, radius, c)
  def this(position: Vector2, radius: Int) = this("ball", position, radius, Color.PINK)

  override def draw(g: GdxGraphics): Unit = {
    val pos = getBodyPosition
    val r = getBodyRadius

    if (PhysicsBall.transparentRendering) {
      g.drawCircle(pos.x, pos.y, r)
    } else {
      g.drawCircle(pos.x, pos.y, r, Color.BLACK)
      g.drawFilledCircle(pos.x, pos.y, r - 1, c)
    }
  }
}

object PhysicsBall {
  var transparentRendering = false
  def changeRendering(): Unit = {
    transparentRendering = !transparentRendering
  }
}
