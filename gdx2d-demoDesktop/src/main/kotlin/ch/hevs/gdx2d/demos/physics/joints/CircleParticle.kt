package ch.hevs.gdx2d.demos.physics.joints

import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * A simple class to display physics circles with a color
 *
 * @author Pierre-André Mudry
 */
class CircleParticle : PhysicsCircle, DrawableObject {
    internal var c = Color.WHITE

    constructor(name: String, position: Vector2, radius: Int) : super(name, position, radius.toFloat(), 1f, 0.001f, 60.2f) {}

    constructor(position: Vector2, radius: Int, c: Color, restitution: Float, friction: Float) : super("", position, radius.toFloat(), 1f, restitution, friction) {
        this.c = c
    }

    override fun draw(g: GdxGraphics) {
        // We have to convert meters (physics) to pixels (display)
        val position = bodyPosition
        val radius = bodyRadius

        // The color is dependent from the last collision time
        g.drawFilledCircle(position.x, position.y, radius, c)
    }
}
