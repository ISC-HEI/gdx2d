package ch.hevs.gdx2d.demos.controllers

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.Xbox
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

import java.util.HashMap

/**
 * Demo program for the `controllers` extension.
 *
 * If you use the XBox One controller, see
 * [
 * this image](https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/360_controller.svg/450px-360_controller.svg.png) which describes each button and axes.
 *
 * The XBox One controller image comes [here](from https://thenounproject.com/term/xbox-one/45114/) (CC BY
 * 3.0 US).
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
class DemoControllers private constructor() : PortableApplication(700, 700, false) {

    private var background: BitmapImage? = null
    private val leftSickVal = Vector2.Zero.cpy() // X,Y values of the left stick (POV)
    private val rightSickVal = Vector2.Zero.cpy() // X,Y values of the left stick (POV)

    private var leftStickPos: Vector2? = null // Center coordinates of the left stick
    private var rightStickPos: Vector2? = null // Center coordinates of the right stick

    private var endPos = Vector2()
    private var center: Vector2? = null

    private var ctrl: Controller? = null // The connected controller

    override fun onInit() {
        setTitle("Controllers demo, mui, mei 2016")

        Logger.log(TAG, "%d controller(s) found", Controllers.getControllers().size)
        for (controller in Controllers.getControllers()) {
            Logger.log(TAG, " - " + controller.name)
        }

        if (Controllers.getControllers().size > 0)
            ctrl = Controllers.getControllers().first()

        background = BitmapImage("images/noun_45114_cc.png")

        center = Vector2(windowWidth.toFloat(), windowHeight.toFloat()).scl(0.5f)
        leftStickPos = Vector2(-105f, 97f).add(center!!)
        rightStickPos = Vector2(58f, 33f).add(center!!)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear(Color.WHITE)

        if (ctrl == null)
            return

        g.drawBackground(background, 0f, 0f)

        g.setColor(Color.RED)
        g.setPenWidth(3f)

        // Left stick display
        endPos.x = leftSickVal.x * 34
        endPos.y = leftSickVal.y * 34
        endPos = endPos.limit(34f) // Make a nice round
        g.drawLine(leftStickPos!!.x, leftStickPos!!.y, leftStickPos!!.x + endPos.x, leftStickPos!!.y - endPos.y)

        // Right stick display
        endPos.x = rightSickVal.x * 34
        endPos.y = rightSickVal.y * 34
        endPos = endPos.limit(34f) // Make a nice round
        g.drawLine(rightStickPos!!.x, rightStickPos!!.y, rightStickPos!!.x + endPos.x, rightStickPos!!.y - endPos.y)

        // Buttons clicked
        for (buttonCode in buttonPos.keys) {
            if (ctrl!!.getButton(buttonCode)) {
                val pos = buttonPos[buttonCode]!!.cpy().add(center!!)
                g.drawFilledCircle(pos.x, pos.y, 10f, Color.RED)
            }
        }

        g.drawFPS(Color.BLACK)
        g.drawSchoolLogo()
    }

    override fun onControllerConnected(controller: Controller) {
        Logger.log(TAG, "A controller has been connected !")
        ctrl = controller
    }

    override fun onControllerDisconnected(controller: Controller) {
        ctrl = null
    }

    override fun onControllerKeyDown(controller: Controller, buttonCode: Int) {
        if (buttonCode == Xbox.X)
            Logger.log(TAG, "Button X pressed")
        if (buttonCode == Xbox.Y)
            Logger.log(TAG, "Button Y pressed")
        if (buttonCode == Xbox.A)
            Logger.log(TAG, "Button A pressed")
        if (buttonCode == Xbox.B)
            Logger.log(TAG, "Button B pressed")
        if (buttonCode == Xbox.L_TRIGGER)
            Logger.log(TAG, "Button L trigger pressed")
        if (buttonCode == Xbox.R_TRIGGER)
            Logger.log(TAG, "Button R trigger pressed")
    }

    override fun onControllerAxisMoved(controller: Controller, axisCode: Int, value: Float) {
        super.onControllerAxisMoved(controller, axisCode, value)

        if (axisCode == Xbox.L_STICK_HORIZONTAL_AXIS)
            leftSickVal.x = value

        if (axisCode == Xbox.L_STICK_VERTICAL_AXIS)
            leftSickVal.y = value

        if (axisCode == Xbox.R_STICK_HORIZONTAL_AXIS)
            rightSickVal.x = value

        if (axisCode == Xbox.R_STICK_VERTICAL_AXIS)
            rightSickVal.y = value
    }

    override fun onControllerPovMoved(controller: Controller, povCode: Int, value: PovDirection) {
        Logger.log(TAG, "POV: $value")
    }

    companion object {

        private val TAG = DemoControllers::class.java.getSimpleName()

        // Contain some button positions used to draw a circle when they are pressed
        private val buttonPos = HashMap<Int, Vector2>()

        init {
            buttonPos[Xbox.X] = Vector2(85f, 97f)
            buttonPos[Xbox.Y] = Vector2(113f, 123f)
            buttonPos[Xbox.A] = Vector2(113f, 71f)
            buttonPos[Xbox.B] = Vector2(141f, 97f)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            DemoControllers()
        }
    }
}
