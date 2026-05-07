package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align

class ScoreAnimation(private val msg: String, private val x: Float, private val y: Float) extends TemporaryDrawable {

  private var duration: Float = 1.0f
  private val startDuration: Float = 1.0f

  override def isDone: Boolean = duration < 0

  override def draw(g: GdxGraphics): Unit = {
    duration -= Gdx.graphics.getDeltaTime
    g.setColor(new Color(Color.BLACK).mul(duration / startDuration))
    g.drawString(x, y, msg, Align.center)
  }
}
