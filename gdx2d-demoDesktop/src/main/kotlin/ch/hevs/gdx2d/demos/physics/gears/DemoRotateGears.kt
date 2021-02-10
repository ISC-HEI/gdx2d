/**
 * A demo on how to use [PhysicsMotor] and GearJointDef.
 *
 *
 * Based on DemoRotateMotor, simulate the famous Swiss railway clock (@see
 * http://en.wikipedia.org/wiki/Swiss_railway_clock). The second hand runs too
 * fast then wait for the synchronization at zero. The minute hand goes by step,
 * when the synchronisation signal comes.
 *
 * The synchronization signal is emulated by waiting the system time's seconds
 * equals zero.
 *
 * This software uses 2 motors, one for the second hand and another for the
 * minute hand. The hour hand is driven by a gear, connected to the minute
 * motor.
 *
 *
 * The clock image source is from [here<a></a>.
 *
 * @author Marc Pignat (pim)
](http://dribbble.com/shots/408344-SBB-CFF-FFS) */

package ch.hevs.gdx2d.demos.physics.gears

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.physics.PhysicsMotor
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.physics.box2d.joints.GearJointDef

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class DemoRotateGears : PortableApplication(512, 256) {

    private val world = PhysicsWorld.getInstance()

    /* Bitmaps */
    private lateinit var bitmapClock: BitmapImage
    private lateinit var bitmapSecond: BitmapImage
    private lateinit var bitmapMinute: BitmapImage
    private lateinit var bitmapHour: BitmapImage

    /* Hands */
    private lateinit var hand_second: PhysicsBox
    private lateinit var hand_minute: PhysicsBox
    private lateinit var hand_hour: PhysicsBox

    /* Motors */
    lateinit var motor_seconds: PhysicsMotor
    lateinit var motor_minutes: PhysicsMotor

    private val CLOCK_CENTER = Vector2(136.0f, 128.0f)

    /* Hand speed */
    private val MOTOR_SPEED_SECOND = (-Math.PI / 58.5).toFloat()
    private val MOTOR_SPEED_MINUTE = (-Math.PI / 60).toFloat()

    private var debugRenderer: DebugRenderer? = null
    private var debug_rendering = false

    /**
     * A simple class for reading time
     *
     */
    internal inner class TimeFloat {
        var hour: Float = 0.toFloat()
        var second: Float = 0.toFloat()
        var minute: Float = 0.toFloat()

        val secondAngle: Float
            get() = (-(2.0 * Math.PI * second.toDouble())).toFloat() / 60

        val minuteAngle: Float
            get() = (-(2.0 * Math.PI * minute.toDouble())).toFloat() / 60

        val hourAngle: Float
            get() = (-(2.0 * Math.PI * (hour % 12 * 60 + minute).toDouble())).toFloat() / (12 * 60)

        init {
            val t = SimpleDateFormat("HH:mm:ss").format(Date())
                    .split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            hour = java.lang.Float.parseFloat(t[0])
            minute = java.lang.Float.parseFloat(t[1])
            second = java.lang.Float.parseFloat(t[2])
        }
    }

    override fun onInit() {
        /* Get time */
        val time = TimeFloat()

        /* Set title */
        setTitle("Simple rotate gears demo, pim 2015")

        /* Create a debug renderer */
        debugRenderer = DebugRenderer()

        /* Load images */
        bitmapClock = BitmapImage("images/clock.png")
        bitmapSecond = BitmapImage("images/clock_second.png")
        bitmapMinute = BitmapImage("images/clock_minute.png")
        bitmapHour = BitmapImage("images/clock_hour.png")

        /* Create the frame, the motors will be attached to it */
        val frame = PhysicsStaticBox("frame", CLOCK_CENTER,
                10f, 10f)

        /* Create the hands, at the current system time */
        hand_second = PhysicsBox("seconds", CLOCK_CENTER, 10f, 50f,
                time.secondAngle)
        hand_minute = PhysicsBox("minutes", CLOCK_CENTER, 10f, 40f,
                time.minuteAngle)
        hand_hour = PhysicsBox("hours", CLOCK_CENTER, 10f, 30f,
                time.hourAngle)

        /* Prevent collision between hands */
        hand_second.setCollisionGroup(-2)
        hand_minute.setCollisionGroup(-2)
        hand_hour.setCollisionGroup(-2)

        /* Create the motors */
        motor_seconds = PhysicsMotor(frame.body,
                hand_second.body, frame.body.worldCenter)

        motor_minutes = PhysicsMotor(frame.body,
                hand_minute.body, frame.body.worldCenter)

        /*
		 * This motor will only be used as a skeleton for the gear.
		 */
        val motor_m2h = PhysicsMotor(frame.body,
                hand_hour.body, frame.body.worldCenter)

        /*
		 * Create the gear between the minute hand and the hour hand
		 */
        val gear_m2h = GearJointDef()

        /* Do the connections */
        gear_m2h.bodyA = hand_minute.body
        gear_m2h.bodyB = hand_hour.body
        gear_m2h.joint1 = motor_minutes.joint
        gear_m2h.joint2 = motor_m2h.joint

        /*
		 * Negative ratio because the minute and hour hand rotates the same
		 * direction
		 */
        gear_m2h.ratio = -60f
        world.createJoint(gear_m2h)

        /*
		 * Start the clock, the second hand is running and the minute hand is
		 * stopped
		 */
        motor_seconds.initializeMotor(MOTOR_SPEED_SECOND, 1.0f, true)
        motor_minutes.initializeMotor(0.0f, 1.0f, true)

        println("click to switch debug/rendering mode")
    }

    override fun onGraphicRender(g: GdxGraphics) {

        /* Update the world physics */
        PhysicsWorld.updatePhysics()

        /* Sync signal emulation using system time */
        val sync_signal = TimeFloat().second % 60.0 == 0.0

        /*
		 * Second hand logic
		 *
		 * Stop when vertical, start when sync_sygnal
		 */
        if (sync_signal) {
            motor_seconds.motorSpeed = MOTOR_SPEED_SECOND
        } else {
            val angle = -hand_second.body.angle % (2 * Math.PI)

            if (angle > 2.0 * Math.PI * 0.995 && motor_seconds.speed.toDouble() != 0.0) {
                motor_seconds.motorSpeed = 0.0f
            }
        }

        /*
		 * Minute hand logic
		 *
		 * Move from 1 minute when sync_signal
		 */
        if (sync_signal) {
            motor_minutes.motorSpeed = MOTOR_SPEED_MINUTE
        } else {
            motor_minutes.motorSpeed = 0.0f
        }

        /* Get the size of the window */
        val w = windowWidth
        val h = windowHeight

        /* Clear the graphic to draw the new image */
        g.clear()

        if (debug_rendering) {
            debugRenderer!!.render(world, g.camera.combined)
            g.setColor(Color.WHITE)
        } else {
            /* Create a nice grey gradient for the background */
            for (i in 0..h) {
                val c = 255 - i * 0.3f
                g.setColor(Color(c / 255, c / 255, c / 255, 1.0f))
                g.drawLine(0f, (h - i).toFloat(), w.toFloat(), (h - i).toFloat())
            }

            /* Draw the clock frame */
            g.drawPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, bitmapClock)

            /* Draw the hands */
            g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
                    Math.toDegrees(hand_hour.body.angle.toDouble()).toFloat(), 1.0f,
                    bitmapHour)

            g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
                    Math.toDegrees(hand_minute.body.angle.toDouble()).toFloat(),
                    1.0f, bitmapMinute)

            g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
                    Math.toDegrees(hand_second.body.angle.toDouble()).toFloat(),
                    1.0f, bitmapSecond)
            g.setColor(Color.BLACK)
        }

        /* Display time in text */
        g.drawString((w - 200).toFloat(), (h - 10).toFloat(), "Famous clock from\r\n" + "the Swiss Railways.")

        g.drawString((w - 200).toFloat(), (h - 80).toFloat(), displayTime())

        g.drawSchoolLogo()
    }

    override
            /**
             * Change shape on click
             */
    fun onClick(x: Int, y: Int, button: Int) {
        debug_rendering = !debug_rendering
    }

    private fun displayTime(): String {
        // Return the current time as a String (hours:minutes:seconds)
        val df = SimpleDateFormat("HH:mm:ss")
        return "Current time: " + df.format(Date())
    }
}

fun main(args: Array<String>) {
  DemoRotateGears()
}
