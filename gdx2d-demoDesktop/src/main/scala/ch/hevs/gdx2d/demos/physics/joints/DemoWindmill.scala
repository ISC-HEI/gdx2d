package ch.hevs.gdx2d.demos.physics.joints

import java.util.{LinkedList, Random}

import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsStaticBox}
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align

/**
 * A demo on how to use [[PhysicsMotor]] and anchor points.
 *
 * Based on ex 5.9 from the Nature of code book.
 *
 * @author Thierry Hischier, hit 2014
 * @version 2.0
 */
class DemoWindmill extends DesktopApplication {

  private var debugRenderer: DebugRenderer = _
  private var physicMotor: PhysicsMotor = _
  private val particles = new LinkedList[CircleParticle]()
  private val random = new Random()

  private var mWidth: Float = 0f
  private var mHeight: Float = 0f
  private var time = 0
  private val GENERATION_RATE = 2
  private var generate = false

  override def onInit(): Unit = {
    setTitle("Windmill simulation, hit 2014")

    Logger.log("Press left mouse button to enable/disable the motor.")
    Logger.log("Press right mouse button to generate particles.")

    debugRenderer = new DebugRenderer()
    mWidth = getWindowWidth.toFloat
    mHeight = getWindowHeight.toFloat

    val staticBox = new PhysicsStaticBox("box1", new Vector2(mWidth / 2, mHeight / 2), 20f, 80f)
    val box1 = staticBox.getBody

    val movingBox = new PhysicsBox("box2", new Vector2(mWidth / 2, mHeight / 2), 240f, 20f)
    val box2 = movingBox.getBody

    physicMotor = new PhysicsMotor(box1, box2, box1.getWorldCenter)
    physicMotor.initializeMotor(2.0f, 200.0f, false)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    val iter = particles.iterator()
    while (iter.hasNext) {
      val p = iter.next()
      p.draw(g)
      val pos = p.getBodyPosition
      if (pos.y > mHeight || pos.y < 0 || pos.x > mWidth || pos.x < 0) {
        p.destroy()
        iter.remove()
      }
    }

    if (generate) {
      for (_ <- 0 until GENERATION_RATE) {
        val x = mWidth / 5f + random.nextFloat() * (mWidth - 2f * mWidth / 5f)
        val y = mHeight * 0.9f + random.nextInt(30)
        particles.add(new CircleParticle("a particle", new Vector2(x, y), 5))
      }
    } else {
      if (time % 25 == 0) {
        val x = random.nextInt(100)
        particles.add(new CircleParticle("a particle", new Vector2((getWindowWidth / 2 - 50 + x).toFloat, (getWindowHeight - 5).toFloat), 5))
      }
    }

    time += 1
    debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera.combined)
    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    g.drawString(mWidth - 5, 60f, "Left Mouse button: Motor ON/OFF", Align.right)
    g.drawString(mWidth - 5, 40f, "Right Mouse button: Generate particles", Align.right)
    g.drawString(mWidth - 5, 20f, s"Motor is ${if (physicMotor.isMotorEnabled) "ON" else "OFF"}", Align.right)

    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    if (button == Input.Buttons.LEFT) {
      physicMotor.enableMotor(!physicMotor.isMotorEnabled)
    } else if (button == Input.Buttons.RIGHT) {
      generate = true
    }
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    super.onRelease(x, y, button)
    generate = false
  }
}

object DemoWindmill {
  def main(args: Array[String]): Unit = {
    new DemoWindmill().launch()
  }
}
