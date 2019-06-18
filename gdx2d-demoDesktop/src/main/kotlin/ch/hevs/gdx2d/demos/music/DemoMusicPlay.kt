package ch.hevs.gdx2d.demos.music

import ch.hevs.gdx2d.components.audio.MusicPlayer
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.AndroidResolver
import com.badlogic.gdx.graphics.Color

/**
 * Shows how to play music in the framework
 *
 *
 * TODO: add short sample play to demonstrate the difference
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.2
 */
class DemoMusicPlay : PortableApplication() {

    private var f: MusicPlayer? = null

    override fun onGraphicRender(g: GdxGraphics) {
        // Clear the screen
        g.clear()
        g.setColor(Color.WHITE)

        // Audio logic
        val text: String
        if (f!!.isPlaying)
            text = "Playing song"
        else
            text = "Click to play"

        g.drawString(20f, (windowHeight / 2).toFloat(), text)

        // Draws the school logo
        g.drawSchoolLogo()
    }

    /**
     * Called when the class is terminated
     */
    override fun onDispose() {
        super.onDispose()

        // We must release all the resources we got
        f!!.dispose()
    }

    override fun onInit() {
        setTitle("Music player, mui 2013")

        // Load the MP3 sound file
        f = MusicPlayer("music/Blues-Loop.mp3")
    }

    override fun onClick(x: Int, y: Int, button: Int) {
        if (f!!.isPlaying)
            f!!.stop()
        else
            f!!.loop()

        if (onAndroid()) {
            // Display Toast on Android
            val sToast = if (f!!.isPlaying)
                "Playing started."
            else
                "Playing stopped."
            androidResolver
                    .showToast(sToast, AndroidResolver.LENGTH_SHORT)
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoMusicPlay()
        }
    }
}
