package ch.hevs.gdx2d.demos.complex_shapes

import java.util.Random

import scala.collection.mutable.ArrayBuffer

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.colors.PaletteGenerator
import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color

/**
 * Performance demo animation rendering multiple circles at the same scale. This
 * is a complex demo, you should not start with this one.
 *
 * @author Pierre-André Mudry, mui
 * @version 2.0
 */
class DemoComplexShapes extends DesktopApplication {
  import DemoComplexShapes.ShapeType

  private val rrand = new Random(12345L)
  private val colors = new ArrayBuffer[Color]
  private val shapes = new ArrayBuffer[DrawableShape]
  private val directions = new ArrayBuffer[Int]
  private val N_SHAPES = 500

  private var screenWidth: Int = 0
  private var screenHeight: Int = 0
  private var maxRadius: Int = 0
  private var angle: Float = 0f
  private var shapeType: ShapeType.Value = ShapeType.CIRCLE

  // For the movement of objects
  private var counter = 10.0
  private var dir = 1.34

  private var imageBmp: BitmapImage = _

  /** Create a nice color palette in the blue tones. */
  private def fillPalette(): Unit = {
    val a = new Color(0.4f, 0f, 0.8f, 0f)
    val b = new Color(0f, 0.2f, 0.97f, 0f)
    val c = new Color(0f, 0.6f, 0.85f, 0f)
    for (_ <- 0 until 50) colors += PaletteGenerator.RandomMix(a, b, c, 0.01f)
  }

  private def destroyObjects(nObjects: Int): Unit = {
    for (_ <- 0 until nObjects) shapes.remove(0)
  }

  private def generateObjects(nObjects: Int): Unit = {
    for (_ <- 0 until nObjects) {
      val width = 10 + rrand.nextInt(40)
      val s = new DrawableShape(
        width, width,
        rrand.nextInt(screenWidth),
        rrand.nextInt(screenHeight),
        colors(rrand.nextInt(colors.size)))
      shapes += s

      var dirLocal = rrand.nextInt(10) + 1
      if (!rrand.nextBoolean()) dirLocal = -dirLocal
      directions += dirLocal
    }
  }

  override def onInit(): Unit = {
    fillPalette()
    setTitle("Demo shapes, mui 2013")
    screenWidth = getWindowWidth
    screenHeight = getWindowHeight
    maxRadius = Math.min(getWindowHeight / 2, getWindowWidth / 2) - 10
    imageBmp = new BitmapImage("images/Android_PI_48x48.png")
    generateObjects(N_SHAPES)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (counter > maxRadius || counter <= 5) dir *= -1.0
    counter += dir
    angle = if (angle >= 360) 0f else angle + 0.2f

    // Move shapes
    for (i <- shapes.indices) {
      val r = shapes(i)
      if (r.x > screenWidth + imageBmp.getImage.getWidth / 2 || r.x < 0) {
        directions(i) = -directions(i)
      }
      r.x += directions(i)
    }

    // Draw
    shapeType match {
      case ShapeType.CIRCLE =>
        g.clear(Color.BLACK)
        for (s <- shapes) g.drawFilledCircle(s.x.toFloat, s.y.toFloat, s.width.toFloat, s.c)
      case ShapeType.IMAGE =>
        g.clear(new Color(0.9f, 0.9f, 0.9f, 1f))
        for (s <- shapes) g.drawTransformedPicture(s.x.toFloat, s.y.toFloat, angle + s.offset, 1f, imageBmp)
      case ShapeType.RECT =>
        g.clear(Color.BLACK)
        for (s <- shapes) g.drawFilledRectangle(s.x.toFloat, s.y.toFloat, s.width.toFloat, s.width.toFloat, 0f, s.c)
    }

    g.drawSchoolLogo()
    g.drawFPS()
  }

  override def onKeyDown(keycode: Int): Unit = {
    keycode match {
      case Input.Keys.PLUS =>
        generateObjects(100)
        Gdx.app.log("[DemoComplexShapes]", "N shapes " + shapes.size)
      case Input.Keys.MINUS if shapes.size > 100 =>
        Gdx.app.log("[DemoComplexShapes]", "N shapes " + shapes.size)
        destroyObjects(100)
      case _ => super.onKeyDown(keycode)
    }
  }

  /** Change shape on click. */
  override def onClick(x: Int, y: Int, button: Int): Unit = {
    shapeType = shapeType match {
      case ShapeType.CIRCLE => ShapeType.RECT
      case ShapeType.RECT   => ShapeType.IMAGE
      case ShapeType.IMAGE  => ShapeType.CIRCLE
    }
  }
}

object DemoComplexShapes {
  private object ShapeType extends Enumeration {
    val CIRCLE, IMAGE, RECT = Value
  }

  def main(args: Array[String]): Unit = new DemoComplexShapes().launch()
}
