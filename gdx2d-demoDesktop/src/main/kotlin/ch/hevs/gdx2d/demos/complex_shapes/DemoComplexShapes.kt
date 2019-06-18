package ch.hevs.gdx2d.demos.complex_shapes

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.colors.PaletteGenerator
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color


import java.util.Random
import java.util.Vector

/**
 * Performance demo animation rendering multiple circles at the same scale. This
 * is a complex demo, you should not start with this one.
 *
 * @author Pierre-André Mudry, mui
 * @version 1.0, April 2013
 */
class DemoComplexShapes : PortableApplication() {

    val rrand = Random(12345)
    val colors = Vector<Color>()
    val shapes = Vector<DrawableShape>()
    val directions = Vector<Int>()
    private val N_SHAPES = 500

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var maxRadius: Int = 0
    private var angle = 0f
    private var shape_type = type_shape.CIRCLE
    // For the movement of objects
    private var counter = 10.0
    private var dir = 1.34
    // The image which will be displayed
    private lateinit var imageBmp: BitmapImage

    /**
     * Create a nice color palette in the blue tones
     */
    fun fillPalette() {
        val a = Color(0.4f, 0f, 0.8f, 0f)
        val b = Color(0f, 0.2f, 0.97f, 0f)
        val c = Color(0f, 0.6f, 0.85f, 0f)

        for (i in 0..49) {
            colors.add(PaletteGenerator.RandomMix(a, b, c, 0.01f))
        }
    }

    protected fun destroyObjects(nObjects: Int) {
        for (i in 0 until nObjects)
            shapes.removeAt(0)
    }

    protected fun generateObjects(nObjects: Int) {
        /**
         * Generate some objects to be drawn randomly
         */
        for (i in 0 until nObjects) {
            val width = 10 + rrand.nextInt(40)

            val s = DrawableShape(width, width,
                    rrand.nextInt(screenWidth),
                    rrand.nextInt(screenHeight),
                    colors[rrand.nextInt(colors.size)])

            shapes.add(s)

            var dir = rrand.nextInt(10) + 1
            dir = if (rrand.nextBoolean()) dir else -dir

            directions.add(dir)
        }
    }

    override fun onInit() {
        fillPalette()

        this.setTitle("Demo shapes, mui 2013")
        screenWidth = windowWidth
        screenHeight = windowHeight
        maxRadius = Math.min(windowHeight / 2, windowWidth / 2) - 10

        imageBmp = BitmapImage("images/Android_PI_48x48.png")
        generateObjects(N_SHAPES)
    }

    override fun onGraphicRender(g: GdxGraphics) {
        // Updates the counter for the position on screen
        dir = if (counter > maxRadius || counter <= 5) dir * -1.0 else dir
        counter += dir
        angle = if (angle >= 360) 0f else angle + 0.2f

        // Move the shapes on the screen
        for (i in shapes.indices) {
            val r = shapes[i]

            if (r.x > screenWidth + imageBmp!!.image.width / 2 || r.x < 0) {
                val `val` = directions[i]
                directions.setElementAt(-`val`, i)
            }

            r.x += directions[i]
        }

        // Do the drawing
        when (shape_type) {
            DemoComplexShapes.type_shape.CIRCLE -> {
                g.clear(Color.BLACK)
                for (i in shapes) {
                    g.drawFilledCircle(i.x.toFloat(), i.y.toFloat(), i.width.toFloat(), i.c)
                }
            }
            DemoComplexShapes.type_shape.IMAGE -> {
                g.clear(Color(0.9f, 0.9f, 0.9f, 1f))
                for (i in shapes)
                    g.drawTransformedPicture(i.x.toFloat(), i.y.toFloat(), angle + i.offset, 1f, imageBmp)
            }
            DemoComplexShapes.type_shape.RECT -> {
                g.clear(Color.BLACK)
                for (i in shapes)
                // FIXME Did not work well for old Linux driver
                    g.drawFilledRectangle(i.x.toFloat(), i.y.toFloat(), i.width.toFloat(), i.width.toFloat(), 0f, i.c)
            }
        }

        g.drawSchoolLogo()
        g.drawFPS()
    }

    override fun onKeyDown(keycode: Int) {
        when (keycode) {
            Input.Keys.PLUS -> {
                generateObjects(100)
                Gdx.app.log("[DemoComplexShapes]", "N shapes " + shapes.size)
            }

            Input.Keys.MINUS -> if (shapes.size > 100) {
                Gdx.app.log("[DemoComplexShapes]", "N shapes " + shapes.size)
                destroyObjects(100)
            }
        }
    }

    override
            /**
             * Change shape on click
             */
    fun onClick(x: Int, y: Int, button: Int) {
        if (shape_type == type_shape.CIRCLE)
            shape_type = type_shape.RECT
        else if (shape_type == type_shape.RECT)
            shape_type = type_shape.IMAGE
        else if (shape_type == type_shape.IMAGE)
            shape_type = type_shape.CIRCLE
    }

    private enum class type_shape {
        CIRCLE, IMAGE, RECT
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoComplexShapes()
        }
    }
}
