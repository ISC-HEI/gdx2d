package ch.hevs.gdx2d.demos.lights

import java.util.Random

import scala.collection.mutable.ArrayBuffer

import box2dLight.{ConeLight, PointLight, RayHandler}
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.{PhysicsConstants, PhysicsScreenBoundaries}
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * Demonstrates the usage of shadows and lights in GDX2D.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoLight extends DesktopApplication {

  // Physics-related attributes
  private var rayHandler: RayHandler = _
  private var world: World = _
  private var debugRenderer: DebugRenderer = _
  private val list = new ArrayBuffer[PhysicsCircle]()

  // Light related attributes
  private var p: PointLight = _
  private var c1: ConeLight = _
  private var c2: ConeLight = _
  private var mWidth: Int = 0
  private var mHeight: Int = 0
  private var firstRun = true

  override def onInit(): Unit = {
    mWidth = getWindowWidth
    mHeight = getWindowHeight
    setTitle("Shadows and lights, mui 2013")

    Gdx.app.log("[DemoLights]", "Left click to create a new light")
    Gdx.app.log("[DemoLights]", "Right click disables normal light")

    world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0f, 0f))

    // The light manager
    rayHandler = new RayHandler(world)

    // This is the light controlled by the mouse click and drag
    p = new PointLight(rayHandler, 200, Color.YELLOW, 10f,
      (mWidth / 2 - 50).toFloat,
      (mHeight / 2 + 150).toFloat)

    p.setDistance(10f)
    p.setColor(new Color(0.9f, 0f, 0.9f, 0.9f))
    p.setActive(false)
    p.setSoft(true)

    // The two light cones that are always present
    c1 = new ConeLight(rayHandler, 300, new Color(1f, 1f, 1f, 0.92f), 14f,
      0.2f * mWidth * PhysicsConstants.PIXEL_TO_METERS,
      0.9f * mHeight * PhysicsConstants.PIXEL_TO_METERS,
      270f, 40f)
    c2 = new ConeLight(rayHandler, 300, new Color(0.1f, 0.1f, 1f, 0.92f), 14f,
      0.8f * mWidth * PhysicsConstants.PIXEL_TO_METERS,
      0.9f * mHeight * PhysicsConstants.PIXEL_TO_METERS,
      270f, 40f)

    rayHandler.setCulling(true)
    rayHandler.setShadows(true)
    rayHandler.setBlur(true)
    rayHandler.setAmbientLight(0.4f)

    new PhysicsScreenBoundaries(mWidth.toFloat, mHeight.toFloat)
    createRandomCircles(10)

    debugRenderer = new DebugRenderer()
  }

  /**
   * Creates n physics objects that will then cast shadows.
   */
  protected def createRandomCircles(n: Int): Unit = {
    val r = new Random()
    for (_ <- 0 until n) {
      val position = new Vector2((mWidth * Math.random()).toFloat, (mHeight * Math.random()).toFloat)
      val circle = new PhysicsCircle("circle", position, 10f, 1.2f, 1f, 0.01f)
      circle.setBodyLinearVelocity(r.nextFloat() * 3, r.nextFloat() * 3)
      list += circle
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (firstRun) {
      val other = new OrthographicCamera()
      other.setToOrtho(false)
      val cmb = other.combined.scl(PhysicsConstants.METERS_TO_PIXELS)
      rayHandler.setCombinedMatrix(cmb)
      firstRun = false
    }

    g.clear()

    // Render the blue spheres
    for (b <- list) {
      val pos = b.getBodyPosition
      g.drawFilledCircle(pos.x, pos.y, 12f, Color.MAGENTA)
    }

    // Render the lights
    g.beginCustomRendering()
    rayHandler.updateAndRender()
    g.endCustomRendering()

    // Update the physics
    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    g.drawSchoolLogo()
    g.drawFPS()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    if (button == Input.Buttons.RIGHT) {
      c1.setActive(!c1.isActive)
      c2.setActive(!c2.isActive)
    }

    if (button == Input.Buttons.LEFT) {
      p.setActive(true)
      p.setPosition(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS)
    }
  }

  override def onDrag(x: Int, y: Int): Unit = {
    p.setPosition(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS)
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    p.setActive(false)
  }
}

object DemoLight {
  def main(args: Array[String]): Unit = {
    new DemoLight().launch()
  }
}
