package ch.hevs.gdx2d.demos.scrolling.objects

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject

/**
 * A simple cloud that does nothing special
 *
 * @author Pierre-Andre Mudry (mui)
 */
class Cloud(var x: Int, var y: Int) : DrawableObject {

    internal val cloudImage = BitmapImage("images/cloud_1.png")

    override fun draw(g: GdxGraphics) {
        g.drawPicture(x.toFloat(), y.toFloat(), cloudImage)
    }

}
