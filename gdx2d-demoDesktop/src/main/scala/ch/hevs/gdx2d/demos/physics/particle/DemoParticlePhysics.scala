package ch.hevs.gdx2d.demos.physics.particle

import java.util.Random

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.{Body, World}
import com.badlogic.gdx.utils.Array

/**
 * Demo for particle physics. There are no collisions in the physics and no
 * boundaries.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoParticlePhysics(w: Int, h: Int) extends DesktopApplication(w, h) {

  def this() = this(1000, 600)

  private val MAX_AGE = 35
  private var CREATION_RATE = 3
  private var dbgRenderer: DebugRenderer = _
  private var world: World = _
  private var mouseActive = false
  private var position = new Vector2()
  private val rnd = new Random()

  override def onInit(): Unit = {
    setTitle("Particle physics, mui 2013")
    dbgRenderer = new DebugRenderer()
    world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0f, -0.6f))
    Gdx.app.log("[DemoParticlePhysics]", "Click on screen to create particles")
    Gdx.app.log("[DemoParticlePhysics]", "a/s change the creation rate of particles")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    val bodies = new Array[Body]()
    world.getBodies(bodies)

    val it = bodies.iterator()
    while (it.hasNext) {
      val p = it.next()
      if (p.getUserData.isInstanceOf[Particle]) {
        val particle = p.getUserData.asInstanceOf[Particle]
        particle.step()
        particle.render(g)
        if (particle.shouldBeDestroyed) {
          particle.destroy()
        }
      }
    }

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)

    if (mouseActive) createParticles()

    g.drawSchoolLogo()
    g.drawFPS()
  }

  private def createParticles(): Unit = {
    for (_ <- 0 until CREATION_RATE) {
      val c = new Particle(position, 10, MAX_AGE + rnd.nextInt(MAX_AGE / 2))
      val force = new Vector2()
      force.x = rnd.nextFloat() * 0.00095f * (if (rnd.nextBoolean()) -1f else 1f)
      force.y = rnd.nextFloat() * 0.00095f * (if (rnd.nextBoolean()) -1f else 1f)
      c.applyBodyLinearImpulse(force, position, true)
    }
  }

  override def onDrag(x: Int, y: Int): Unit = {
    position.set(x.toFloat, y.toFloat)
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    mouseActive = true
    position.set(x.toFloat, y.toFloat)
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    position.set(x.toFloat, y.toFloat)
    mouseActive = false
  }

  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)
    if (keycode == Input.Keys.A) CREATION_RATE += 1
    if (keycode == Input.Keys.S) CREATION_RATE = if (CREATION_RATE > 1) CREATION_RATE - 1 else CREATION_RATE
    Gdx.app.log("[DemoParticlePhysics]", s"Creation rate is now $CREATION_RATE")
  }
}

object DemoParticlePhysics {
  def main(args: Array[String]): Unit = {
    new DemoParticlePhysics().launch()
  }
}
