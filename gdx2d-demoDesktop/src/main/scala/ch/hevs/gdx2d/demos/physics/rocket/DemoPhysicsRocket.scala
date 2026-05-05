package ch.hevs.gdx2d.demos.physics.rocket

import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Vector2

/**
 * Control a spaceship with your keyboard.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 2.0
 */
class DemoPhysicsRocket extends DesktopApplication {

  private var dbgRenderer: DebugRenderer = _
  private var ship: Spaceship = _

  override def onInit(): Unit = {
    setTitle("Rocket with physics")
    Logger.log("Use the arrows keys to move the spaceship.")

    PhysicsWorld.getInstance().setGravity(new Vector2(0f, 0f))
    dbgRenderer = new DebugRenderer()

    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)
    ship = new Spaceship(new Vector2(getWindowWidth / 2f, getWindowHeight / 2f))
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    dbgRenderer.render(PhysicsWorld.getInstance(), g.getCamera.combined)
    ship.draw(g)

    g.drawStringCentered(400f, "Use the keys to control the rocket")
    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onKeyUp(keycode: Int): Unit = {
    keycode match {
      case Input.Keys.LEFT  => ship.thrustLeft = false
      case Input.Keys.RIGHT => ship.thrustRight = false
      case Input.Keys.UP    => ship.thrustUp = 0f
      case _ => ()
    }
  }

  override def onKeyDown(keycode: Int): Unit = {
    keycode match {
      case Input.Keys.LEFT  => ship.thrustLeft = true
      case Input.Keys.RIGHT => ship.thrustRight = true
      case Input.Keys.UP    => ship.thrustUp = Spaceship.MAX_THRUST
      case _ => ()
    }
  }
}

object DemoPhysicsRocket {
  def main(args: Array[String]): Unit = {
    new DemoPhysicsRocket().launch()
  }
}
