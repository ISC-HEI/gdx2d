package ch.hevs.gdx2d.demos.menus

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

/**
 * Very simple UI demonstration
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
class DemoGUI : PortableApplication() {
    lateinit var skin: Skin
    lateinit var stage: Stage
    lateinit var newGameButton: TextButton
    lateinit var quitGameButton: TextButton
    lateinit var textArea: TextField

    override fun onInit() {
        val buttonWidth = 180
        val buttonHeight = 30

        setTitle("GUI demonstration")

        stage = Stage()
        Gdx.input.inputProcessor = stage// Make the stage consume events

        // Load the default skin (which can be configured in the JSON file)
        skin = Skin(Gdx.files.internal("ui/uiskin.json"))

        newGameButton = TextButton("Click me", skin) // Use the initialized skin
        newGameButton.width = buttonWidth.toFloat()
        newGameButton.height = buttonHeight.toFloat()

        quitGameButton = TextButton("Useless button", skin) // Use the initialized skin
        quitGameButton.width = buttonWidth.toFloat()
        quitGameButton.height = buttonHeight.toFloat()

        newGameButton.setPosition((Gdx.graphics.width / 2 - buttonWidth / 2).toFloat(), (Gdx.graphics.height * .6).toInt().toFloat())
        quitGameButton.setPosition((Gdx.graphics.width / 2 - buttonWidth / 2).toFloat(), (Gdx.graphics.height * .7).toInt().toFloat())

        textArea = TextField("Enter some text...", skin)
        textArea.width = buttonWidth.toFloat()
        textArea.setPosition((Gdx.graphics.width / 2 - buttonWidth / 2).toFloat(), (Gdx.graphics.height * .4).toInt().toFloat())

        textArea.setTextFieldListener { _, key ->
            textArea.setSelection(0, 0)

            // When you press 'enter', do something
            if (key.toInt() == 13)
                Logger.log("You have typed " + textArea.text)
        }

        /**
         * Adds the buttons to the stage
         */
        stage.addActor(newGameButton)
        stage.addActor(quitGameButton)
        stage.addActor(textArea)

        /**
         * Register listener
         */
        newGameButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                super.clicked(event, x, y)

                if (newGameButton.isChecked)
                    Logger.log("Button is checked")
                else
                    Logger.log("Button is not checked")
            }
        })
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear(Color.BLACK)

        // This is required for having the GUI work properly
        stage.act()
        stage.draw()

        g.drawStringCentered((windowHeight / 4).toFloat(), "Button status " + newGameButton.isChecked)
        g.drawSchoolLogo()
        g.drawFPS()
    }

    override fun onDispose() {
        super.onDispose()
        stage.dispose()
        skin.dispose()
    }
}

fun main(args: Array<String>) {
  DemoGUI()
}
