package ch.hevs.gdx2d.demos.physics.chains

import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * A simple physics ball that can be drawn
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
class PhysicsBall : PhysicsCircle, DrawableObject {
    var c: Color

    constructor(position: Vector2, radius: Int, c: Color) : this("ball", position, radius, c)

  constructor(position: Vector2, radius: Int) : this("ball", position, radius)

  constructor(name: String, position: Vector2, radius: Int, c: Color) : super(name, position, radius.toFloat()) {
        this.c = c
    }

    constructor(name: String, position: Vector2, radius: Int) : super(name, position, radius.toFloat()) {
        this.c = Color.PINK
    }

    override fun draw(g: GdxGraphics) {
        val position = bodyPosition
        val radius = bodyRadius

        if (transparentRendering)
            g.drawCircle(position.x, position.y, radius)
        else {
            g.drawCircle(position.x, position.y, radius, Color.BLACK)
            g.drawFilledCircle(position.x, position.y, radius - 1, c)
            //			g.drawFilledBorderedCircle(position.x, position.y, radius, c, Color.BLACK);
        }

    }

    companion object {
        var transparentRendering = false

        fun change_rendering() {
            transparentRendering = !transparentRendering
        }
    }
}
