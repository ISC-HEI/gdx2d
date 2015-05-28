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
 * Handles multiple rendering screens and enables transitions between those screens.
 * <br>
 * You can choose the screen to display using its index or automatically switch to the next screen.
 * If the screen index to display is out of range, the first or last screen is shown.
 * <br>
 * If a transition is already in progress or if the selected screen is already active, the transition is ignored.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class ScreenManager {
	protected ArrayList<Class> screens = new ArrayList<Class>();
	protected int activeScreen = 0;
	protected RenderingScreen currScreen, nextScreen;

	/**
	 * Available screen transitions effects.
	 */
	public enum TransactionType {
		SMOOTH, SLIDE, SLICE
	}

	protected TransactionType transitionStyle = TransactionType.SMOOTH;

	// To handle transitions
	protected FrameBuffer currFbo, nextFbo;
	protected SpriteBatch batch;
	protected float time;
	protected ScreenTransition screenTransition;

	protected boolean transitioning = false;
	protected int nextScreenIdx = -1;

	protected RenderingScreen createInstance(Class c) {
		try {
			RenderingScreen s = (RenderingScreen) c.newInstance();
			s.onInit();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Render the current screen to the GdxGraphics
	 *
	 * @param g The instance of GdxGraphics to draw on
	 */
	public void render(GdxGraphics g) {
		// Normal case, no transition
		if (!transitioning) {
			currScreen.render(g);
		} else {
			// Called at the beginning of the transition
			if (screenTransition == null) {
				int w = g.getScreenWidth();
				int h = g.getScreenHeight();
				currFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
				nextFbo = new FrameBuffer(Pixmap.Format.RGB888, w, h, false);
				batch = new SpriteBatch();

				switch (transitionStyle) {
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

				nextScreen = createInstance(screens.get(getNextScreenIndex()));

				// Render next screen to FBO
				nextFbo.begin();
				nextScreen.render(g);
				nextFbo.end();
			}

			// Do the transition
			transition(Gdx.graphics.getDeltaTime());
		}
	}

	// Do the actual transition between the screens
	private void transition(float deltaTime) {
		float duration = screenTransition.getDuration();

		// Update the progress of ongoing transition
		time = Math.min(time + deltaTime, duration);

		// When transition is over
		if (screenTransition == null || time >= duration) {
			screenTransition = null;
			transitioning = false;
			activeScreen = getNextScreenIndex();
			currScreen = nextScreen;
			nextScreen = null;
			time = 0;
			return;
		}

		// Render the transition effect to screen
		float alpha = time / duration;
		screenTransition.render(batch,
				currFbo.getColorBufferTexture(),
				nextFbo.getColorBufferTexture(),
				alpha);
	}

	// Go to the next screen if no target index has been choose
	private int getNextScreenIndex() {
		if (nextScreenIdx < 0)
			return (activeScreen + 1) % screens.size();

		return nextScreenIdx;
	}

	// Check if the screen index is out of bound or not
	private int checkIndexBounds(int screenIndex) {
		if (screenIndex < 0)
			return 0;
		if (screenIndex > screens.size() - 1)
			return screens.size() - 1;

		return screenIndex;
	}

	/**
	 * Add a screen to the screen list
	 *
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
			nextScreenIdx = -1;
			activeScreen = getNextScreenIndex();
			currScreen = createInstance(screens.get(activeScreen));
		}
	}

	/**
	 * Show the corresponding screen without transition.
	 *
	 * @param nextScreen the screen index to display
	 */
	public void activateScreen(int nextScreen) {
		if (activeScreen == nextScreen)
			return;

		if (!transitioning) {
			// Release resources from old screen
			currScreen.dispose();
			nextScreenIdx = -1;
			activeScreen = checkIndexBounds(nextScreen);
			currScreen = createInstance(screens.get(activeScreen));
		}
	}

	/**
	 * Called to transition to the next screen with the corresponding effect.
	 *
	 * @param effect the screen transition effect to use
	 */
	public void transitionToNext(TransactionType effect) {
		if (!transitioning) {
			transitioning = true;
			transitionStyle = effect;
		}
	}

	/**
	 * Called to transition to the specified screen with the corresponding effect.
	 *
	 * @param nextScreen the screen index to display
	 * @param effect     the screen transition effect to use
	 */
	public void transitionTo(int nextScreen, TransactionType effect) {
		if (activeScreen == nextScreen)
			return;

		if (!transitioning) {
			nextScreenIdx = checkIndexBounds(nextScreen);
			transitioning = true;
			transitionStyle = effect;
		}
	}

	/**
	 * Called to transition to the next screen with slicing.
	 * Use the {@link hevs.gdx2d.lib.ScreenManager.TransactionType#SLICE} effect.
	 *
	 * @see #transitionToNext(TransactionType)
	 */
	public void sliceTransitionToNext() {
		transitionToNext(TransactionType.SLICE);
	}


	/**
	 * Called to transition to the next screen with sliding.
	 * Use the {@link hevs.gdx2d.lib.ScreenManager.TransactionType#SLIDE} effect.
	 *
	 * @see #transitionToNext(TransactionType)
	 */
	public void slideTransitionToNext() {
		transitionToNext(TransactionType.SLIDE);
	}

	/**
	 * Called to transition to the next screen with fade-out.
	 * Use the {@link hevs.gdx2d.lib.ScreenManager.TransactionType#SMOOTH} effect.
	 *
	 * @see #transitionToNext(TransactionType)
	 */
	public void smoothTransitionToNext() {
		transitionToNext(TransactionType.SMOOTH);
	}

	/**
	 * @return the active screen which is currently rendered or {@code null} is a transition is in progress
	 */
	public RenderingScreen getActiveScreen() {
		return !transitioning ? currScreen : null;
	}
}