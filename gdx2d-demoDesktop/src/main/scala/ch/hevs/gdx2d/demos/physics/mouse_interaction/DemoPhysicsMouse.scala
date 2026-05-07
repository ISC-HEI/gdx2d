package ch.hevs.gdx2d.demos.physics.mouse_interaction

import java.util.Random

import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsCircle, PhysicsStaticBox}
import ch.hevs.gdx2d.components.physics.utils.{PhysicsConstants, PhysicsScreenBoundaries}
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.joints.{MouseJoint, MouseJointDef}

/**
 * A demo on how to use the mouse to move objects with `Box2D`.
 *
 * @author Pierre-André Mudry, mui 2013
 * @version 2.0
 */
class DemoPhysicsMouse(wWidth: Int, wHeight: Int) extends DesktopApplication(wWidth, wHeight) {

  def this() = this(800, 500)

  protected var hitBody: Body = _
  protected var groundBody: Body = _
  protected var mouseJoint: MouseJoint = _

  private var debugRenderer: DebugRenderer = _
  private val testPoint = new Vector2()
  private val target = new Vector2()

  private val callback = new QueryCallback {
    override def reportFixture(fixture: Fixture): Boolean = {
      if (fixture.testPoint(testPoint.x, testPoint.y)) {
        hitBody = fixture.getBody
        false
      } else {
        true
      }
    }
  }

  override def onInit(): Unit = {
    setTitle("Mouse interactions in box2d, mui 2013")

    val world = PhysicsWorld.getInstance()
    groundBody = world.createBody(new BodyDef())

    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)
    new PhysicsStaticBox("wall in the middle", new Vector2(getWindowWidth / 2f, 50f), 20f, 100f)

    val r = new Random()
    for (_ <- 0 until 10) {
      new PhysicsBox("box", new Vector2((100 + r.nextInt(100)).toFloat, (200 + r.nextInt(100)).toFloat),
        16f, (r.nextInt(80) + 40).toFloat, 1000f, 0.2f, 0.2f)
    }

    new PhysicsCircle("ball", new Vector2(100f, 500f), 20f)

    debugRenderer = new DebugRenderer()
    debugRenderer.setDrawJoints(true)
    debugRenderer.setDrawContacts(true)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera.combined)
    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    g.drawFPS()
    g.drawSchoolLogoUpperRight()
  }

  override def onDrag(x: Int, y: Int): Unit = {
    if (mouseJoint != null) {
      mouseJoint.setTarget(target.set(x.toFloat, y.toFloat).scl(PhysicsConstants.PIXEL_TO_METERS))
    }
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    if (mouseJoint != null) {
      PhysicsWorld.getInstance().destroyJoint(mouseJoint)
      mouseJoint = null
    }
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    testPoint.set(x.toFloat, y.toFloat).scl(PhysicsConstants.PIXEL_TO_METERS)
    hitBody = null
    PhysicsWorld.getInstance().QueryAABB(callback, testPoint.x - 5, testPoint.y - 5, testPoint.x + 5, testPoint.y + 5)

    if (hitBody == null || hitBody.getType == BodyType.KinematicBody) return

    val defJoint = new MouseJointDef()
    defJoint.bodyA = groundBody
    defJoint.bodyB = hitBody
    defJoint.collideConnected = true
    defJoint.dampingRatio = 0.8f
    defJoint.target.set(testPoint.x, testPoint.y)
    defJoint.maxForce = 100.0f * hitBody.getMass

    mouseJoint = PhysicsWorld.getInstance().createJoint(defJoint).asInstanceOf[MouseJoint]
    hitBody.setAwake(true)
  }

  override def onDispose(): Unit = {
    super.onDispose()
    debugRenderer.dispose()
  }
}

object DemoPhysicsMouse {
  def main(args: Array[String]): Unit = {
    new DemoPhysicsMouse().launch()
  }
}
