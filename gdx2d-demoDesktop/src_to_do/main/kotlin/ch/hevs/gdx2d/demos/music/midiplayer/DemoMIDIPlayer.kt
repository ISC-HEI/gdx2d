package ch.hevs.gdx2d.demos.music.midiplayer

import ch.hevs.gdx2d.components.audio.SoundSample
import ch.hevs.gdx2d.components.colors.ColorUtils
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.graphics.Color
import ch.hevs.gdx2d.demos.music.midiplayer.MIDIPlayer.MIDIListener
import ch.hevs.gdx2d.demos.music.midiplayer.MIDIPlayer.MIDIMessage
import ch.hevs.gdx2d.demos.music.midiplayer.MIDIPlayer.MIDIPLayer

import java.util.ArrayList
import java.util.Collections

/**
 * A MIDI sampler demo
 *
 * @author Mikael Follonier (fom)
 * @version 1.2
 */
class DemoMIDIPlayer : PortableApplication() {
    // Where the music and samples are stored
    protected val nSamples = 104
    protected val DATADIR = "music/midiplayer/pianonotes/"
    protected val FILEPATH = "music/midiplayer/brahms_lullaby.mid"

    // Storage for the loaded sound samples
    protected lateinit var notesSamples: ArrayList<SoundSample>

    // Contains the notes that are currently playing
    protected var currentlyPlayingNotes = ArrayList<Int>()

    // This is the class that handles the player inputs
    protected lateinit var player: MIDIPLayer

    // The object that will receive the note messages
    lateinit var midiListener: MIDIListener

    override fun onInit() {
        setTitle("MIDI demo, fom 2015")
        notesSamples = ArrayList()

        // Read all the piano samples that are in the directory
        for (i in 0 until nSamples) {
            notesSamples.add(SoundSample(DATADIR + String.format("%03d", i) + ".mp3"))
        }

        midiListener = object : MIDIListener {
            /**
             * Handles the received MIDI message and plays the corresponding sample
             * @param msg msg Message list for a given MIDI tick
             */
            override fun onMIDIMessage(msg: ArrayList<MIDIMessage>) {
                // Play the notes from the MIDI player
                for (message in msg) {
                    synchronized(currentlyPlayingNotes) {
                        if (message.cmd == MIDIPLayer.NOTE_ON) {
                            notesSamples[message.key].play() //MIDI notes start at octave -2
                            currentlyPlayingNotes.add(message.note)
                        }

                        if (message.cmd == MIDIPLayer.NOTE_OFF) {
                            currentlyPlayingNotes.remove(message.note)
                        }

                        // Sort the list
                        Collections.sort(currentlyPlayingNotes)
                    }
                }
            }

            override fun onTrackFinished() {
                Logger.log("Track is over")
            }
        }

        try {
            player = MIDIPLayer(FILEPATH)
            player.addMIDIListener(midiListener)
            player.startReader()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()
        g.drawSchoolLogoUpperRight()


        /**
         * Each message corresponds to a MIDI event, so let's get them
         * and display something everytime there is a NOTE_ON message
         */
        synchronized(currentlyPlayingNotes) {
            for (note in currentlyPlayingNotes) {
                val noteColor = ColorUtils.hsvToColor(0.4f, 1f - 1 * note / 10.0f, 1f)

                g.drawFilledCircle(250f, 250f, 10 + 120 - note * 10f, noteColor)
            }
        }
    }

    override fun onDispose() {
        for (s in notesSamples) {
            s.stop()
            s.dispose()
        }

        player.terminate()
        super.onDispose()
    }
}

fun main(args: Array<String>) {
  DemoMIDIPlayer()
}
