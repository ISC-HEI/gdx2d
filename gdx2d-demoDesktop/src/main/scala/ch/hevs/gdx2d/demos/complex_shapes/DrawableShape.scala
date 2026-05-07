package ch.hevs.gdx2d.demos.complex_shapes

import java.util.Random

import com.badlogic.gdx.graphics.Color

/**
 * A drawable shape class for the [[DemoComplexShapes]] demonstration.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DrawableShape(val width: Int,
                    val height: Int,
                    var x: Int,
                    var y: Int,
                    val c: Color) {
  val offset: Float = DrawableShape.rnd.nextFloat() * 45f
}

object DrawableShape {
  private val rnd = new Random()
}
