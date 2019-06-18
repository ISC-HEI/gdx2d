package ch.hevs.gdx2d.demos.physics.motors

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType


import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

/**
 * A demo on how to use [PhysicsMotor].
 *
 *
 * Based on ex 5.9 from the Nature of code book The bodys are created without
 * the help of this library to show how two bodys for a joint motor can be
 * created.
 *
 *
 * The clock image source is from [here<a></a>.
 *
 * @author Thierry Hischier (hit)
 * @author Christopher Métrailler (mei)
 * @version 1.3
 * @see [The
 * nature of code example](http://natureofcode.com/book/chapter-5-physics-libraries/)
](http://dribbble.com/shots/408344-SBB-CFF-FFS) */
class DemoRotateMotor : PortableApplication(512, 256) {
    private val world = PhysicsWorld.getInstance()

    private var physicMotorSeconds: PhysicsMotor? = null
    private var physicMotorMinutes: PhysicsMotor? = null
    private var physicMotorHours: PhysicsMotor? = null

    private var clockBitmap: BitmapImage? = null
    private var secondBitmap: BitmapImage? = null
    private var minuteBitmap: BitmapImage? = null
    private var hourBitmap: BitmapImage? = null
    private var body: Body? = null
    private var body2: Body? = null
    private var body3: Body? = null
    private var body4: Body? = null

    private val CLOCK_CENTER = Vector2(136.0f, 128.0f)

    private var minuteDrawAngle: Float = 0.toFloat()

