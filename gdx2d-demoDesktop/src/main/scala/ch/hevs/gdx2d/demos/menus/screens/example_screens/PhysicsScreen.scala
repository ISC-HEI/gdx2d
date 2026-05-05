package ch.hevs.gdx2d.demos.menus.screens.example_screens

import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsCircle, PhysicsStaticBox}
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * A screen to check that physics work in screens.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class PhysicsScreen extends RenderingScreen {

  private var world: World = _
  private var ball: PhysicsCircle = _
  private var debugRenderer: DebugRenderer = _

  override def onInit(): Unit = {
    val w = Gdx.graphics.getWidth
    val h = Gdx.graphics.getHeight

    world = PhysicsWorld.getInstance()

    new PhysicsScreenBoundaries(w.toFloat, h.toFloat)

    new PhysicsStaticBox("slope", new Vector2(w / 2f, h / 2f), (w / 3f * 2f), 16f, math.Pi.toFloat / 12.0f)

    ball = new PhysicsCircle("none", new Vector2(w * 0.7f, h - 0.1f * h), 12f, 0.5f, 0.3f, 0.3f)
    ball.setBodyLinearVelocity(-1f, 1f)

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
    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)
    g.drawStringCentered((g.getScreenHeight / 2).toFloat, "2 - Main game screen")

    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }

  override def dispose(): Unit = {
    super.dispose()
    PhysicsWorld.dispose()
  }
}
