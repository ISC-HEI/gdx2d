package ch.hevs.gdx2d.demos.physics.pinball


import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

class SensorSpinner(name: String, position: Vector2, private val width: Float, private val height: Float, protected var sprites: Array<TextureRegion>) : Sensor(name, position, width, height), DrawableObject {
    protected var speed: Float = 0.toFloat()
    protected var pos: Float = 0.toFloat()

    override fun draw(g: GdxGraphics) {
        pos += speed
        speed -= speed * 0.8f * Gdx.graphics.deltaTime
        val i = pos.toInt() % sprites.size

        val w = sprites[i].regionWidth.toFloat()
        val h = sprites[i].regionHeight.toFloat()
        g.draw(sprites[i], bodyPosition.x - w / 2, bodyPosition.y - h / 2, w / 2, h / 2, w, h, width / w, height / h, 0f)
    }

    override fun collision(theOtherObject: AbstractPhysicsObject, energy: Float) {
        super.collision(theOtherObject, energy)
        println(javaClass.name + " uohoho " + theOtherObject.body.linearVelocity)
        speed = theOtherObject.body.linearVelocity.len() / 2
    }
}
