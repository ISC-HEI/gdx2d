package hevs.gdx2d.demos.simple;

import com.badlogic.gdx.Gdx;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import com.badlogic.gdx.graphics.Color;

/**
 * A very simple demonstration on how to display something animated with the
 * library
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSimpleAnimation extends PortableApplication {
    int radius = 5, speed = 1;
    int screenHeight, screenWidth;

    public DemoSimpleAnimation(boolean onAndroid) {
        super(onAndroid);
    }

    @Override
    public void onInit() {
        // Sets the window title
        setTitle("Simple demo, mui 2013");

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void onGraphicRender(GdxGraphics g) {

        // Clears the screen
        g.clear();
        g.drawAntiAliasedCircle(screenWidth / 2, screenHeight / 2, radius, Color.BLUE);

        // If reaching max or min size, invert the growing direction
        if (radius >= 100 || radius <= 3) {
            speed *= -1;
        }

        // Modify the radius
        radius += speed;

        g.drawSchoolLogo();
    }

    @Override
    public void onClick(int x, int y, int button) {
        if (onAndroid)
            getAndroidResolver().showAboutBox();
    }

    public static void main(String[] args) {
        /**
         * Note that the constructor parameter is used to determine if running
         * on Android or not. As we are in main there, it means we are on
         * desktop computer.
         */
        new DemoSimpleAnimation(false);
    }

}
