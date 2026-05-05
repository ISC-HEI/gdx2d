package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import com.badlogic.gdx.math.Vector2

class Sensor(name: String, position: Vector2, width: Float, height: Float) extends PhysicsStaticBox(name, position, width, height) {
  setSensor(true)
}
