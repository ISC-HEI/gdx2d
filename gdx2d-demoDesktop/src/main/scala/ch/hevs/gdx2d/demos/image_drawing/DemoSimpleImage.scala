package ch.hevs.gdx2d.demos.image_drawing

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Demo application to demonstrate how to load a file which should be located
 * in a `data` folder at the root of the project.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoSimpleImage extends DesktopApplication {

  protected var counter: Float = 0f
  protected var imgBitmap: BitmapImage = _

  override def onInit(): Unit = {
    setTitle("Simple image drawing, mui 2013")
    imgBitmap = new BitmapImage("images/compass_150.png")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    g.drawPicture((getWindowWidth / 2).toFloat, (getWindowHeight / 2).toFloat, imgBitmap)
    g.drawFPS()
    g.drawSchoolLogo()
  }
}

object DemoSimpleImage {
  def main(args: Array[String]): Unit = new DemoSimpleImage().launch()
}
