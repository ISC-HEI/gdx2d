package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

class SensorSpinner(name: String, position: Vector2, private val width: Float, private val height: Float, protected var sprites: Array[TextureRegion])
  extends Sensor(name, position, width, height) with DrawableObject {

  protected var speed: Float = 0f
  protected var pos: Float = 0f

  override def draw(g: GdxGraphics): Unit = {
    pos += speed
    speed -= speed * 0.8f * Gdx.graphics.getDeltaTime
    val i = pos.toInt % sprites.length

    val w = sprites(i).getRegionWidth.toFloat
    val h = sprites(i).getRegionHeight.toFloat
    g.draw(sprites(i), getBodyPosition.x - w / 2, getBodyPosition.y - h / 2, w / 2, h / 2, w, h, width / w, height / h, 0f)
  }

  override def collision(theOtherObject: AbstractPhysicsObject, energy: Float): Unit = {
    super.collision(theOtherObject, energy)
    speed = theOtherObject.getBody.getLinearVelocity.len() / 2f
  }
}
