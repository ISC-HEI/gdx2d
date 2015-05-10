package hevs.gdx2d.lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import hevs.gdx2d.components.screen_management.RenderingScreen;
import hevs.gdx2d.components.screen_management.transitions.ScreenTransition;
import hevs.gdx2d.components.screen_management.transitions.SliceTransition;
import hevs.gdx2d.components.screen_management.transitions.SlideTransition;
import hevs.gdx2d.components.screen_management.transitions.SmoothTransition;

import java.util.ArrayList;

/**
 * Handles multiple rendering screens and enables
 * transitions between those screens.
 * Pierre-André Mudry 2015
 */
public class ScreenManager {
	ArrayList<Class> screens = new ArrayList<Class>();
	protected int activeScreen = 0;
	protected RenderingScreen currScreen, nextScreen;
	protected enum transition_t {SMOOTH, SLIDE, SLICE};
	protected transition_t transitionStyle = transition_t.SMOOTH;

	// To handle transitions
	protected FrameBuffer currFbo, nextFbo;
	protected SpriteBatch batch;
	protected float time;
	protected ScreenTransition screenTransition;
	protected boolean transitioning = false;

	protected  RenderingScreen createInstance(Class c) {
		try {
			RenderingScreen s = (RenderingScreen) c.newInstance();
			s.onInit();
			return s;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Render the current screen to the GdxGraphics
	 * @param g The instance of GdxGraphics to draw on
	 */
	public void render(GdxGraphics g) {
		// Normal case, no transition
		if (!transitioning) {
			currScreen.render(g);
		} else {
			// Rendering the transition
			if (transitioning) {
				// Called at the beginning of the transition
				if (screenTransition == null) {
					int w = g.getScreenWidth();
					int h = g.getScreenHeight();
					currFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
					nextFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
					batch = new SpriteBatch();

					switch(transitionStyle){
						case SLICE:
							screenTransition = SliceTransition.init(0.75f,
												SliceTransition.UP_DOWN,
												10,
												Interpolation.pow4Out);
							break;
						case SLIDE:
							screenTransition = SlideTransition.init(0.75f,
												SlideTransition.LEFT,
												false,
												Interpolation.bounceOut);
							break;
						case SMOOTH:
							screenTransition = SmoothTransition.init(0.75f);
							break;
					}

					// Render current screen to FBO
					currFbo.begin();
						currScreen.render(g);
					currFbo.end();

					// Releasing resources from the current screen
					currScreen.dispose();
					currScreen = null;

					nextScreen = createInstance(screens.get((activeScreen + 1) % screens.size()));

					// Render next screen to FBO
					nextFbo.begin();
						nextScreen.render(g);
					nextFbo.end();
				}

				// Do the transition
				transition(Gdx.graphics.getDeltaTime(), g);
			}
		}
	}

	/**
	 * Do the actual transition between the screens
	 * @param deltaTime
	 * @param g
	 */
	private void transition(float deltaTime, GdxGraphics g) {
		float duration = screenTransition.getDuration();

		// Update the progress of ongoing transition
		time = Math.min(time + deltaTime, duration);

		// When transition is over
		if (screenTransition == null || time >= duration) {
			screenTransition = null;
			transitioning = false;
			activeScreen = (activeScreen + 1) % screens.size();
			currScreen = nextScreen;
			nextScreen = null;
			time = 0;
			return;
		}

		// Rnender the transition effect to screen
		float alpha = time / duration;
		screenTransition.render(batch,
				currFbo.getColorBufferTexture(),
				nextFbo.getColorBufferTexture(),
				alpha);
	}

	/**
	 * Add a screen to the screen list
	 * @param s The class of the screen to add
	 */
	public void registerScreen(Class s) {
		screens.add(s);

		if (currScreen == null) {
			currScreen = createInstance(screens.get(0));
		}
	}

	/**
	 * Called to activate the next screen without transition
	 */
	public void activateNextScreen() {
		if (!transitioning) {
			// Release resources from old screen
			currScreen.dispose();
			activeScreen = (activeScreen + 1) % screens.size();
			currScreen = createInstance(screens.get(activeScreen));
		}
	}

	/**
	 * Called to transition to the next screen with slicing
	 */
	public void sliceTransitionToNext() {
		if (!transitioning) {
			transitioning = true;
			transitionStyle = transition_t.SLICE;
		}
	}


	/**
	 * Called to transition to the next screen with sliding
	 */
	public void slideTransitionToNext() {
		if (!transitioning) {
			transitioning = true;
			transitionStyle = transition_t.SLIDE;
		}
	}

	/**
	 * Called to transition to the next screen with fade-out
	 */
	public void smoothTransitionToNext() {
		if (!transitioning) {
			transitioning = true;
			transitionStyle = transition_t.SMOOTH;
		}
	}

	/**
	 * @return The active screen which is currently rendered
	 */
	public RenderingScreen getActiveScreen() {
		if (transitioning == false)
			return currScreen;
		else
			return null;
	}
}