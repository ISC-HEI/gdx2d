package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align

class ScoreAnimation(private val msg: String, private val x: Float, private val y: Float) : TemporaryDrawable {

    private var duration: Float = 0.toFloat()
    private val start_duration: Float

    override val isDone: Boolean
        get() = duration < 0

    init {
        this.start_duration = 1.0f
        this.duration = 1.0f
    }

    override fun draw(g: GdxGraphics) {
        duration -= Gdx.graphics.deltaTime
        g.setColor(Color(Color.BLACK).mul(duration / start_duration))
        g.drawString(x, y, msg, Align.center)
    }
}
