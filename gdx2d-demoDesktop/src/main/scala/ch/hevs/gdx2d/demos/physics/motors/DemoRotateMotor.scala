package ch.hevs.gdx2d.demos.physics.motors

import java.text.SimpleDateFormat
import java.util.Date

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.utils.{PhysicsConstants, PhysicsScreenBoundaries}
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType

/**
 * A demo on how to use [[PhysicsMotor]].
 *
 * @author Thierry Hischier (hit)
 * @author Christopher Métrailler (mei)
 * @version 2.0
 */
class DemoRotateMotor extends DesktopApplication(512, 256) {

  private val CLOCK_CENTER = new Vector2(136.0f, 128.0f)

  private var clockBitmap: BitmapImage = _
  private var secondBitmap: BitmapImage = _
  private var minuteBitmap: BitmapImage = _
  private var hourBitmap: BitmapImage = _

  private var body: Body = _
  private var body2: Body = _
  private var body3: Body = _
  private var body4: Body = _

  private var minuteDrawAngle: Float = 0f

  override def onInit(): Unit = {
    setTitle("Simple rotate motor demo, hit, mei 2014")

    new PhysicsScreenBoundaries(getWindowWidth.toFloat, getWindowHeight.toFloat)

    clockBitmap = new BitmapImage("images/clock.png")
    secondBitmap = new BitmapImage("images/clock_second.png")
    minuteBitmap = new BitmapImage("images/clock_minute.png")
    hourBitmap = new BitmapImage("images/clock_hour.png")

    val df = new SimpleDateFormat("HH:mm:ss")
    val timeParts = df.format(new Date()).split(":")
    val hourVal = timeParts(0).toFloat
    val minuteVal = timeParts(1).toFloat
    val secondVal = timeParts(2).toFloat

    val world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0f, 0f))

    // The clock is the main static body
    val bd1 = new BodyDef()
    bd1.`type` = BodyType.StaticBody
    bd1.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER))
    body = world.createBody(bd1)

    val shape = new CircleShape()
    shape.setRadius(PhysicsConstants.PIXEL_TO_METERS * 120)
    val fd1 = new FixtureDef()
    fd1.density = 20.0f
    fd1.shape = shape
    body.createFixture(fd1)
    shape.dispose()

    // Second needle body
    val bd2 = new BodyDef()
    bd2.`type` = BodyType.DynamicBody
    bd2.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER))
    bd2.angle = calcSecondAngle(secondVal)
    body2 = world.createBody(bd2)

    val shape2 = new CircleShape()
    shape2.setRadius(PhysicsConstants.PIXEL_TO_METERS * 60)
    val fd2 = new FixtureDef()
    fd2.density = 20.0f
    fd2.shape = shape2
    body2.createFixture(fd2)
    shape2.dispose()

    // Minute needle body
    val bd3 = new BodyDef()
    bd3.`type` = BodyType.DynamicBody
    bd3.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER))
    bd3.angle = calcMinuteAngle(minuteVal, secondVal)
    body3 = world.createBody(bd3)
    body3.createFixture(fd2) // same fixture def as second needle is fine

    minuteDrawAngle = (calcMinuteAngle(minuteVal, 0f) * 180 / math.Pi).toFloat

    // Hour needle body
    val bd4 = new BodyDef()
    bd4.`type` = BodyType.DynamicBody
    bd4.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER))
    bd4.angle = calcHourAngle(hourVal, minuteVal)
    body4 = world.createBody(bd4)
    body4.createFixture(fd2)

    val motorSeconds = new PhysicsMotor(body, body2, body.getWorldCenter)
    val motorMinutes = new PhysicsMotor(body, body3, body.getWorldCenter)
    val motorHours = new PhysicsMotor(body, body4, body.getWorldCenter)

    motorSeconds.initializeMotor((-(3 * math.Pi / 180.0f)).toFloat, 360.0f, true)
    motorMinutes.initializeMotor((-(0.0475 * math.Pi / 180.0f)).toFloat, 360.0f, true)
    motorHours.initializeMotor((-(0.003 * math.Pi / 180.0f)).toFloat, 360.0f, true)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    val df = new SimpleDateFormat("HH:mm:ss")
    val timeParts = df.format(new Date()).split(":")
    val seconds = timeParts(2).toFloat

    if (seconds == 0f) {
      minuteDrawAngle = (body3.getAngle * 180 / math.Pi).toFloat
    }

    val w = getWindowWidth
    val h = getWindowHeight

    g.clear()

    // Background gradient
    for (i <- 0 to h) {
      val c = 255 - i * 0.2f
      g.setColor(new Color(c / 255f, c / 255f, c / 255f, 1.0f))
      g.drawLine(0f, (h - i).toFloat, w.toFloat, (h - i).toFloat)
    }

    PhysicsWorld.updatePhysics()

    g.drawPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, clockBitmap)
    g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, (body4.getAngle * 180 / math.Pi).toFloat, 1.0f, hourBitmap)
    g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, minuteDrawAngle, 1.0f, minuteBitmap)
    g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, (body2.getAngle * 180 / math.Pi).toFloat, 1.0f, secondBitmap)

    g.setColor(Color.BLACK)
    g.drawString(w - 200f, h - 10f, "Famous clock from\r\nthe Swiss Railways.")
    g.drawString(w - 200f, h - 80f, displayTime())
    g.drawSchoolLogo()
  }

  private def displayTime(): String = {
    val df = new SimpleDateFormat("HH:mm:ss")
    "Current time: " + df.format(new Date())
  }

  private def calcSecondAngle(seconds: Float): Float =
    ((360 - seconds * 6) * math.Pi / 180.0f).toFloat

  private def calcMinuteAngle(minutes: Float, seconds: Float): Float =
    ((360 - (minutes * 6 + seconds * 6 / 60)) * math.Pi / 180.0f).toFloat

  private def calcHourAngle(hours: Float, minutes: Float): Float = {
    val h = if (hours > 12) hours - 12f else hours
    ((360 - (h * 30 + minutes * 30 / 60)) * math.Pi / 180.0f).toFloat
  }
}

object DemoRotateMotor {
  def main(args: Array[String]): Unit = {
    new DemoRotateMotor().launch()
  }
}
