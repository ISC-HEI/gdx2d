package ch.hevs.gdx2d.demos.physics.joints

import java.util.{LinkedList, Random}

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.colors.Palette
import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsStaticBox}
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Align

/**
 * A box that will be used as a rotor for the demo.
 */
class Rotor(name: String, position: Vector2, private val w: Float, private val h: Float)
  extends PhysicsBox(name, position, w, h) with DrawableObject {

  override def draw(g: GdxGraphics): Unit = {
    val pos = getBodyPosition
    val angle = getBodyAngleDeg
    g.drawFilledRectangle(pos.x, pos.y, w, h, angle, Palette.pastel1(1))
    g.drawTransformedPicture(pos.x, pos.y, angle, 0.2f, Rotor.screw)
  }
}

object Rotor {
  lazy val screw = new BitmapImage("images/screw.png")
}

/**
 * A demo on how to use [[PhysicsMotor]] with anchor points.
 *
 * @author Pierre-André Mudry, mui
 * @version 2.0
 */
class DemoMixer extends DesktopApplication {

  private val N_PARTICLES = 100
  private var dbgRenderer: DebugRenderer = _
  private var box1: Body = _
  private var box2: Body = _
  private var physicMotor: PhysicsMotor = _
  private val particles = new LinkedList[CircleParticle]()
  private val rnd = new Random()
  private var mWidth: Float = 0f
  private var mHeight: Float = 0f
  private var rotor: Rotor = _

  override def onInit(): Unit = {
    setTitle("Particle mixer, mui 2014")
    Logger.log("Press left mouse button to enable/disable the motor.")

    dbgRenderer = new DebugRenderer()
    mWidth = getWindowWidth.toFloat
    mHeight = getWindowHeight.toFloat

    new PhysicsScreenBoundaries(mWidth, mHeight)

    val stator = new PhysicsStaticBox("stator", new Vector2(mWidth / 2f, mHeight / 2f), 5f, 5f)
    box1 = stator.getBody

    rotor = new Rotor("rotor", new Vector2(mWidth / 2f, mHeight / 2f), mWidth * 0.85f, mHeight * 0.02f)
    box2 = rotor.getBody

    physicMotor = new PhysicsMotor(box1, box2, box1.getWorldCenter)
    physicMotor.initializeMotor(1.0f, 8000.0f, false)

    createParticles()
  }

  private def createParticles(): Unit = {
    for (_ <- 0 until N_PARTICLES) {
      val x = mWidth / 5f + rnd.nextFloat() * (mWidth - 2f * mWidth / 5f)
      val y = rnd.nextFloat() * mHeight
      val c = if (rnd.nextBoolean()) Color.DARK_GRAY else Color.LIGHT_GRAY
      val p = new CircleParticle(new Vector2(x, y), 10 + rnd.nextInt(5), c, 0.002f, 1f)
      particles.add(p)
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    val it = particles.iterator()
    while (it.hasNext) it.next().draw(g)

    rotor.draw(g)

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    g.drawString(5f, mHeight - 20, "Left Mouse button: Motor ON/OFF", Align.left)
    g.drawString(5f, mHeight - 40, s"Motor is ${if (physicMotor.isMotorEnabled) "ON" else "OFF"}", Align.left)

    g.drawSchoolLogoUpperRight()
    g.drawFPS(Color.CYAN)
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    if (button == Input.Buttons.LEFT) {
      physicMotor.enableMotor(!physicMotor.isMotorEnabled)
    }
  }
}

object DemoMixer {
  def main(args: Array[String]): Unit = {
    new DemoMixer().launch()
  }
}
