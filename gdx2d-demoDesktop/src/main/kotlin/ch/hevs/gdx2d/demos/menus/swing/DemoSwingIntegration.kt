package ch.hevs.gdx2d.demos.menus.swing
/*

import ch.hevs.gdx2d.demos.physics.DemoSimplePhysics
import ch.hevs.gdx2d.desktop.Game2D
import ch.hevs.gdx2d.desktop.PortableApplication
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas
import ch.hevs.gdx2d.demos.simple.DemoCircles

import javax.swing.*
import java.awt.*
import java.util.ArrayList

class DemoSwingIntegration : JFrame() {

    var current = 0

    var canvasList = ArrayList<Lwjgl3AWTCanvas>()

    init {
        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        this.title = "Embedding a gdx2demo into Swing components"
        this.isResizable = false

        val container = contentPane
        container.layout = FlowLayout()

        PortableApplication.CreateLwjglApplication = false // TODO: configuration this way is ugly...

        val jp1 = JPanel()
        jp1.layout = FlowLayout()
        val jb1 = JButton("Change demo")
        jp1.add(jb1)
        container.add(jp1)

        val canvas = LwjglAWTCanvas(Game2D(DemoSimplePhysics()))
        canvas.canvas.setSize(400, 400)
        container.add(canvas.canvas)
        canvasList.add(canvas)

        jb1.addActionListener {
            canvasList[0].stop()

            val newCanvas = LwjglAWTCanvas(Game2D(if (current % 2 == 1) DemoSimplePhysics() else DemoCircles()))

            current++

            canvasList.clear()
            canvasList.add(0, newCanvas)

            container.remove(1)
            newCanvas.canvas.setSize(400, 400)
            container.add(newCanvas.canvas)
            pack()
        }

        setSize(600, 500)
        setLocationRelativeTo(null)
        isVisible = true
        pack()
    }
}

fun main(args: Array<String>) {
  SwingUtilities.invokeLater { DemoSwingIntegration() }
}
 */