    override fun onInit() {
        setTitle("Simple rotate motor demo, hit, mei 2014")

        PhysicsScreenBoundaries(windowWidth.toFloat(), windowHeight.toFloat())

        clockBitmap = BitmapImage("images/clock.png")

        secondBitmap = BitmapImage("images/clock_second.png")
        minuteBitmap = BitmapImage("images/clock_minute.png")
        hourBitmap = BitmapImage("images/clock_hour.png")

        minuteDrawAngle = 0f

        // Get the actual time to calibrate the clock
        val df = SimpleDateFormat("HH:mm:ss")
        val time = df.format(Date())
        val timeList = time.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val hourAngle = java.lang.Float.parseFloat(timeList[0])
        val minuteAngle = java.lang.Float.parseFloat(timeList[1])
        val secondAngle = java.lang.Float.parseFloat(timeList[2])

        // The clock is the main static body
        val bd1 = BodyDef()
        bd1.type = BodyType.StaticBody
        bd1.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER))
        bd1.angle = 0f

        // Physical information about the static body
        body = world.createBody(bd1)
        val fd1 = FixtureDef()
        fd1.density = 20.0f
        fd1.restitution = 0.0f
        fd1.friction = 1.0f
        fd1.shape = CircleShape()
        fd1.shape.radius = PhysicsConstants.PIXEL_TO_METERS * 120

        body!!.createFixture(fd1)
        fd1.shape.dispose()

        // Create the dynamic Body which acts as needle controller
        val bd2 = BodyDef()
        bd2.type = BodyType.DynamicBody
        bd2.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER))
        bd2.angle = calcSecondAngle(secondAngle)

        // Physical information about the dynamic body for the second needle
        body2 = world.createBody(bd2)
        val fd2 = FixtureDef()
        fd2.density = 20.0f
        fd2.restitution = 0.0f
        fd2.friction = 2.0f
        fd2.shape = CircleShape()
        fd2.shape.radius = PhysicsConstants.PIXEL_TO_METERS * 60

        body2!!.createFixture(fd2)
        fd2.shape.dispose()

        val bd3 = BodyDef()
        bd3.type = BodyType.DynamicBody
        bd3.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER))
        bd3.angle = calcMinuteAngle(minuteAngle, secondAngle)

        // Physical information about the dynamic body for the minute needle
        body3 = world.createBody(bd3)
        val fd3 = FixtureDef()
        fd3.density = 20.0f
        fd3.restitution = 0.0f
        fd3.friction = 2.0f
        fd3.shape = CircleShape()
        fd3.shape.radius = PhysicsConstants.PIXEL_TO_METERS * 60

        body3!!.createFixture(fd3)
        fd3.shape.dispose()

        // Initialize the minute needle that it points exactly on the minutes marks
        minuteDrawAngle = (calcMinuteAngle(minuteAngle, 0f) * 180 / Math.PI).toFloat()

        val bd4 = BodyDef()
        bd4.type = BodyType.DynamicBody
        bd4.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER))
        bd4.angle = calcHourAngle(hourAngle, minuteAngle)

        // Physical information about the dynamic body for the hour needle
        body4 = world.createBody(bd4)
        val fd4 = FixtureDef()
        fd4.density = 20.0f
        fd4.restitution = 0.0f
        fd4.friction = 2.0f
        fd4.shape = CircleShape()
        fd4.shape.radius = PhysicsConstants.PIXEL_TO_METERS * 60

        body4!!.createFixture(fd4)
        fd4.shape.dispose()

        /**
         * Create 3 motors that will make the moving box move and rotate around
         * the anchor point (which is the center of the clock) for each needle
         */
        world.gravity = Vector2(0f, 0f)
        physicMotorSeconds = PhysicsMotor(body, body2, body!!.worldCenter)
        physicMotorMinutes = PhysicsMotor(body, body3, body!!.worldCenter)
        physicMotorHours = PhysicsMotor(body, body4, body!!.worldCenter)

        // Initialize the motor with a speed and torque
        physicMotorSeconds!!.initializeMotor((-(3 * Math.PI / 180.0f)).toFloat(), 360.0f, true)
        physicMotorMinutes!!.initializeMotor((-(0.0475 * Math.PI / 180.0f)).toFloat(), 360.0f, true)
        physicMotorHours!!.initializeMotor((-(0.003 * Math.PI / 180.0f)).toFloat(), 360.0f, true)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Control the second needle, change the position of the minute needle only when
        // a whole minute is passed
        val df = SimpleDateFormat("HH:mm:ss")
        val time = df.format(Date())
        val timeList = time.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        val seconds = java.lang.Float.parseFloat(timeList[2])

        if (seconds == 0f) {
            minuteDrawAngle = (body3!!.angle * 180 / Math.PI).toFloat()
        }

        // Get the size of the window
        val w = windowWidth
        val h = windowHeight

        // Clear the graphic to draw the new image
        g.clear()

        // Create a nice background from light gray to white without any images
        for (i in 0..h) {
            val c = 255 - i * 0.2f
            g.setColor(Color(c / 255, c / 255, c / 255, 1.0f))
            g.drawLine(0f, (h - i).toFloat(), w.toFloat(), (h - i).toFloat())
        }

        PhysicsWorld.updatePhysics()
        // Draw the clock surface as background and the clock hand in front with
        // the same angle as the motor has.
        g.drawPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, clockBitmap)

        // Draw the hour needle
        g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
                (body4!!.angle * 180 / Math.PI).toFloat(), 1.0f, hourBitmap)

        // Draw the minute needle
        // (the position of the image change every whole minute, the motor turns continuously)
        g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
                minuteDrawAngle, 1.0f, minuteBitmap)

        // Draw the second needle
        g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
                (body2!!.angle * 180 / Math.PI).toFloat(), 1.0f, secondBitmap)

        g.setColor(Color.BLACK)
        g.drawString((w - 200).toFloat(), (h - 10).toFloat(), "Famous clock from\r\n" + "the Swiss Railways.")

        g.drawString((w - 200).toFloat(), (h - 80).toFloat(), displayTime())

        g.drawSchoolLogo()
    }

    private fun displayTime(): String {
        // Return the current time as a String (hours:minutes:seconds)
        val df = SimpleDateFormat("HH:mm:ss")
        return "Current time: " + df.format(Date())
    }

    private fun calcSecondAngle(seconds: Float): Float {
        val result = ((360 - seconds * 6) * Math.PI / 180.0f).toFloat()
        return result
    }

    private fun calcMinuteAngle(minutes: Float, seconds: Float): Float {
        val result = ((360 - (minutes * 6 + seconds * 6 / 60)) * Math.PI / 180.0f).toFloat()
        return result
    }

    private fun calcHourAngle(hours: Float, minutes: Float): Float {
        var hours = hours
        if (hours > 12) {
            hours -= 12f
        }
        val result = ((360 - (hours * 30 + minutes * 30 / 60)) * Math.PI / 180.0f).toFloat()
        return result
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoRotateMotor()
        }
    }
}
