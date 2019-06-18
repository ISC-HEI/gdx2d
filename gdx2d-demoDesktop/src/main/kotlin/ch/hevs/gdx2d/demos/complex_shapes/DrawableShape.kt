package ch.hevs.gdx2d.demos.complex_shapes

import com.badlogic.gdx.graphics.Color

import java.util.Random

/**
 * A drawable shape class for the [DemoComplexShapes] demonstration
 *
 * @author Pierre-Andre Mudry (mui)
 * @version 1.0
 */
class DrawableShape internal constructor(internal var width: Int, internal var height: Int, internal var x: Int, internal var y: Int, internal var c: Color) {
    internal var offset: Float = 0.toFloat()

    init {
        this.offset = rnd.nextFloat() * 45
    }

    companion object {

        private val rnd = Random()
    }
}
