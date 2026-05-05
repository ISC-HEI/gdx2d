package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

/**
 * Demonstrates the use of `BitmapImage.getColor` on a real case.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoGetImageColor extends DesktopApplication {

  private var imgBitmap: BitmapImage = _

  override def onInit(): Unit = {
    setTitle("Get image color, mui 2014")
    Logger.log("Move the mouse on the image to get its color")
    imgBitmap = new BitmapImage("images/color_pattern.png")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    val imagePosition = new Vector2(100f, 100f)
    val sampledPixel = new Vector2(Gdx.input.getX.toFloat,
      (Gdx.graphics.getHeight - Gdx.input.getY).toFloat)

    g.drawPicture(imagePosition.x, imagePosition.y, imgBitmap)

    // Screen coordinates -> image coordinates
    val imgPixel = imgBitmap.pixelInScreenSpace(sampledPixel, imagePosition)

    if (imgBitmap.isContained(imgPixel)) {
      val c = imgBitmap.getColor(imgPixel.x.toInt, imgPixel.y.toInt)
      g.drawStringCentered(300f, "Color read from the image")
      g.drawFilledCircle(250f, 250f, 20f, c)
    }

    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onDispose(): Unit = {
    super.onDispose()
    imgBitmap.dispose()
  }
}

object DemoGetImageColor {
  def main(args: Array[String]): Unit = new DemoGetImageColor().launch()
}
