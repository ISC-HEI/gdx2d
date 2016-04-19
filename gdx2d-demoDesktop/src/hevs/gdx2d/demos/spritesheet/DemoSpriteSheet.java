package hevs.gdx2d.demos.spritesheet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import hevs.gdx2d.components.bitmaps.Spritesheet;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.utils.Logger;

/**
 * This demo demonstrates how to load a spritesheet
 * and display its content as a simple animation.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSpriteSheet extends PortableApplication {

	/**
	 * The size of each sprite in the sheet
	 */
	final int SPRITE_WIDTH = 64;
	final int SPRITE_HEIGHT = 64;
	final double FRAME_TIME = 0.15; // Duration of each frime
	Spritesheet ss;
	/**
	 * The currently selected sprite for animation
	 */
	int textureX = 0;
	int textureY = 1;

	/**
	 * Animation related parameters
	 */
	float dt = 0;
	int currentFrame = 0;
	int nFrames = 4;

	public static void main(String args[]) {
		new DemoSpriteSheet();
	}

	@Override
	public void onInit() {
		setTitle("SpriteSheet demo");

		ss = new Spritesheet("data/images/lumberjack_sheet.png", SPRITE_WIDTH, SPRITE_HEIGHT);
		Logger.log("Press up/down to change the current animation");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Color.LIGHT_GRAY);
		g.drawFPS();

		dt += Gdx.graphics.getDeltaTime();

		// Do we have to display the next frame
		if (dt > FRAME_TIME) {
			dt = 0;
			currentFrame = (currentFrame + 1) % nFrames;
		}

		// Display the current image of the animation
		g.draw(ss.sprites[textureY][currentFrame],
				this.getWindowWidth() / 2 - SPRITE_WIDTH / 2,
				this.getWindowHeight() / 2 - SPRITE_HEIGHT / 2);

		g.drawSchoolLogo();

	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);

		switch (keycode) {

			case Input.Keys.DOWN:
				textureY = (textureY + 1) % ss.sprites.length;
				break;

			case Input.Keys.UP:
				textureY = (textureY - 1) < 0 ? ss.sprites.length - 1 : textureY - 1;
				break;

			default:
				break;
		}
	}

}

