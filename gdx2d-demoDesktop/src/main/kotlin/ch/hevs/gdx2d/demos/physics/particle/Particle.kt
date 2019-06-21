package ch.hevs.gdx2d.demos.physics.particle

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Filter

/**
 * Demonstrates how to render particles
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.2
 */
class Particle(position: Vector2, radius: Int, protected val maxAge: Int) : PhysicsBox(null, position, radius.toFloat(), radius.toFloat(), 0.12f, 1f, 1f) {
    // Resources MUST not be static
    protected var img = BitmapImage("images/texture.png")
    protected var age = 0
    private var init = false

    init {

        // Particles should not collide together
        val filter = Filter()
        filter.groupIndex = -1
        f.filterData = filter
    }

    @Throws(Throwable::class)
    protected fun finalize() {
        img.dispose()
    }

    fun shouldbeDestroyed(): Boolean {
        return age > maxAge
    }

    fun step() {
        age++
    }

    fun render(g: GdxGraphics) {
        val col = g.sbGetColor()
        val pos = bodyPosition

        if (!init) {
            g.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE)
            init = true
        }

        // Make the particle disappear with time
        g.sbSetColor(.5f, 0.7f, 0.9f, 1.0f - age / (maxAge + 5).toFloat())

        // Draw the particle
        g.draw(img.region, pos.x - img.image.width / 2, pos.y - img.image.height / 2)
        g.sbSetColor(col)
    }
}
