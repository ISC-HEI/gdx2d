package ch.hevs.gdx2d.demos.physics.joints

import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsStaticBox}
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef

/**
 * A demo on how to build chains with rope joints with box2d.
 *
 * @author Pierre-André Mudry, mui
 * @author Thierry Hischier, hit
 * @version 2.0
 */
class DemoRopeJoint extends DesktopApplication {

  private var debugRenderer: DebugRenderer = _

  override def onInit(): Unit = {
    setTitle("Rope joints simulation, hit/mui 2014")
    val w = getWindowWidth
    val h = getWindowHeight

    debugRenderer = new DebugRenderer()
    val world = PhysicsWorld.getInstance()

    val p = new PhysicsStaticBox("", new Vector2(w / 2.0f, h / 1.4f), 20f, 20f)
    var prevBody = p.getBody

    val nSegments = 10
    val segmentLength = 20
    val spaceBetweenSegments = 10

    for (i <- 0 until nSegments) {
      val box = new PhysicsBox("", new Vector2((w / 2 + i * (segmentLength + spaceBetweenSegments)).toFloat, h / 1.4f),
        segmentLength.toFloat, 4f)

      val anchorA = prevBody.getLocalCenter
      val anchorB = box.getBodyLocalCenter.add((-(segmentLength + spaceBetweenSegments)).toFloat, 0f)

      val revoluteJointDefRope = new RevoluteJointDef()
      revoluteJointDefRope.bodyA = prevBody
      revoluteJointDefRope.bodyB = box.getBody
      revoluteJointDefRope.collideConnected = false
      revoluteJointDefRope.localAnchorA.set(PhysicsConstants.coordPixelsToMeters(anchorA))
      revoluteJointDefRope.localAnchorB.set(PhysicsConstants.coordPixelsToMeters(anchorB))
      world.createJoint(revoluteJointDefRope)

      prevBody = box.getBody
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)
    debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera.combined)
    g.drawSchoolLogoUpperRight()
    g.drawFPS()
  }
}

object DemoRopeJoint {
  def main(args: Array[String]): Unit = {
    new DemoRopeJoint().launch()
  }
}
