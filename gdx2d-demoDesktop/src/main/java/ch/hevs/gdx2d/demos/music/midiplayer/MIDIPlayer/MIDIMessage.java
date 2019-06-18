package ch.hevs.gdx2d.demos.music.midiplayer.MIDIPlayer;

/**
 * A simple MIDI message class
 *
 * @author Mikael Follonier (fom)
 * @version 1.0
 */
public class MIDIMessage {
	public long current_time; // MIDI time base
	public int channel; // The current MIDI channel (~ which instrument)
	public int key; // The MIDI note (from 0 to 127)
	public int note; // The corresponding note in the scale (C = 0, C# = 1...)
	public String noteName;
	public int octave; // The number of the octave
	public int cmd; // ON or OFF for the note in the current message

	/**
	 * Warning, MIDI messages don't start with the C on a piano
	 */
	private final int OFFSET = 24;

	private static final String[] NOTE_NAMES =
			{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

	/**
	 *
	 * @param tick MIDI time base
	 * @param channel The current MIDI channel (~ which instrument)
	 * @param command ON or OFF for the note in the current message
	 * @param key The MIDI note (from 0 to 127)
	 * @param octave The number of the octave
	 */
	public MIDIMessage(long tick, int channel, int command, int key, int octave) {
		current_time = tick;
		this.channel = channel;
		cmd = command;
		this.key = key - OFFSET;
		this.octave = octave;
		note = this.key % 12;
		noteName = NOTE_NAMES[note];
	}

	public String toString() {
		return "Current tick : " + current_time + ", Channel : " + channel + ", Command : " +
				"" + cmd + ", Note : " + key + " " + noteName + ", Octave : " + octave;
	}
}
