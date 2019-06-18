package ch.hevs.gdx2d.demos.tween

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject

/**
 * A simple ball object
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.2
 */
class Ball(var posx: Float, var posy: Float) : DrawableObject {
    val img = BitmapImage("images/soccer.png")

    override fun draw(g: GdxGraphics) {
        draw(g, 1.0f)
    }

    fun draw(g: GdxGraphics, scale: Float) {
        g.drawTransformedPicture(posx, posy, 0f, scale, img)
    }
}
