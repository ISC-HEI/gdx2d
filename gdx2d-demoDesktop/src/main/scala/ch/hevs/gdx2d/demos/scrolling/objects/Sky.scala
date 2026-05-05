package ch.hevs.gdx2d.demos.scrolling.objects

import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Pixmap, Texture}
import com.badlogic.gdx.graphics.Pixmap.Format

/**
 * A quite complex background image that is linked to the camera.
 * Note that we use a texture to draw the background only *once* for
 * performance reasons.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class Sky extends DrawableObject {
  private val width: Int = Gdx.graphics.getWidth
  private val height: Int = Gdx.graphics.getHeight

  private val p: Pixmap = {
    val pm = new Pixmap(width, height, Format.RGB888)
    // Fill the sky with a nice color gradient
    for (i <- 0 until height) {
      val col = ColorUtils.hsvToRgb(0.55f, i.toFloat / height, 1.0f)
      pm.setColor(ColorUtils.intToColor(col))
      pm.drawLine(0, i, width, i)
    }
    pm
  }

  private val t: Texture = new Texture(p)

  override def draw(g: GdxGraphics): Unit = {
    g.drawBackground(t, 0f, 0f)
  }
}
