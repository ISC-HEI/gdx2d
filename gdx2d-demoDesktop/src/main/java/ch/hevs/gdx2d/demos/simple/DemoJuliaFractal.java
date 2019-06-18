package ch.hevs.gdx2d.demos.simple

import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.Texture

/**
 * Compute a Julia fractal (Julia set).
 *
 *
 * Some parameters can be used to tune the fractal, like
 * [DemoJuliaFractal.C1], [DemoJuliaFractal.C2] coefficients,
 * [DemoJuliaFractal.MAX_ITER] and the [DemoJuliaFractal.SCALE].<br></br>
 * For now, this demonstration is not available for Android because it is too
 * slow on mobile. To have better performances, RenderScript or shader must be
 * used.
 *
 * @author Christopher Metrailler (mei)
 * @version 1.0
 */
class DemoJuliaFractal : PortableApplication() {
    private val pixmap = Pixmap(IMAGE_SIZE, IMAGE_SIZE, Format.RGBA8888)
    private var currentTexture: Texture? = null
    private var isFractalGenerated = false
    private var startTime: Long = 0
    private var estimatedTime: Long = 0 // Time measurement

    override fun onInit() {
        this.setTitle("Julia fractal, mei 2014")
        Logger.log("Click to generate a fractal with new parameters.")
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // The fractal is generated only once. Takes about 1/10 seconds
        // depending on the fractal parameters.
        if (!isFractalGenerated) {
            pixmap.fill()   // Use a Pixamp because the setPixel method is very slow (issue #26)

            // Computation time estimation
            startTime = System.currentTimeMillis()
            for (i in 0 until IMAGE_SIZE)
                for (j in 0 until IMAGE_SIZE)
                // Compute the Julia fractal for each pixels
                    workPixel(i, j, pixmap)
            estimatedTime = System.currentTimeMillis() - startTime

            currentTexture = Texture(pixmap, Format.RGBA8888, false)
            isFractalGenerated = true
            Logger.log(String.format("New fractal: C1=%f, C2=%f.", C1, C2))
        }

        /* Drawing */
        g.clear(BCK_COLOR) // Add some blue for the fractal background

        // Draw the generated fractal
        g.drawBackground(currentTexture, 0f, 0f)
        g.drawSchoolLogo()

        // Display the fractal generation time
        val info = String.format("Fractal generated in %d ms.", estimatedTime)
        g.drawString(10f, (0.98 * g.screenHeight).toInt().toFloat(), info)
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        // Generate some new fractals
        C1 += 0.05f
        if (C1 > 0.2f)
            C1 = C1_START
        isFractalGenerated = false
    }

    override fun onDispose() {
        pixmap.dispose()
    }

    companion object {

        /* Fractal parameters to be tuned */
        private val IMAGE_SIZE = 512 // create a N-by-N image (power of two)
        /* Used for pixels operations */
        private val BCK_COLOR = Color(0.0f, 0f, 0.2f, 1.0f)
        private val MAX_ITER = 115 // Stop after max iteration for a pixel
        private val SCALE = 1 / 2f // Scale factor for the fractal
        // Base fractal coefficients
        private val C1_START = -0.55f
        private val C2_START = 0.65f
        private var C1 = C1_START
        private val C2 = C2_START

        // Julia fractal on each pixels
        private fun workPixel(i: Int, j: Int, pixmap: Pixmap) {
            // Convert to mathematical coordinates with a custom scale
            val x = i.toFloat() * SCALE * 2f / IMAGE_SIZE.toFloat() - 1 * SCALE
            val y = j.toFloat() * SCALE * 2f / IMAGE_SIZE.toFloat() - 1 * SCALE

            var k = 0
            var a = x
            var b = y

            // Julia algorithm with a max upper bound
            while (k < MAX_ITER && a * a + b * b < 4) {
                val aCopy = a
                a = a * a - b * b + C1
                b = 2f * aCopy * b + C2
                k++
            }

            // Draw the current pixel
            if (k == MAX_ITER)
            // Draw red pixels when max iteration is reached
                pixmap.drawPixel(i, j, Color.rgba8888(1.0f, 0f, 0f, 1.0f))
            else {
                // Use HSV to have a better color contrast
                val color = ColorUtils.hsvToColor(k / MAX_ITER.toFloat(), 1.0f, 1.0f)
                pixmap.drawPixel(i, j, color.toIntBits()) // Convert to ABGR to draw
            }
        }

        // Java main for the desktop demonstration
        @JvmStatic
        fun main(args: Array<String>) {
            DemoJuliaFractal()
        }
    }
}
