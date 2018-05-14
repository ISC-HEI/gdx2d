package ch.hevs.gdx2d.components.audio;

import ch.hevs.gdx2d.lib.utils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * A class to play <em>short</em> samples within gdx2d. The music sample
 * must be short because it is completely loaded into memory. The counterpart
 * is that you can play many many samples at the same time and modify their pitch
 * and volume dynamically (see {@link #setPitch(float)} and {@link #setVolume(float)}).
 * <p/>
 * Note that {@link SoundSample} differs from {@link MusicPlayer}
 * in the sense that a sample is completely loaded into memory before
 * playing. Thus, it makes sense to play short samples but not complete
 * songs (for background music for instance).
 *
 * @author Pierre-André Mudry (mui)
 * @author Nils Chatton (chn)
 * @version 1.4
 */
public class SoundSample implements Disposable {
	protected Sound s;
	protected float volume = 1.0f;
	protected float pitch = 1.0f;

	/**
	 * Create a sound sample from a file. Gdx must be loaded.
	 *
	 * @param file the file to be loaded, using internal path representation
	 * @throws GdxRuntimeException if Gdx is not loaded
	 */
	public SoundSample(String file) {
		Utils.assertGdxLoaded(SoundSample.class);
		s = Gdx.audio.newSound(Gdx.files.internal(file));
	}

	/**
	 * Change volume of the player.
	 *
	 * @param v the new volume (should be between 0 and 1 inclusive)
	 * @throws UnsupportedOperationException if the new volume value is out of range
	 */
	public void setVolume(float v) {
		if (v > 1.0f || v < 0.0f) {
			throw new UnsupportedOperationException("Volume must be set in a range between 0 and 1");
		} else {
			volume = v;
		}
	}

	/**
	 * Changes the speed (pitch) at which the sound is played.
	 * Valid values are between 0.5 (twice slower) and 2 (twice faster) (inslusive).
	 *
	 * @param p the speed (pitch) of the sound
	 * @throws UnsupportedOperationException if the new volume value is out of range
	 */
	public void setPitch(float p) {
		if (p > 2.0f || p < 0.5f) {
			throw new UnsupportedOperationException("Pitch must be set in a range between 0.5 and 2");
		}

		pitch = p;
	}

	/**
	 * Changes the pitch of a currently playing sound, specified by its
	 * id (which is returned by the {@link #play()} method). If the sound
	 * is no longer playing, nothing happens
	 *
	 * @param newPitch See {@link #setPitch(float)}
	 * @param id       See {@link #play()}
	 */
	public void modifyPitch(float newPitch, long id) {
		s.setPitch(id, newPitch);
	}

	/**
	 * Changes the volume of a currently playing sound, specified by its
	 * id (which is returned by the {@link #play()} method). If the sound
	 * is no longer playing, nothing happens
	 *
	 * @param newVolume See {@link #setVolume(float)}
	 * @param id        See {@link #play()}
	 */
	public void mofidyPlayingVolument(float newVolume, long id) {
		s.setVolume(id, newVolume);
	}

	/**
	 * Starts playing the sample at the current {@link #pitch} and {@link #volume}. If it is already playing, another
	 * sample will start, concurrently.
	 *
	 * @return The id of the currently playing sample. Can be used to change pitch and volume while playing
	 */
	public long play() {
		return s.play(volume, pitch, 0);
	}

	/**
	 * Stops playing the sample
	 */
	public void stop() {
		s.stop();
	}

	/**
	 * Plays the song in loop (repeat forever), using the
	 * current {@link #volume} and {@link #pitch}.
	 *
	 * @return The id of the currently playing sample. Can be used to change pitch and volume while playing
	 */
	public long loop() {
		return s.loop(volume, pitch, 0);
	}

	/**
	 * Release resources when done working with them
	 */
	@Override
	public void dispose() {
		s.dispose();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
}
