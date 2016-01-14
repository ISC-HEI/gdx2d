package ch.hevs.gdx2d.components.screen_management.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Pierre-André Mudry
 */
public interface ScreenTransition {
    float getDuration();

    void render(SpriteBatch batch, Texture currScreen, Texture nextScreen, float alpha);
}