package ch.hevs.gdx2d.demos.scrolling.objects

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject

/** A simple pipe that does nothing special. */
class Pipe(var posx: Int, var posy: Int) extends DrawableObject {
  private val img = new BitmapImage("images/pipe.png")

  override def draw(g: GdxGraphics): Unit =
    g.drawPicture(posx.toFloat, posy.toFloat, img)
}
