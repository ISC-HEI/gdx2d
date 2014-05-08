package hevs.gdx2d.demos.music;

import static com.badlogic.gdx.graphics.Color.LIGHT_GRAY;
import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import com.badlogic.gdx.Input.Keys;

/**
 * This demonstrates how to play short samples with GDX2d.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSamplesPlay extends PortableApplication {

	public SoundSample s1, s2, s3, s4;

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clear the screen
		g.clear();
		g.setColor(LIGHT_GRAY);

		// Audio logic
		String text = "Press 1/2/3/4 to play different samples";
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
		s1.dispose();
		s2.dispose();
		s3.dispose();
		s4.dispose();
	}

	@Override
	public void onInit() {
		setTitle("Sound samples player, mui 2014");

		// Load the MP3 sound file
		s1 = new SoundSample("data/music/babypianosamples/Honky_C1.wav");
		s2 = new SoundSample("data/music/babypianosamples/Honky_C2.wav");
		s3 = new SoundSample("data/music/babypianosamples/Honky_C3.wav");
		s4 = new SoundSample("data/music/babypianosamples/Honky_C4.wav");
	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		
		if(keycode == Keys.NUM_1)
			s1.play();
		if(keycode == Keys.NUM_2)
			s2.play();
		if(keycode == Keys.NUM_3)
			s3.play();
		if(keycode == Keys.NUM_4)
			s4.play();
		
		if(keycode == Keys.SPACE){
			s1.setPitch(2);
			s2.setPitch(2);
			s3.setPitch(2);
			s4.setPitch(2);
		}
	}
	
	public DemoSamplesPlay(boolean onAndroid) {
		super(onAndroid);
	}

	public static void main(String[] args) {
		new DemoSamplesPlay(false);
	}
}

