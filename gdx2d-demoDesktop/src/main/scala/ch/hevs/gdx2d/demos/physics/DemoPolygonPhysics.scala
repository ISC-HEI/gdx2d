package ch.hevs.gdx2d.demos.physics

import ch.hevs.gdx2d.components.graphics.GeomUtils
import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * Demonstrates how to use general polygons as physics objects.
 *
 * @author Pierre-André Mudry
 * @version 2.0
 */
class DemoPolygonPhysics extends DesktopApplication {

  private var p1: PhysicsPolygon = _
  private var p2: PhysicsPolygon = _
  private var debugRenderer: DebugRenderer = _

  override def onInit(): Unit = {
    // A triangle
    val obj1 = Array(new Vector2(100f, 100f), new Vector2(200f, 100f), new Vector2(150f, 200f))

    // A special polygon
    val obj2 = Array(new Vector2(0f, 0f), new Vector2(4f, 0f), new Vector2(5f, 3f), new Vector2(2f, 8f), new Vector2(-1f, 3f))

    GeomUtils.translate(obj1, new Vector2(250f, 100f))
    GeomUtils.rotate(obj1, 12f)

    GeomUtils.scale(obj2, 20f)
    GeomUtils.rotate(obj2, 14f)
    GeomUtils.translate(obj2, new Vector2(100f, 100f))

    p1 = new PhysicsPolygon("poly1", obj1, true)
    p2 = new PhysicsPolygon("poly2", obj2, true)

    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)

    debugRenderer = new DebugRenderer()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    PhysicsWorld.updatePhysics(Gdx.graphics.getRawDeltaTime)
    debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera.combined)

    g.drawFilledPolygon(p1.getPolygon, Color.YELLOW)
    g.drawFilledPolygon(p2.getPolygon, Color.RED)

    g.drawSchoolLogo()
    g.drawFPS()
  }
}

object DemoPolygonPhysics {
  def main(args: Array[String]): Unit = {
    new DemoPolygonPhysics().launch()
  }
}
