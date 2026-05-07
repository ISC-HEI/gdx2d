package ch.hevs.gdx2d.demos.physics.particle

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Filter

/**
 * Demonstrates how to render particles.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class Particle(position: Vector2, radius: Int, protected val maxAge: Int)
  extends PhysicsBox(null, position, radius.toFloat, radius.toFloat, 0.12f, 1f, 1f) {

  private val img = new BitmapImage("images/texture.png")
  private var age = 0
  private var initialized = false

  // Particles should not collide together
  val filter = new Filter()
  filter.groupIndex = -1
  f.setFilterData(filter)

  def shouldBeDestroyed: Boolean = age > maxAge

  def step(): Unit = {
    age += 1
  }

  def render(g: GdxGraphics): Unit = {
    val col = g.sbGetColor
    val pos = getBodyPosition

    if (!initialized) {
      g.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE)
      initialized = true
    }

    g.sbSetColor(0.5f, 0.7f, 0.9f, 1.0f - age / (maxAge + 5).toFloat)
    g.draw(img.getRegion, pos.x - img.getImage.getWidth / 2f, pos.y - img.getImage.getHeight / 2f)
    g.sbSetColor(col)
  }

  override def finalize(): Unit = {
    super.finalize()
    // In a real app we'd dispose, but here we're in a demo
  }
}
