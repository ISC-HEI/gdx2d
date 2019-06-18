package ch.hevs.gdx2d.demos.music

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color

import com.badlogic.gdx.graphics.Color.LIGHT_GRAY

/**
 * This demonstrates how to play short samples with GDX2d.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class DemoBabyPiano : PortableApplication() {

    lateinit var s1: SoundSample
    lateinit var s2: SoundSample
    lateinit var s3: SoundSample
    lateinit var s4: SoundSample

    override fun onGraphicRender(g: GdxGraphics) {
        // Clear the screen
        g.clear(Color.BLACK)
        g.setColor(LIGHT_GRAY)

        // Audio logic
        val text = "Press 1/2/3/4 to play different samples"
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
        s1.dispose()
        s2.dispose()
        s3.dispose()
        s4.dispose()
    }

    override fun onInit() {
        setTitle("Sound samples player, mui 2014")

        // Load the MP3 sound file
        s1 = SoundSample("music/babypianosamples/Honky_C1.wav")
        s2 = SoundSample("music/babypianosamples/Honky_C2.wav")
        s3 = SoundSample("music/babypianosamples/Honky_C3.wav")
        s4 = SoundSample("music/babypianosamples/Honky_C4.wav")
    }

    override fun onKeyDown(keycode: Int) {
        super.onKeyDown(keycode)

        if (keycode == Keys.NUM_1)
            s1.play()
        if (keycode == Keys.NUM_2)
            s2.play()
        if (keycode == Keys.NUM_3)
            s3.play()
        if (keycode == Keys.NUM_4)
            s4.play()

        if (keycode == Keys.SPACE) {
            s1.setPitch(2f)
            s2.setPitch(2f)
            s3.setPitch(2f)
            s4.setPitch(2f)
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoBabyPiano()
        }
    }
}

