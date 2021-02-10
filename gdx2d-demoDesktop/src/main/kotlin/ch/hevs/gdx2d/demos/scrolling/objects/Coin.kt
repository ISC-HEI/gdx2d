package ch.hevs.gdx2d.demos.scrolling.objects

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject

/**
 * An animated coin that scales with time
 *
 * @author Pierre-Andre Mudry (mui)
 */
class Coin(var posx: Int, var posy: Int) : DrawableObject {

    val img = BitmapImage("images/retro-coin.png")
    var scale = 0.4f
    var direction = 0.01f

    override fun draw(g: GdxGraphics) {
        g.drawTransformedPicture(posx.toFloat(), posy.toFloat(), 0f, scale, img)

        // If too small or too big, change direction of scaling
        if (scale < 0.2 || scale > 0.45)
            direction *= -1f

        scale += direction
    }

}
