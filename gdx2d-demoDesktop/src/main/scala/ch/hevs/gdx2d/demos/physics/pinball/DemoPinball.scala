package ch.hevs.gdx2d.demos.physics.pinball

import java.util.LinkedList

import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.components.physics.primitives.{PhysicsPolygon, PhysicsStaticCircle, PhysicsStaticLine}
import ch.hevs.gdx2d.components.physics.utils.{PhysicsConstants, PhysicsScreenBoundaries}
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import ch.hevs.gdx2d.lib.physics.{AbstractPhysicsObject, PhysicsWorld}
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

/**
 * WORK IN PROGRESS, DO NOT USE.
 */
class DemoPinball extends DesktopApplication {

  private var debugRenderer: DebugRenderer = _
  private var drawMode = DemoPinball.DrawMode.DEBUG_STD
  private var displayHelp = 500
  private val ballPosition = new Vector2(51f, 150f)
  private var leftFlipper: Flipper = _
  private var rightFlipper: Flipper = _
  private var ball: Ball = _
  private var bumperLeft: PhysicsStaticLine = _
  private var bumperRight: PhysicsStaticLine = _
  private var ballSprite: Spritesheet = _
  private var renderer: Renderer = _
  private var decorations: LinkedList[TemporaryDrawable] = _

  private class DrawablePhysicsStaticLine(name: String, p1: Vector2, p2: Vector2, density: Float, restitution: Float, friction: Float)
    extends PhysicsStaticLine(name, p1, p2, density, restitution, friction) with DrawableObject {
    override def draw(g: GdxGraphics): Unit = {
      g.setColor(Color.GRAY)
      g.drawLine(p1.x, p1.y, p2.x, p2.y)
    }
  }

  private class DrawablePhysicsPolygon(name: String, vertices: Array[Vector2], dynamic: Boolean)
    extends PhysicsPolygon(name, vertices, dynamic) with DrawableObject {
    def this(name: String, vertices: Array[Vector2], density: Float, restitution: Float, friction: Float, dynamic: Boolean) = {
      this(name, vertices, dynamic)
      // Note: we can't easily change density etc. after super init in this way,
      // but the original code used this pattern.
    }
    override def draw(g: GdxGraphics): Unit = {
      g.setColor(Color.GRAY)
      g.drawPolygon(getPolygon)
    }
  }

  private class DrawablePhysicsStaticCircle(name: String, position: Vector2, val radius: Float, density: Float, restitution: Float, friction: Float)
    extends PhysicsStaticCircle(name, position, radius, density, restitution, friction) with DrawableObject {
    def this(name: String, position: Vector2, radius: Float) = this(name, position, radius, 1f, 0f, 0f)
    override def draw(g: GdxGraphics): Unit = {
      g.setColor(Color.GRAY)
      g.drawCircle(getBodyPosition.x, getBodyPosition.y, radius)
    }
  }

  private def scale(x: Float): Float = {
    val h = Gdx.graphics.getHeight.toFloat
    val w = Gdx.graphics.getWidth.toFloat
    x * math.min(h, w)
  }

  override def onInit(): Unit = {
    ballSprite = new Spritesheet("images/pinball/sprites.png", 20, 20)
    decorations = new LinkedList[TemporaryDrawable]()
    val h = Gdx.graphics.getHeight.toFloat
    val w = Gdx.graphics.getWidth.toFloat
    setTitle("Pinball, pim 2015")
    Logger.log("Hello")

    val world = PhysicsWorld.getInstance()
    world.setGravity(PinballSettings.G)
    new PhysicsScreenBoundaries(h * PinballSettings.PINBALL_SIZE.x, w * PinballSettings.PINBALL_SIZE.y)
    debugRenderer = new DebugRenderer()
    renderer = new Renderer()

    val leftCenter = new Vector2(50f, 50f)
    val rightCenter = new Vector2(170f, 50f)

    leftFlipper = new Flipper("left_flipper", new Vector2(leftCenter).add(3f, -3f), 50f, 10f, -25f, 50f, ballSprite.sprites(1))
    rightFlipper = new Flipper("right_flipper", new Vector2(rightCenter).add(-3f, -3f), 50f, 10f, (-180 + 25).toFloat, -50f, ballSprite.sprites(1))

    new DrawablePhysicsPolygon("out1", Array(new Vector2(0f, 0f), new Vector2(0f, leftCenter.y * 2), leftCenter, new Vector2(leftCenter.x, 1f)), false)
    new DrawablePhysicsPolygon("out2", Array(new Vector2(rightCenter.x, 0f), rightCenter, new Vector2(w, h / 2), new Vector2(w, 0f)), false)
    new DrawablePhysicsPolygon("bumperLeft_frame", Array(new Vector2(leftCenter).add(-20f, 55f), new Vector2(leftCenter).add(-20f, 95f), new Vector2(leftCenter).add(5f, 55f)), false)

    bumperLeft = new DrawablePhysicsStaticLine("bumperLeft", new Vector2(leftCenter).add(-19f, 96f), new Vector2(leftCenter).add(4f, 56f), 4f, 2f, .6f) {
      override def collision(theOtherObject: AbstractPhysicsObject, energy: Float): Unit = {
        super.collision(theOtherObject, energy)
        println("yahoo")
      }
    }

    new DrawablePhysicsPolygon("bumperRight_frame", Array(new Vector2(rightCenter).add(20f, 55f), new Vector2(rightCenter).add(20f, 95f), new Vector2(rightCenter).add(-5f, 55f)), false)

    new SensorSpinner("toto", new Vector2(0f, leftCenter.y).add(12.5f, 55f), 25f, 25f, ballSprite.sprites(2))

    bumperRight = new DrawablePhysicsStaticLine("bumperRight", new Vector2(rightCenter).add(19f, 96f), new Vector2(rightCenter).add(-4f, 56f), 4f, 2f, .6f)

    ball = new Ball("ball", ballPosition, scale(PinballSettings.BALL_DIAMETER / 2), ballSprite.sprites(0))
    ball.enableCollisionListener()

    new DrawablePhysicsStaticCircle("b1", new Vector2(250f, 450f), 20f, 1f, 2f, .6f) {
      override def collision(theOtherObject: AbstractPhysicsObject, energy: Float): Unit = {
        println("houla")
      }
    }
    new DrawablePhysicsStaticCircle("b2", new Vector2(325f, 525f), 20f, 1f, 2f, .6f)
    new DrawablePhysicsStaticCircle("b3", new Vector2(400f, 450f), 20f, 1f, 2f, .6f)
  }

