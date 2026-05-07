package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

/**
 * WORK IN PROGRESS, DO NOT USE.
 */
class Ball(name: String, position: Vector2, private val radius: Float, protected var sprites: Array[TextureRegion])
  extends PhysicsCircle(name, position, radius) with DrawableObject {

  override def draw(g: GdxGraphics): Unit = {
    val i = (getBodyPosition.x + getBodyPosition.y).toInt % sprites.length
    val w = sprites(i).getRegionWidth.toFloat
    val h = sprites(i).getRegionHeight.toFloat

    g.draw(sprites(i), getBodyPosition.x - w / 2, getBodyPosition.y - h / 2, w / 2, h / 2, w, h, 2 * radius / w, 2 * radius / h, getBodyAngleDeg)
  }
}
