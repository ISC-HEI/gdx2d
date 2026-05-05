package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Similar to [[DemoSimpleImage]] but demonstrates how to use the
 * `mirrorUpDown` and `mirrorLeftRight` methods.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoMirrorImage extends DesktopApplication {

  private var imgBitmap: BitmapImage = _

  override def onInit(): Unit = {
    setTitle("Mirror image drawing, mui 2013")
    imgBitmap = new BitmapImage("images/Android_PI_48x48.png")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    g.drawPicture(getWindowWidth / 3.0f, getWindowHeight / 3.0f, imgBitmap)
    imgBitmap.mirrorUpDown()
    g.drawPicture(getWindowWidth * 2.0f / 3.0f, getWindowHeight / 3.0f, imgBitmap)
    imgBitmap.mirrorUpDown()

    g.drawPicture(getWindowWidth / 3.0f, getWindowHeight * 2.0f / 3.0f, imgBitmap)
    imgBitmap.mirrorLeftRight()
    g.drawPicture(getWindowWidth * 2.0f / 3.0f, getWindowHeight * 2.0f / 3.0f, imgBitmap)
    imgBitmap.mirrorLeftRight()

    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoMirrorImage {
  def main(args: Array[String]): Unit = new DemoMirrorImage().launch()
}