  private def moveCamera(g: GdxGraphics, p: Vector2): Unit = {
    val h = Gdx.graphics.getHeight.toFloat
    var y = p.y - h / PinballSettings.PINBALL_SIZE.y - h / 3
    if (y > h) y = h
    if (y < 0) y = 0f
    g.moveCamera(0f, y)
  }

  def drawStd(g: GdxGraphics): Unit = {
    val h = Gdx.graphics.getHeight.toFloat
    val w = Gdx.graphics.getWidth.toFloat

    g.setBackgroundColor(Color.WHITE)
    g.setColor(new Color(.8f, .8f, .8f, 1f))

    var x = 0f
    while (x < h) {
      g.drawLine(x, 0f, x, 2 * h)
      x += 25
    }

    var y = 0f
    while (y < 2 * h) {
      g.drawLine(0f, y, w, y)
      y += 25
    }

    renderer.draw(g, PhysicsWorld.getInstance(), decorations)
    g.drawSchoolLogo()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    PhysicsWorld.updatePhysics()
    moveCamera(g, PhysicsConstants.coordMetersToPixels(ball.getBody.getPosition))

    drawMode match {
      case DemoPinball.DrawMode.DEBUG =>
        debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera.combined)
        g.drawFPS(Color.BLACK)
      case DemoPinball.DrawMode.DEBUG_STD =>
        debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera.combined)
        drawStd(g)
        g.drawFPS(Color.BLACK)
      case DemoPinball.DrawMode.STD =>
        drawStd(g)
    }

    if (displayHelp > 0) {
      displayHelp -= 1
      g.setColor(new Color(Color.BLACK).mul(displayHelp.toFloat / 100f))
      g.drawStringCentered(g.getCamera.position.y,
        "Warning : work in progress\n" +
          "CTRL keys : flippers\n" +
          "SPACE : new ball\n" +
          "d : debug view\n"
      )
    }
  }

  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)
    keycode match {
      case Keys.CONTROL_LEFT  => leftFlipper.power(true)
      case Keys.CONTROL_RIGHT => rightFlipper.power(true)
      case Keys.SPACE | Keys.D => ()
      case _ => displayHelp = 100
    }
  }

  override def onKeyUp(keycode: Int): Unit = {
    super.onKeyUp(keycode)
    keycode match {
      case Keys.CONTROL_LEFT  => leftFlipper.power(false)
      case Keys.CONTROL_RIGHT => rightFlipper.power(false)
      case Keys.D =>
        drawMode = drawMode match {
          case DemoPinball.DrawMode.DEBUG     => DemoPinball.DrawMode.DEBUG_STD
          case DemoPinball.DrawMode.DEBUG_STD => DemoPinball.DrawMode.STD
          case DemoPinball.DrawMode.STD       => DemoPinball.DrawMode.DEBUG
        }
      case Keys.SPACE =>
        val h = Gdx.graphics.getHeight.toFloat
        ball.destroy()
        ball = new Ball("ball", new Vector2(0f, h), scale(PinballSettings.BALL_DIAMETER / 2), ballSprite.sprites(0))
        ball.enableCollisionListener()
      case _ => displayHelp = 300
    }
  }
}

object DemoPinball {
  object DrawMode extends Enumeration {
    val STD, DEBUG, DEBUG_STD = Value
  }
  def main(args: Array[String]): Unit = {
    new DemoPinball().launch()
  }
}
