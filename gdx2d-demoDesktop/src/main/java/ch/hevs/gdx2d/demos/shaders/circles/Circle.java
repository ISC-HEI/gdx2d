package ch.hevs.gdx2d.demos.shaders.circles

import com.badlogic.gdx.math.Vector2

/**
 * A simple circle class for playing with shaders
 *
 * @author Pierre-André Mudry
 */
internal class Circle(x: Int, y: Int) {
    var pos: Vector2

    init {
        pos = Vector2(x.toFloat(), y.toFloat())
    }
}
