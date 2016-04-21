package ch.hevs.gdx2d.demos.menus.screens;

import com.badlogic.gdx.Input;
import ch.hevs.gdx2d.demos.menus.screens.example_screens.CreditsScreen;
import ch.hevs.gdx2d.demos.menus.screens.example_screens.PhysicsScreen;
import ch.hevs.gdx2d.demos.menus.screens.example_screens.SplashScreen;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.ScreenManager;
import hevs.gdx2d.lib.ScreenManager.TransactionType;
import hevs.gdx2d.lib.utils.Logger;

/**
 * Show how to add multiple screen and switch between them with different transitions.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 */
public class DemoScreen extends PortableApplication {

    private ScreenManager s = new ScreenManager();
    private int transactionTypeId;

    @Override
    public void onInit() {
        setTitle("Multiple screens and transitions");
        Logger.log("Press enter/space to show the next screen, 1/2/3 to transition to them");
        s.registerScreen(SplashScreen.class);
        s.registerScreen(PhysicsScreen.class);
        s.registerScreen(CreditsScreen.class);
    }

    @Override
    public void onGraphicRender(GdxGraphics g) {
        s.render(g);
    }

    @Override
    public void onClick(int x, int y, int button) {
        // Delegate the click to the child class
        s.getActiveScreen().onClick(x, y, button);
    }

    @Override
    public void onKeyDown(int keycode) {
        super.onKeyDown(keycode);

        // Display the next screen without transition
        if (keycode == Input.Keys.ENTER)
            s.activateNextScreen();

        // Switch to next screen using all available transitions effects
        if (keycode == Input.Keys.SPACE) {
            s.transitionToNext(TransactionType.values()[transactionTypeId]);

            // Switch to the next transition effect
            transactionTypeId = (transactionTypeId + 1) % TransactionType.values().length;
        }

        if (keycode == Input.Keys.NUM_1)
            s.transitionTo(0, TransactionType.SLICE); // s.activateScreen(0);

        if (keycode == Input.Keys.NUM_2)
            s.transitionTo(1, TransactionType.SLIDE); // s.activateScreen(1);

        if (keycode == Input.Keys.NUM_3)
            s.transitionTo(2, TransactionType.SMOOTH); // s.activateScreen(2);
    }

    public static void main(String[] args) {
        new DemoScreen();
    }
}
