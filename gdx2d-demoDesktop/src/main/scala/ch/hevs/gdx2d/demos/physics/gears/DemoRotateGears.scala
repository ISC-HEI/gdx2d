package ch.hevs.gdx2d.demos.physics.gears

import java.text.SimpleDateFormat
import java.util.Date

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.primitives.{PhysicsBox, PhysicsStaticBox}
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.GearJointDef

/**
 * A demo on how to use [[PhysicsMotor]] and GearJointDef.
 *
 * @author Marc Pignat (pim)
 * @version 2.0
 */
class DemoRotateGears extends DesktopApplication(512, 256) {

  private val CLOCK_CENTER = new Vector2(136.0f, 128.0f)
  private val MOTOR_SPEED_SECOND = (-math.Pi / 58.5).toFloat
  private val MOTOR_SPEED_MINUTE = (-math.Pi / 60.0).toFloat

  private var bitmapClock: BitmapImage = _
  private var bitmapSecond: BitmapImage = _
  private var bitmapMinute: BitmapImage = _
  private var bitmapHour: BitmapImage = _

  private var handSecond: PhysicsBox = _
  private var handMinute: PhysicsBox = _
  private var handHour: PhysicsBox = _

  private var motorSeconds: PhysicsMotor = _
  private var motorMinutes: PhysicsMotor = _

  private var debugRenderer: DebugRenderer = _
  private var debugRendering = false

  class TimeFloat {
    var hour: Float = 0f
    var second: Float = 0f
    var minute: Float = 0f

    def secondAngle: Float = (-(2.0 * math.Pi * second)).toFloat / 60f
    def minuteAngle: Float = (-(2.0 * math.Pi * minute)).toFloat / 60f
    def hourAngle: Float = (-(2.0 * math.Pi * (hour % 12 * 60 + minute))).toFloat / (12 * 60)

    val t = new SimpleDateFormat("HH:mm:ss").format(new Date()).split(":")
    hour = t(0).toFloat
    minute = t(1).toFloat
    second = t(2).toFloat
  }

  override def onInit(): Unit = {
    val time = new TimeFloat()
    setTitle("Simple rotate gears demo, pim 2015")
    debugRenderer = new DebugRenderer()

    bitmapClock = new BitmapImage("images/clock.png")
    bitmapSecond = new BitmapImage("images/clock_second.png")
    bitmapMinute = new BitmapImage("images/clock_minute.png")
    bitmapHour = new BitmapImage("images/clock_hour.png")

    val frame = new PhysicsStaticBox("frame", CLOCK_CENTER, 10f, 10f)

    handSecond = new PhysicsBox("seconds", CLOCK_CENTER, 10f, 50f, time.secondAngle)
    handMinute = new PhysicsBox("minutes", CLOCK_CENTER, 10f, 40f, time.minuteAngle)
    handHour = new PhysicsBox("hours", CLOCK_CENTER, 10f, 30f, time.hourAngle)

    handSecond.setCollisionGroup(-2)
    handMinute.setCollisionGroup(-2)
    handHour.setCollisionGroup(-2)

    val world = PhysicsWorld.getInstance()
    motorSeconds = new PhysicsMotor(frame.getBody, handSecond.getBody, frame.getBody.getWorldCenter)
    motorMinutes = new PhysicsMotor(frame.getBody, handMinute.getBody, frame.getBody.getWorldCenter)
    val motorM2H = new PhysicsMotor(frame.getBody, handHour.getBody, frame.getBody.getWorldCenter)

    val gearM2H = new GearJointDef()
    gearM2H.bodyA = handMinute.getBody
    gearM2H.bodyB = handHour.getBody
    gearM2H.joint1 = motorMinutes.getJoint
    gearM2H.joint2 = motorM2H.getJoint
    gearM2H.ratio = -60f
    world.createJoint(gearM2H)

    motorSeconds.initializeMotor(MOTOR_SPEED_SECOND, 1.0f, true)
    motorMinutes.initializeMotor(0.0f, 1.0f, true)

    println("click to switch debug/rendering mode")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    PhysicsWorld.updatePhysics()

    val syncSignal = new TimeFloat().second % 60.0 == 0.0

    if (syncSignal) {
      motorSeconds.setMotorSpeed(MOTOR_SPEED_SECOND)
      motorMinutes.setMotorSpeed(MOTOR_SPEED_MINUTE)
    } else {
      val angle = -handSecond.getBody.getAngle % (2 * math.Pi)
      if (angle > 2.0 * math.Pi * 0.995 && motorSeconds.getSpeed != 0f) {
        motorSeconds.setMotorSpeed(0.0f)
      }
      motorMinutes.setMotorSpeed(0.0f)
    }

    val w = getWindowWidth
    val h = getWindowHeight

    g.clear()

    if (debugRendering) {
      debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera.combined)
      g.setColor(Color.WHITE)
    } else {
      for (i <- 0 to h) {
        val c = 255 - i * 0.3f
        g.setColor(new Color(c / 255f, c / 255f, c / 255f, 1.0f))
        g.drawLine(0f, (h - i).toFloat, w.toFloat, (h - i).toFloat)
      }

      g.drawPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, bitmapClock)
      g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, math.toDegrees(handHour.getBody.getAngle.toDouble).toFloat, 1.0f, bitmapHour)
      g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, math.toDegrees(handMinute.getBody.getAngle.toDouble).toFloat, 1.0f, bitmapMinute)
      g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, math.toDegrees(handSecond.getBody.getAngle.toDouble).toFloat, 1.0f, bitmapSecond)
      g.setColor(Color.BLACK)
    }

    g.drawString(w - 200f, h - 10f, "Famous clock from\r\nthe Swiss Railways.")
    g.drawString(w - 200f, h - 80f, displayTime())
    g.drawSchoolLogo()
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    debugRendering = !debugRendering
  }

  private def displayTime(): String = {
    val df = new SimpleDateFormat("HH:mm:ss")
    "Current time: " + df.format(new Date())
  }
}

object DemoRotateGears {
  def main(args: Array[String]): Unit = {
    new DemoRotateGears().launch()
  }
}
