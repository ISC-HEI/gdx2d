package ch.hevs.gdx2d.demos.scrolling.objects

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject

/** An animated coin that scales with time. */
class Coin(var posx: Int, var posy: Int) extends DrawableObject {
  private val img = new BitmapImage("images/retro-coin.png")
  private var scale: Float = 0.4f
  private var direction: Float = 0.01f

  override def draw(g: GdxGraphics): Unit = {
    g.drawTransformedPicture(posx.toFloat, posy.toFloat, 0f, scale, img)
    if (scale < 0.2f || scale > 0.45f) direction *= -1f
    scale += direction
  }
}
