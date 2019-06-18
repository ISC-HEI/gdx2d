package ch.hevs.gdx2d.demos.accelerometer

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Peripheral

/**
 * A demonstration program for the accelerometers
 * This works only on Android.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoAccelerometer : PortableApplication() {
    private val SMOOTHING = 30.0 // This value changes the dampening effect of the low-pass
    private lateinit var compassBitmap: BitmapImage
    // For low-pass filtering
    private var smoothedValue = 0f

    override fun onGraphicRender(g: GdxGraphics) {
        // Read the accelerometers
        val accelX = Gdx.input.accelerometerX
        val accelY = Gdx.input.accelerometerY

        // Low pass filtering of the value
        smoothedValue += ((accelX - smoothedValue) / SMOOTHING).toFloat()

        // Draws
        g.clear()
        g.drawTransformedPicture((windowWidth / 2).toFloat(), (windowHeight / 2).toFloat(), smoothedValue * 20, 1f, compassBitmap)
        g.drawSchoolLogo()

        g.drawString(10f, 60f, "Non filtered values:")
        g.drawString(15f, 20f, "X $accelX")
        g.drawString(15f, 40f, "Y $accelY")
    }

    override fun onInit() {
        val available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)

        if (!available) {
            Gdx.app.error("Libgdx error", "Accelerometers are not available ! Exiting")
            Gdx.app.exit()
        }

        compassBitmap = BitmapImage("images/compass_150.png")
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoAccelerometer()
        }
    }
}
