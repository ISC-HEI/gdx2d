package ch.hevs.gdx2d.demos.tween

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject

/**
 * A simple ball object.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 2.0
 */
class Ball(var posx: Float, var posy: Float) extends DrawableObject {
  private val img = new BitmapImage("images/soccer.png")

  override def draw(g: GdxGraphics): Unit = {
    draw(g, 1.0f)
  }

  def draw(g: GdxGraphics, scale: Float): Unit = {
    g.drawTransformedPicture(posx, posy, 0f, scale, img)
  }
}
