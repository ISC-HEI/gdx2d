package ch.hevs.gdx2d.demos.physics.car

import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

import ch.hevs.gdx2d.demos.physics.car.components.Car

/**
 * Shows how to make a top-down car with physics.
 *
 * @author Pierre-André Mudry
 * @version 2.0
 */
class DemoCarDriving extends DesktopApplication {

  private var dbgRenderer: DebugRenderer = _
  private var world: World = _
  private var c1: Car = _

  override def onInit(): Unit = {
    setTitle("Car driving")
    Logger.log("Use the arrows to move the car")

    world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0f, 0f))

    dbgRenderer = new DebugRenderer()

    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)

    c1 = new Car(30f, 70f, new Vector2(200f, 200f), math.Pi.toFloat, 10f, 30f, 15f)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
      c1.accelerate = true
    } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
      c1.brake = true
    } else {
      c1.accelerate = false
      c1.brake = false
    }

    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
      c1.steer_left = true
      c1.steer_right = false
    } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
      c1.steer_right = true
      c1.steer_left = false
    } else {
      c1.steer_left = false
      c1.steer_right = false
    }

    c1.update(Gdx.graphics.getDeltaTime)
    c1.draw(g)

    dbgRenderer.render(world, g.getCamera.combined)

    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoCarDriving {
  def main(args: Array[String]): Unit = {
    new DemoCarDriving().launch()
  }
}
