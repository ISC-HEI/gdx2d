package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.GL20

/**
 * Demonstrates how to use [[com.badlogic.gdx.graphics.g2d.SpriteBatch]] blending
 * functions to combine source and destination images in different ways.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoBlendingFunctions extends DesktopApplication {

  private var imgBitmap: BitmapImage = _
  private var backgroundBitmap: BitmapImage = _
  private var w: Float = 0f
  private var h: Float = 0f

  private val srcFunctions = Array(
    GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_SRC_COLOR, GL20.GL_ONE_MINUS_SRC_COLOR,
    GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

  private val dstFunctions = Array(
    GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_DST_COLOR, GL20.GL_ONE_MINUS_DST_COLOR,
    GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

  override def onInit(): Unit = {
    setTitle("Blending functions for images, mui 2013")
    imgBitmap = new BitmapImage("images/texture.png")
    backgroundBitmap = new BitmapImage("images/back1_512.png")
    w = imgBitmap.getImage.getWidth.toFloat
    h = imgBitmap.getImage.getHeight.toFloat
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    g.drawBackground(backgroundBitmap, 0f, 0f)

    for (i <- 0 to 5; j <- 0 to 5) {
      g.setBlendFunction(srcFunctions(i), dstFunctions(j))
      g.drawPicture(60f + w * i + 10 * i, 60f + h * j + 10 * j, imgBitmap)
    }

    g.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onDispose(): Unit = {
    super.onDispose()
    imgBitmap.dispose()
    backgroundBitmap.dispose()
  }
}

object DemoBlendingFunctions {
  def main(args: Array[String]): Unit = new DemoBlendingFunctions().launch()
}
