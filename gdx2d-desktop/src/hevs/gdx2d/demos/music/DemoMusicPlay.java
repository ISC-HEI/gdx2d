package hevs.gdx2d.demos.music;

import hevs.gdx2d.components.audio.MusicPlayer;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.interfaces.AndroidResolver;

import com.badlogic.gdx.graphics.Color;

/**
 * Shows how to play music in the framework
 * 
 * TODO: add short sample play to demonstrate the difference
 * 
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 1.2
 */
public class DemoMusicPlay extends PortableApplication {

	private MusicPlayer f;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clear the screen
		g.clear();
		g.setColor(Color.WHITE);

		// Audio logic
		String text;
		if (f.isPlaying())
			text = "Playing song";
		else
			text = "Click to play";

		g.drawString(20, getWindowHeight() / 2, text);

		// Draws the school logo
		g.drawSchoolLogo();
	}

	/**
	 * Called when the class is terminated
	 */
	@Override
	public void onDispose() {
		super.onDispose();

		// We must release all the resources we got
		f.dispose();
	}

	@Override
	public void onInit() {
		setTitle("Music player, mui 2013");

		// Load the MP3 sound file
		f = new MusicPlayer("data/music/Blues-Loop.mp3");
	}

	@Override
	public void onClick(int x, int y, int button) {
		if (f.isPlaying())
			f.stop();
		else
			f.loop();

		if (onAndroid) {
			// Display Toast on Android
			final String sToast = (f.isPlaying()) ? "Playing started."
					: "Playing stopped.";
			getAndroidResolver()
					.showToast(sToast, AndroidResolver.LENGTH_SHORT);
		}
	}

	public DemoMusicPlay(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String[] args) {
		new DemoMusicPlay(false);
	}
}
