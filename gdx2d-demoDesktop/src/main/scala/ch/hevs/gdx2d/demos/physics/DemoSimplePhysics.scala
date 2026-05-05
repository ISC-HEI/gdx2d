package ch.hevs.gdx2d.demos.physics

import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsCircle, PhysicsStaticBox}
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * Demonstrates the basic usage of the physics with a simple demo
 * Based on examples from box2d.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoSimplePhysics extends DesktopApplication {

  // Contains all the objects that will be simulated
  private var world: World = _
  private var ball: PhysicsCircle = _
  private var debugRenderer: DebugRenderer = _

  override def onInit(): Unit = {
    setTitle("Simple physics simulation, mui 2013")

    world = PhysicsWorld.getInstance()

    val w = getWindowWidth
    val h = getWindowHeight

    // Build the walls around the screen
    new PhysicsScreenBoundaries(w.toFloat, h.toFloat)

    // The slope on which the objects roll
    new PhysicsStaticBox("slope", new Vector2(w / 2f, h / 2f), (w / 3f * 2f), 16f, math.Pi.toFloat / 12.0f)

    // Build the falling object
    ball = new PhysicsCircle("none", new Vector2(w * 0.7f, h - 0.1f * h), 12f, 0.5f, 0.3f, 0.3f)
    ball.setBodyLinearVelocity(-1f, 1f)

    // Build the dominoes
    val nDominoes = 20
    val dominoSpace = (w - 60) / nDominoes

    for (i <- 0 until nDominoes) {
      new PhysicsBox(s"box$i", new Vector2((60 + i * dominoSpace).toFloat, 120f), 6f, 60f, 0.1f, 0.1f, 0.3f)
    }

    debugRenderer = new DebugRenderer()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    debugRenderer.render(world, g.getCamera.combined)
    PhysicsWorld.updatePhysics(Gdx.graphics.getRawDeltaTime)

    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }
}

object DemoSimplePhysics {
  def main(args: Array[String]): Unit = {
    new DemoSimplePhysics().launch()
  }
}
