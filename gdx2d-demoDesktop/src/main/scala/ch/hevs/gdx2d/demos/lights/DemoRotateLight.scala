package ch.hevs.gdx2d.demos.lights

import box2dLight.{PointLight, RayHandler}
import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.components.physics.primitives.PhysicsCircle
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * A simple demo for the gdx2light integration (lights and shadows).
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoRotateLight extends DesktopApplication {

  protected val radius = 0.3f
  protected var mWidth: Int = 0
  protected var mHeight: Int = 0
  protected var angle: Float = 0f
  protected var clicked = false
  protected var rotationCenter = new Vector2()

  private var world: World = _
  private var rayHandler: RayHandler = _
  private var p: PointLight = _

  private var hue = 0.3f
  private var direction = 0.001f

  private var circle1: PhysicsCircle = _
  private var circle2: PhysicsCircle = _
  private var circle3: PhysicsCircle = _

  override def onInit(): Unit = {
    setTitle("Rotate light demo, mui 2013")
    Gdx.app.log("[DemoLights]", "Left click to move the light source")

    world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0f, 0f))

    mWidth = getWindowWidth
    mHeight = getWindowHeight

    rayHandler = new RayHandler(world)

    p = new PointLight(rayHandler, 800, Color.LIGHT_GRAY, 8f,
      (mWidth / 2 - 30) * PhysicsConstants.PIXEL_TO_METERS,
      (mHeight / 2 + 120) * PhysicsConstants.PIXEL_TO_METERS)

    p.setDistance(8f)
    p.setSoft(true)
    p.setColor(ColorUtils.hsvToColor(hue, 0.8f, 1.0f))

    // Creates the objects that will cast shadows
    circle1 = new PhysicsCircle("circle", new Vector2((mWidth / 2).toFloat, (mHeight / 2).toFloat), 10f, 1.2f, 1f, 0.01f)
    circle2 = new PhysicsCircle("circle", new Vector2((mWidth / 2 - mWidth / 10).toFloat, (mHeight / 2).toFloat), 10f, 1.2f, 1f, 0.01f)
    circle3 = new PhysicsCircle("circle", new Vector2((mWidth / 2 + mWidth / 10).toFloat, (mHeight / 2).toFloat), 10f, 1.2f, 1f, 0.01f)

    val other = new OrthographicCamera()
    other.setToOrtho(false)
    other.combined.scl(PhysicsConstants.METERS_TO_PIXELS)
    rayHandler.setCombinedMatrix(other.combined)

    rotationCenter.set((mWidth / 2).toFloat, mHeight * 0.65f)
    rotationCenter.scl(PhysicsConstants.PIXEL_TO_METERS)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    // Render the blue objects
    var pos = circle1.getBodyPosition
    g.drawFilledCircle(pos.x, pos.y, 12f, Color.BLUE)
    pos = circle2.getBodyPosition
    g.drawFilledCircle(pos.x, pos.y, 12f, Color.BLUE)
    pos = circle3.getBodyPosition
    g.drawFilledCircle(pos.x, pos.y, 12f, Color.BLUE)

    angle += 0.05f

    // Change the light's color
    if (hue > 0.99f || hue <= 0.01f) {
      direction *= -1f
    }
    hue += direction
    p.setColor(ColorUtils.hsvToColor(hue, 0.8f, 1.0f))

    if (clicked)
      p.setPosition(rotationCenter.x, rotationCenter.y)
    else
      p.setPosition(
        (rotationCenter.x - radius + radius * Math.cos(angle.toDouble)).toFloat,
        (rotationCenter.y + radius * Math.sin(angle.toDouble)).toFloat)

    // Update the light rays
    g.beginCustomRendering()
    rayHandler.updateAndRender()
    g.endCustomRendering()

    // Update the physics
    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    g.drawSchoolLogo()
    g.drawFPS()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    if (button == Input.Buttons.LEFT) {
      rotationCenter.set(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS)
      clicked = true
    }
  }

  override def onDrag(x: Int, y: Int): Unit = {
    rotationCenter.set(x * PhysicsConstants.PIXEL_TO_METERS, y * PhysicsConstants.PIXEL_TO_METERS)
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    super.onRelease(x, y, button)
    if (clicked) {
      angle = 0f
      clicked = false
    }
  }
}

object DemoRotateLight {
  def main(args: Array[String]): Unit = {
    new DemoRotateLight().launch()
  }
}
