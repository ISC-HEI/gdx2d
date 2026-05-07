package ch.hevs.gdx2d.demos.physics

import java.util.LinkedList

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * A demo for physics, based on box2d.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoPhysicsBalls extends DesktopApplication {

  private val list = new LinkedList[PhysicsCircle]()
  private var world: World = _
  private var debugRenderer: DebugRenderer = _
  private var img: BitmapImage = _

  override def onInit(): Unit = {
    setTitle("Physics demo with box2d, mui 2013")
    Logger.log("Click to create a ball")

    world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0f, -10f))

    // The walls
    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)

    // Used to display debug information about the physics
    debugRenderer = new DebugRenderer()
    img = new BitmapImage("images/soccer.png")

    addBall(getWindowWidth / 2, getWindowHeight / 2)
  }

  /**
   * Adds a ball at a given location.
   *
   * @param x x location to put the ball to
   * @param y y location to put the ball to
   */
  def addBall(x: Int, y: Int): Unit = {
    // If there exists enough balls already, remove the oldest one
    if (list.size > 50) {
      val b = list.poll()
      b.destroy()
    }

    val size = (math.random() * 15.0).toFloat + 15f
    val b = new PhysicsCircle(null, new Vector2(x.toFloat, y.toFloat), size)

    // Add the ball to the list of existing balls
    list.add(b)
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    if (button == Input.Buttons.LEFT) addBall(x, y)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    // For every body in the world
    val it = list.iterator()
    while (it.hasNext) {
      val c = it.next()
      val radius = c.getBodyRadius
      val pos = c.getBodyPosition
      g.drawTransformedPicture(pos.x, pos.y, c.getBodyAngleDeg, radius, radius, img)
      c.setBodyAwake(true)
    }

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }
}

object DemoPhysicsBalls {
  def main(args: Array[String]): Unit = {
    new DemoPhysicsBalls().launch()
  }
}
