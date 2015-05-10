package hevs.gdx2d.components.screen_management.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by pmudr_000 on 07.05.2015.
 * HES-SO Valais, 2015
 */
public interface ScreenTransition {
    public float getDuration();

    public void render(SpriteBatch batch, Texture currScreen, Texture
            nextScreen, float alpha);
}