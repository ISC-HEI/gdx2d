package ch.hevs.gdx2d.demos.physics.chains

import java.util.{LinkedList, Random}

import ch.hevs.gdx2d.components.colors.Palette
import ch.hevs.gdx2d.components.physics.PhysicsChain
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

/**
 * Demonstration of chained objects. Here the chain is the thing on which the
 * balls fall.
 *
 * Based on ex 5.3 from the Nature of code book.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoChainPhysics(wWidth: Int, wHeight: Int) extends DesktopApplication(wWidth, wHeight) {

  def this() = this(500, 500)

  private val balls = new LinkedList[PhysicsBall]()
  private val rnd = new Random()
  private var world: World = _
  private var chain: PhysicsChain = _

  private val GENERATION_RATE = 7

  private var mWidth: Float = 0f
  private var mHeight: Float = 0f
  private var generate = false

  override def onInit(): Unit = {
    setTitle("Physics objects in well demo, mui 2013")

    Gdx.app.log("[DemoChainPhysics]", "Left click to generate balls")
    Gdx.app.log("[DemoChainPhysics]", "Right click to generate random terrain")
    Gdx.app.log("[DemoChainPhysics]", "Middle click to generate Catmull-Rom terrain")
    Gdx.app.log("[DemoChainPhysics]", "'r' to modify rendering type")

    world = PhysicsWorld.getInstance()
    mWidth = getWindowWidth.toFloat
    mHeight = getWindowHeight.toFloat

    chain = new PhysicsChain(
      new Vector2(mWidth / 10f, mHeight * 0.33f),
      new Vector2(mWidth - mWidth / 10f, mHeight * 0.33f),
      8,
      PhysicsChain.chain_type.CATMUL)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.LIGHT_GRAY)
    chain.draw(g)

    val iter = balls.iterator()
    while (iter.hasNext) {
      val ball = iter.next()
      ball.draw(g)

      val p = ball.getBodyPosition
      if (p.y > mHeight || p.y < 0 || p.x > mWidth || p.x < 0) {
        ball.destroy()
        iter.remove()
      }
    }

    g.drawFPS()
    g.drawString(5f, 30f, s"#Obj: ${world.getBodyCount}")
    g.drawSchoolLogo()

    if (generate) {
      for (_ <- 0 until GENERATION_RATE) {
        val x = mWidth / 10f + rnd.nextFloat() * (mWidth - mWidth / 10f)
        val y = mHeight * 0.8f + rnd.nextInt(10)
        val b = new PhysicsBall(new Vector2(x, y),
          rnd.nextInt(10) + 6,
          Palette.pastel2(rnd.nextInt(Palette.pastel2.length)))
        balls.add(b)
      }
    }

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)
  }

  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)
    if (keycode == Input.Keys.R) {
      PhysicsBall.changeRendering()
    }
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    super.onClick(x, y, button)
    if (button == Input.Buttons.LEFT) generate = true
    if (button == Input.Buttons.MIDDLE) chain.catmull_chain(5)
    if (button == Input.Buttons.RIGHT) chain.random_chain(15)
  }

  override def onRelease(x: Int, y: Int, button: Int): Unit = {
    super.onRelease(x, y, button)
    generate = false
  }
}

object DemoChainPhysics {
  def main(args: Array[String]): Unit = {
    new DemoChainPhysics(1000, 600).launch()
  }
}
