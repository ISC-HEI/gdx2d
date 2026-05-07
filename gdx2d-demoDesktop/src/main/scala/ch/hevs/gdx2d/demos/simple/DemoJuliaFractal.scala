package ch.hevs.gdx2d.demos.simple

import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.graphics.Pixmap.Format

/**
 * Compute a Julia fractal (Julia set).
 *
 * Some parameters can be used to tune the fractal — `C1`, `C2`, `MAX_ITER` and
 * `SCALE` in the companion object.
 *
 * @author Christopher Metrailler (mei)
 * @version 2.0
 */
class DemoJuliaFractal extends DesktopApplication {
  import DemoJuliaFractal._

  private val pixmap = new Pixmap(IMAGE_SIZE, IMAGE_SIZE, Format.RGBA8888)
  private var currentTexture: Texture = _
  private var isFractalGenerated = false
  private var estimatedTime: Long = 0

  override def onInit(): Unit = {
    setTitle("Julia fractal, mei 2014")
    Logger.log("Click to generate a fractal with new parameters.")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    // The fractal is generated only once. Takes about 1/10 seconds
    // depending on the fractal parameters.
    if (!isFractalGenerated) {
      pixmap.fill() // Use a Pixmap because Pixmap.drawPixel is much faster than setPixel

      val startTime = System.currentTimeMillis()
      var i = 0
      while (i < IMAGE_SIZE) {
        var j = 0
        while (j < IMAGE_SIZE) {
          workPixel(i, j, pixmap)
          j += 1
        }
        i += 1
      }
      estimatedTime = System.currentTimeMillis() - startTime

      currentTexture = new Texture(pixmap, Format.RGBA8888, false)
      isFractalGenerated = true
      Logger.log(f"New fractal: C1=$C1%f, C2=$C2%f.")
    }

    g.clear(BCK_COLOR)
    g.drawBackground(currentTexture, 0f, 0f)
    g.drawSchoolLogo()

    val info = f"Fractal generated in $estimatedTime%d ms."
    g.drawString(10f, (0.98 * g.getScreenHeight).toInt.toFloat, info)
  }

  override def onClick(x: Int, y: Int, button: Int): Unit = {
    // Generate some new fractals
    C1 += 0.05f
    if (C1 > 0.2f) C1 = C1_START
    isFractalGenerated = false
  }

  override def onDispose(): Unit = {
    pixmap.dispose()
  }
}

object DemoJuliaFractal {
  private val IMAGE_SIZE = 512 // create an N-by-N image (power of two)
  private val BCK_COLOR = new Color(0.0f, 0f, 0.2f, 1.0f)
  private val MAX_ITER = 115 // Stop after max iteration for a pixel
  private val SCALE = 1 / 2f // Scale factor for the fractal
  private val C1_START = -0.55f
  private val C2_START = 0.65f
  private var C1 = C1_START
  private val C2 = C2_START

  private def workPixel(i: Int, j: Int, pixmap: Pixmap): Unit = {
    val x = i.toFloat * SCALE * 2f / IMAGE_SIZE.toFloat - 1 * SCALE
    val y = j.toFloat * SCALE * 2f / IMAGE_SIZE.toFloat - 1 * SCALE

    var k = 0
    var a = x
    var b = y

    while (k < MAX_ITER && a * a + b * b < 4) {
      val aCopy = a
      a = a * a - b * b + C1
      b = 2f * aCopy * b + C2
      k += 1
    }

    if (k == MAX_ITER) {
      pixmap.drawPixel(i, j, Color.rgba8888(1.0f, 0f, 0f, 1.0f))
    } else {
      val color = ColorUtils.hsvToColor(k / MAX_ITER.toFloat, 1.0f, 1.0f)
      pixmap.drawPixel(i, j, color.toIntBits())
    }
  }

  def main(args: Array[String]): Unit = new DemoJuliaFractal().launch()
}
