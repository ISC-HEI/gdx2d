package ch.hevs.gdx2d.demos.scrolling.objects

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject

/** A simple cloud that does nothing special. */
class Cloud(var x: Int, var y: Int) extends DrawableObject {
  private val cloudImage = new BitmapImage("images/cloud_1.png")

  override def draw(g: GdxGraphics): Unit =
    g.drawPicture(x.toFloat, y.toFloat, cloudImage)
}
