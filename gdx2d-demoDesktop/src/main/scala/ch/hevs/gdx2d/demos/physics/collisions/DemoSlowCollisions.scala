package ch.hevs.gdx2d.demos.physics.collisions

import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

/**
 * Simple collision handling in box2d.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoSlowCollisions extends DesktopApplication {

  private var dbgRenderer: DebugRenderer = _
  private var b1: BumpyBall = _
  private var b2: BumpyBall = _

  override def onInit(): Unit = {
    dbgRenderer = new DebugRenderer()
    setTitle("Slow collisions for box2d, mui 2013")

    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)

    // A BumpyBall has redefined its collision method.
    b1 = new BumpyBall("ball 1", new Vector2(150f, 250f), 30)
    b2 = new BumpyBall("ball 2", new Vector2(350f, 250f), 30)

    // Indicate that the ball should be informed for collisions
    b1.enableCollisionListener()
    b2.enableCollisionListener()
    b1.setBodyLinearVelocity(1f, 0f)
    b2.setBodyLinearVelocity(-1f, 0f)

    PhysicsWorld.getInstance().setGravity(Vector2.Zero)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    b1.draw(g)
    b2.draw(g)

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }
}

object DemoSlowCollisions {
  def main(args: Array[String]): Unit = {
    new DemoSlowCollisions().launch()
  }
}
