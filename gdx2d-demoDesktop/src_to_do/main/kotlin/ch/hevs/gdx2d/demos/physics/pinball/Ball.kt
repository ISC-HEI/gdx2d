package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2


/**
 * WORK IN PROGRESS, DO NOT USE
 */
class Ball(name: String, position: Vector2, private val radius: Float, protected var sprites: Array<TextureRegion>) : PhysicsCircle(name, position, radius), DrawableObject {

    override fun draw(g: GdxGraphics) {
        val i = (bodyPosition.x + bodyPosition.y).toInt() % sprites.size
        val w = sprites[i].regionWidth.toFloat()
        val h = sprites[i].regionHeight.toFloat()

        g.draw(sprites[i], bodyPosition.x - w / 2, bodyPosition.y - h / 2, w / 2, h / 2, w, h, 2 * radius / w, 2 * radius / h, bodyAngleDeg)
    }
}
