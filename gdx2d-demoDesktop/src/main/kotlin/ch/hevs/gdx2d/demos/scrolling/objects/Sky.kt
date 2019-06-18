package ch.hevs.gdx2d.demos.scrolling.objects

import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.Texture

/**
 * A quite complex background image that is linked to the camera.
 * Note that we use a texture to draw the background only *ONCE*
 * for performance reasons.
 *
 * @author Pierre-Andre Mudry (mui)
 * @version 1.2
 */
class Sky : DrawableObject {
    internal var p: Pixmap
    internal var t: Texture
    internal var width: Int = 0
    internal var height: Int = 0

    init {
        width = Gdx.graphics.width
        height = Gdx.graphics.height

        /**
         * For the sky we need to draw directy onto an image.
         * An image is first of all a pixmap.
         */

        p = Pixmap(width, height, Format.RGB888)

        // Fill the sky with a nice color
        for (i in 0 until height) {
            val col = ColorUtils.hsvToRgb(0.55f, i.toFloat() / height, 1.0f)
            p.setColor(ColorUtils.intToColor(col))
            p.drawLine(0, i, width, i)
        }

        // For the pixmap, we need to create a texture
        t = Texture(p)
    }

    override fun draw(g: GdxGraphics) {
        /**
         * Using this method is faster than drawing an image and
         * also makes sure that the image is not moved with the camera (fixed
         * position).
         */
        g.drawBackground(t, 0f, 0f)
    }
}
