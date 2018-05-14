package ch.hevs.gdx2d.components.audio;

import ch.hevs.gdx2d.lib.utils.Logger;
import ch.hevs.gdx2d.lib.utils.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * A class to stream music without loading it within gdx2d.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.2
 */
public class MusicPlayer implements Disposable {
	protected Music s;
	protected float volume = 1.0f;

	/**
	 * Create a music player from a sound file. Gdx must be loaded.
	 *
	 * @param file the sound file to be played, using internal path representation
	 * @throws GdxRuntimeException if Gdx is not loaded
	 */
	public MusicPlayer(String file) {
    Utils.assertGdxLoaded(MusicPlayer.class);

		try {
			s = Gdx.audio.newMusic(Gdx.files.internal(file));
		} catch (GdxRuntimeException e) {
			Logger.error("Unable to load the music file '" + file + "'");
			throw e;
		}
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
	 * Start playing the song.
	 */
	public void play() {
		s.play();
	}

	/**
	 * Stop playing the song.
	 */
	public void stop() {
		s.stop();
	}

	/**
	 * @return {@code true} if the music is playing
	 */
	public boolean isPlaying() {
		return s.isPlaying();
	}

	/**
	 * @return {@code true} if the music is currently looping (aka playing repeatedly)
	 */
	public boolean isLooping() {
		return s.isLooping();
	}

	/**
	 * Play the song in loop, repeating forever.
	 */
	public void loop() {
		s.play();
		s.setLooping(true);
	}

	/**
	 * Release resources when done working with them
	 */
	@Override
	public void dispose() {
		s.dispose();
	}

	/**
	 * Called when the object is destroyed
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
}
