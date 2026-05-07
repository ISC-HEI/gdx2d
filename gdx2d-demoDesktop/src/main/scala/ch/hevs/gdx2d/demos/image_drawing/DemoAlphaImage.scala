package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Similar to [[DemoSimpleImage]] but demonstrates how to use the alpha
 * parameter to draw images.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoAlphaImage extends DesktopApplication {

  private var imgBitmap: BitmapImage = _
  private var backgroundBitmap: BitmapImage = _

  private var alpha1 = 0.06f
  private var alpha2 = 0.3f
  private var alpha3 = 0.6f
  private var alpha4 = 0.94f
  private var dir1 = 1
  private var dir2 = 1
  private var dir3 = 1
  private var dir4 = 1

  override def onInit(): Unit = {
    setTitle("Alpha transparency modification for images, mui 2013")
    imgBitmap = new BitmapImage("images/Android_PI_48x48.png")
    backgroundBitmap = new BitmapImage("images/back1_512.png")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    g.drawBackground(backgroundBitmap, 0f, 0f)

    if (alpha1 <= 0.05f || alpha1 >= 0.95f) dir1 *= -1
    if (alpha2 <= 0.05f || alpha2 >= 0.95f) dir2 *= -1
    if (alpha3 <= 0.05f || alpha3 >= 0.95f) dir3 *= -1
    if (alpha4 <= 0.05f || alpha4 >= 0.95f) dir4 *= -1

    alpha1 += (if (dir1 > 0) 0.01f else -0.01f)
    alpha2 += (if (dir2 > 0) 0.01f else -0.01f)
    alpha3 += (if (dir3 > 0) 0.01f else -0.01f)
    alpha4 += (if (dir4 > 0) 0.01f else -0.01f)

    g.drawAlphaPicture(getWindowWidth / 3.0f, getWindowHeight / 3.0f, alpha1, imgBitmap)
    g.drawAlphaPicture(getWindowWidth * 2.0f / 3.0f, getWindowHeight / 3.0f, alpha2, imgBitmap)
    g.drawAlphaPicture(getWindowWidth / 3.0f, getWindowHeight * 2.0f / 3.0f, alpha3, imgBitmap)
    g.drawAlphaPicture(getWindowWidth * 2.0f / 3.0f, getWindowHeight * 2.0f / 3.0f, alpha4, imgBitmap)

    g.drawFPS()
    g.drawSchoolLogo()
  }

  override def onDispose(): Unit = {
    super.onDispose()
    imgBitmap.dispose()
    backgroundBitmap.dispose()
  }
}

object DemoAlphaImage {
  def main(args: Array[String]): Unit = new DemoAlphaImage().launch()
}
