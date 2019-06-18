package ch.hevs.gdx2d.demos.music.midiplayer.MIDIPlayer

import javax.sound.midi.*

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle

import java.io.IOException
import java.util.ArrayList
import kotlin.experimental.and

/**
 * A MIDI file loader and player
 *
 * @author Mikael Follonier (fom)
 * @version 1.11
 */
class MIDIPLayer
/**
 * @param s The file we want to play
 */
(s: String) {

    private var totalticks: Long = 0
    private var tickCount: Long = 0
    private var key = 0
    private var octave = 0
    private var bpm: Float = 0.toFloat()
    /**
     * @return The resolution of the MIDI track, in msec
     */
    var midiTimeResolution: Float = 0.toFloat()
        private set

    private val listeners: ArrayList<MIDIListener>
    private var event: MidiEvent? = null
    private var message: javax.sound.midi.MidiMessage? = null
    private var sequence: Sequence? = null
    private var sm: ShortMessage? = null

    private var last_tick: Long = 0
    private var current_tick: Long = 0
    private var running: Boolean = false

    init {
        val handle = Gdx.files.internal(s)

        try {
            sequence = MidiSystem.getSequence(handle.read())
        } catch (e: InvalidMidiDataException) {
            e.printStackTrace()
        } catch (e: IOException) {
            System.err.println("Could not open file $handle")
            e.printStackTrace()
        }

        listeners = ArrayList()
    }

    /**
     * Start playing a MIDI tune in its own thread
     */
    fun startReader() {
        running = true

        val thread = Thread(Runnable {
            while (running) {
                val messages = ArrayList<MIDIMessage>()

                for (track in sequence!!.tracks) {
                    if (!running) {
                        break
                    }

                    totalticks = sequence!!.microsecondLength

                    for (i in 0 until track.size()) {
                        if (!running) {
                            break
                        }
                        tickCount++
                        event = track.get(i)
                        message = event!!.message

                        if (message is ShortMessage) {
                            sm = message as ShortMessage?
                            key = sm!!.data1
                            octave = key / 12 - 1
                            if (sm!!.command == NOTE_ON || sm!!.command == NOTE_OFF) {
                                last_tick = current_tick
                                current_tick = event!!.tick
                                if (current_tick == last_tick) {
                                    messages.add(MIDIMessage(event!!.tick, sm!!.channel, sm!!.command, key, octave))
                                } else {
                                    messages.clear()
                                    messages.add(MIDIMessage(event!!.tick, sm!!.channel, sm!!.command, key, octave))
                                }
                            }
                        }

                        if (message is MetaMessage) {
                            val mm = message as MetaMessage?
                            if (tickCount >= totalticks) {
                                terminate()
                                kill()
                            }
                            if (mm!!.type == 0x51) {
                                bpm = 60000000f / (mm.message[3] * Math.pow(2.0, 16.0) + (mm.message[4] and 0xff.toByte()) * Math.pow(2.0, 8.0) + mm.message[5].toDouble()).toLong()
                                midiTimeResolution = 60000f / (sequence!!.resolution * bpm)
                            }
                        }

                        try {
                            if ((current_tick - last_tick).toFloat() < 0 || !running) {
                                running = false
                                kill()
                                break
                            } else {
                                notifyListeners(messages)
                                tickCount += Math.round(midiTimeResolution * (current_tick - last_tick).toFloat()).toLong()
                                Thread.sleep(Math.round(midiTimeResolution * (current_tick - last_tick).toFloat()).toLong())
                            }
                        } catch (ie: InterruptedException) {
                            terminate()
                        }

                    }
                }
            }
        })

        thread.start()
    }

    /**
     * Used to register your listener which will be informed of events
     * such as notes
     * @param im
     */
    fun addMIDIListener(im: MIDIListener) {
        listeners.add(im)
    }

    /**
     * Indicates that we want to stop playing MIDI
     */
    fun terminate() {
        running = false
    }

    private fun notifyListeners(msg: ArrayList<MIDIMessage>) {
        for (l in listeners) {
            l.onMIDIMessage(msg)
        }
    }

    private fun kill() {
        for (l in listeners) {
            l.onTrackFinished()
        }
    }

    companion object {
        val NOTE_ON = 0x90
        val NOTE_OFF = 0x80
    }
}
