package ch.hevs.gdx2d.demos.menus.screens.example_screens;

import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Pierre-André Mudry on 07.05.2015.
 * HES-SO Valais, 2015
 */
public class CreditsScreen extends RenderingScreen {
	@Override
	public void onInit() {}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Color.DARK_GRAY);
		g.drawStringCentered(g.getScreenHeight() / 2, "3 - Ending screen");
	}

	@Override
	public void dispose() {
		PhysicsWorld.dispose();
		super.dispose();
	}
}
