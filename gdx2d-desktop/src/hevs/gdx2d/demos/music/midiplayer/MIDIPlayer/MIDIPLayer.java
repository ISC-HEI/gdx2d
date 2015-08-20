package hevs.gdx2d.demos.music.midiplayer.MIDIPlayer;

import javax.sound.midi.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A MIDI file loader and player
 *
 * @author Mikael Follonier (fom)
 * @version 1.11
 */
public class MIDIPLayer {
	public static final int NOTE_ON = 0x90;
	public static final int NOTE_OFF = 0x80;

	private long totalticks;
	private long tickCount;
	private int key = 0;
	private int octave = 0;
	private float bpm;
	private float msecRes;

	private ArrayList<MIDIListener> listeners;
	private MidiEvent event;
	private javax.sound.midi.MidiMessage message;
	private Sequence sequence;
	private ShortMessage sm;

	private long last_tick = 0;
	private long current_tick = 0;
	private boolean running;

	/**
	 * @param s The file we want to play
	 */
	public MIDIPLayer(String s) {
		FileHandle handle = Gdx.files.internal(s);			
		
		try {				
			sequence = MidiSystem.getSequence(handle.read());
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not open file " + handle);
			e.printStackTrace();
		}

		listeners = new ArrayList<MIDIListener>();
	}

	/**
	 * Start playing a MIDI tune in its own thread
	 */
	public void startReader() {
		running = true;

		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (running) {
					ArrayList<MIDIMessage> messages = new ArrayList<MIDIMessage>();

					for (Track track : sequence.getTracks()) {
						if (!running) {
							break;
						}

						totalticks = sequence.getMicrosecondLength();

						for (int i = 0; i < track.size(); i++) {
							if (!running) {
								break;
							}
							tickCount++;
							event = track.get(i);
							message = event.getMessage();

							if (message instanceof ShortMessage) {
								sm = (ShortMessage) message;
								key = sm.getData1();
								octave = (key / 12) - 1;
								if (sm.getCommand() == NOTE_ON || sm.getCommand() == NOTE_OFF) {
									last_tick = current_tick;
									current_tick = event.getTick();
									if (current_tick == last_tick) {
										messages.add(new MIDIMessage(event.getTick(), sm.getChannel(), sm.getCommand(), key, octave));
									} else {
										messages.clear();
										messages.add(new MIDIMessage(event.getTick(), sm.getChannel(), sm.getCommand(), key, octave));
									}
								}
							}

							if (message instanceof MetaMessage) {
								MetaMessage mm = (MetaMessage) message;
								if (tickCount >= totalticks) {
									terminate();
									kill();
								}
								if (mm.getType() == 0x51) {
									bpm = 60000000f / (long) (mm.getMessage()[3] * Math.pow(2, 16) + (mm.getMessage()[4] & 0xff) * Math.pow(2, 8) + mm.getMessage()[5]);
									msecRes = (60000f) / (sequence.getResolution() * bpm);
								}
							}

							try {
								if ((float) (current_tick - last_tick) < 0 || !running) {
									running = false;
									kill();
									break;
								} else {
									notifyListeners(messages);
									tickCount+=Math.round((msecRes * (float) (current_tick - last_tick)));
									Thread.sleep(Math.round((msecRes * (float) (current_tick - last_tick))));
								}
							} catch (InterruptedException ie) {
								terminate();
							}
						}
					}
				}
			}
		});

		thread.start();
	}

	/**
	 * @return The resolution of the MIDI track, in msec
	 */
	public float getMIDITimeResolution() {
		return msecRes;
	}

	/**
	 * Used to register your listener which will be informed of events
	 * such as notes
	 * @param im
	 */
	public void addMIDIListener(MIDIListener im) {
		listeners.add(im);
	}

	/**
	 * Indicates that we want to stop playing MIDI
	 */
	public void terminate() {
		running = false;
	}

	private void notifyListeners(ArrayList<MIDIMessage> msg) {
		for (MIDIListener l : listeners) {
			l.onMIDIMessage(msg);
		}
	}

	private void kill() {
		for (MIDIListener l : listeners) {
			l.onTrackFinished();
		}
	}
}