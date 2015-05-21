package hevs.gdx2d.demos.music.midiplayer;

import com.badlogic.gdx.graphics.Color;
import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.components.colors.ColorUtils;
import hevs.gdx2d.demos.music.midiplayer.MIDIPlayer.MIDIListener;
import hevs.gdx2d.demos.music.midiplayer.MIDIPlayer.MIDIMessage;
import hevs.gdx2d.demos.music.midiplayer.MIDIPlayer.MIDIPLayer;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A MIDI sampler demo
 *
 * @author Mikael Follonier (fom)
 * @version 1.2
 */
public class DemoMIDIPlayer extends PortableApplication {
	// Where the music and samples are stored
	protected final int nSamples = 104;
	protected final String DATADIR = "data/music/midiplayer/pianonotes/";
	protected final String FILEPATH = "data/music/midiplayer/brahms_lullaby.mid";

	// Storage for the loaded sound samples
	protected ArrayList<SoundSample> notesSamples;

	// Contains the notes that are currently playing
	protected ArrayList<Integer> currentlyPlayingNotes = new ArrayList<Integer>();

	// This is the class that handles the player inputs
	protected MIDIPLayer player;

	// The object that will receive the note messages
	MIDIListener midiListener;

	@Override
	public void onInit() {
		setTitle("MIDI demo, fom 2015");
		notesSamples = new ArrayList<SoundSample>();

		// Read all the piano samples that are in the directory
		for (int i = 0; i < nSamples; i++) {
			notesSamples.add(new SoundSample(DATADIR + String.format("%03d", i) + ".wav"));
		}

		midiListener = new MIDIListener() {
			/**
			 * Handles the received MIDI message and plays the corresponding sample
			 * @param msg msg Message list for a given MIDI tick
			 */
			@Override
			public void onMIDIMessage(ArrayList<MIDIMessage> msg) {
				// Play the notes from the MIDI player
				for (MIDIMessage message : msg) {
					synchronized (currentlyPlayingNotes) {
						if (message.cmd == MIDIPLayer.NOTE_ON) {
							notesSamples.get(message.key).play(); //MIDI notes start at octave -2
							currentlyPlayingNotes.add(message.note);
						}

						if (message.cmd == MIDIPLayer.NOTE_OFF) {
							currentlyPlayingNotes.remove((Integer) message.note);
						}

						// Sort the list
						Collections.sort(currentlyPlayingNotes);
					}
				}
			}

			@Override
			public void onTrackFinished() {
				Logger.log("Track is over");
			}
		};

		try {
			player = new MIDIPLayer(FILEPATH);
			player.addMIDIListener(midiListener);
			player.startReader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onGraphicRender(final GdxGraphics g) {
		g.clear();
		g.drawSchoolLogoUpperRight();
		g.drawStringCentered(getWindowHeight() / 8, "Left click = start, right click = stop");

		/**
		 * Each message corresponds to a MIDI event, so let's get them
		 * and display something everytime there is a NOTE_ON message
		 */
		synchronized (currentlyPlayingNotes) {
			for (Integer note : currentlyPlayingNotes) {
				Color noteColor = ColorUtils.hsvToColor(0.4f, 1f - (1 * note / 10.0f), 1);

				g.drawFilledCircle(250, 250, 10 + 120 - (note * 10f), noteColor);
			}
		}
	}

	@Override
	public void onDispose() {
		for (SoundSample s : notesSamples) {
			s.stop();
			s.dispose();
		}

		player.terminate();
		super.onDispose();
	}

	public static void main(String[] args) {
		new DemoMIDIPlayer();
	}
}
