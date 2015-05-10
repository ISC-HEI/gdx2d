package hevs.gdx2d.demos.menus.screens;

import com.badlogic.gdx.Input;
import hevs.gdx2d.demos.menus.screens.example_screens.CreditsScreen;
import hevs.gdx2d.demos.menus.screens.example_screens.PhysicsScreen;
import hevs.gdx2d.demos.menus.screens.example_screens.SplashScreen;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.ScreenManager;
import hevs.gdx2d.lib.utils.Logger;

public class DemoScreen extends PortableApplication {
    ScreenManager s;

    @Override
    public void onInit() {
        this.setTitle("Multiple screens and transitions");
        Logger.log("Press enter to activate next screen, 1/2/3 to transition to them");
        s = new ScreenManager();
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
        super.onClick(x, y, button);

        // Delegate the click to the child class
        s.getActiveScreen().onClick(x, y, button);
    }

    @Override
    public void onKeyDown(int keycode) {
        super.onKeyDown(keycode);

        if(keycode == Input.Keys.ENTER)
            s.activateNextScreen();

        if(keycode == Input.Keys.NUM_1)
            s.sliceTransitionToNext();

        if(keycode == Input.Keys.NUM_2)
            s.slideTransitionToNext();

        if(keycode == Input.Keys.NUM_3) {
            s.smoothTransitionToNext();
        }
    }

    public static void main(String[] args) {
        new DemoScreen();
    }
}
