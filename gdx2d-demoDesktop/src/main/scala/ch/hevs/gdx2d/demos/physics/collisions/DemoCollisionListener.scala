package ch.hevs.gdx2d.demos.physics.collisions

import java.util.LinkedList

import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Vector2

/**
 * Simple collision handling in box2d.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoCollisionListener extends DesktopApplication {

  private val otherBalls = new LinkedList[BumpyBall]()
  private var dbgRenderer: DebugRenderer = _
  private var time = 0
  private var b1: BumpyBall = _
  private var b2: BumpyBall = _
  private var b3: BumpyBall = _
  private var b4: BumpyBall = _

  override def onInit(): Unit = {
    dbgRenderer = new DebugRenderer()
    setTitle("Collision demo for box2d, mui 2013")

    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)

    // A BumpyBall has redefined its collision method.
    b1 = new BumpyBall("ball 1", new Vector2(100f, 250f), 30)

    // Indicate that the ball should be informed for collisions
    b1.enableCollisionListener()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    if (b1 != null) b1.draw(g)
    if (b2 != null) b2.draw(g)
    if (b3 != null) b3.draw(g)
    if (b4 != null) b4.draw(g)

    // Draw all the manually added balls
    val it = otherBalls.iterator()
    while (it.hasNext) {
      it.next().draw(g)
    }

    // Add balls from time to time
    if (time == 100) {
      b2 = new BumpyBall("ball 2", new Vector2(105f, 300f), 40)
      b2.enableCollisionListener()
    }

    if (time == 150) {
      b3 = new BumpyBall("ball 3", new Vector2(120f, 300f), 20)
      b3.enableCollisionListener()
    }

    if (time == 200) {
      b4 = new BumpyBall("ball 4", new Vector2(130f, 310f), 30)
      b4.enableCollisionListener()
    }

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    g.drawSchoolLogoUpperRight()
    g.drawFPS()
    time += 1
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    if (button == Input.Buttons.LEFT) {
      val newBall = new BumpyBall("a ball", new Vector2(x.toFloat, y.toFloat), 50)
      newBall.enableCollisionListener()
      otherBalls.add(newBall)
    }
  }
}

object DemoCollisionListener {
  def main(args: Array[String]): Unit = {
    new DemoCollisionListener().launch()
  }
}
