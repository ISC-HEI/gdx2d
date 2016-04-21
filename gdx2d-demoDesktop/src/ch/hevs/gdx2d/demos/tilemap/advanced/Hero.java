package ch.hevs.gdx2d.demos.tilemap.advanced;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.bitmaps.Spritesheet;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * Character for the demo.
 *
 * @author Alain Woeffray (woa)
 * @author Pierre-André Mudry (mui)
 */
public class Hero implements DrawableObject{

    public enum Direction{
        UP,
        DOWN,
        RIGHT,
        LEFT,
        NULL
    }

    /**
     * The currently selected sprite for animation
     */
    int textureX = 0;
    int textureY = 1;
    float speed = 1;

    float dt = 0;
    int currentFrame = 0;
    int nFrames = 4;
    private final static int SPRITE_WIDTH = 32;
    private final static int SPRITE_HEIGHT = 32;
    final float FRAME_TIME = 0.1f; // Duration of each frime
    Spritesheet ss;

    Vector2 lastPosition;
    Vector2 newPosition;
    Vector2 position;

    final BitmapImage img = new BitmapImage("data/images/pipe.png");


    private boolean move = false;

    /**
     * Create the hero at the start position (0,0)
     */
    public Hero(){
        this(new Vector2(0,0));
    }

    /**
     * Create the hero at the given start tile.
     * @param x Column
     * @param y Line
     */
    public Hero(int x, int y){
        this(new Vector2(SPRITE_WIDTH * x, SPRITE_HEIGHT * y));
    }

    /**
     * Create the hero at the start position
     * @param initialPosition Start position [px] on the map.
     */
    public Hero(Vector2 initialPosition) {

        lastPosition = new Vector2(initialPosition);
        newPosition = new Vector2(initialPosition);
        position = new Vector2(initialPosition);

        ss = new Spritesheet("data/images/lumberjack_sheet32.png", SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    /**
     * @return the current position of the hero on the map.
     */
    public Vector2 getPosition(){
        return this.position;
    }

    /**
     * Update the position and the texture of the hero.
     * @param elapsedTime The time [s] elapsed since the last time which this method was called.
     */
    public void animate(double elapsedTime) {
        float frameTime = FRAME_TIME / speed;

        position = new Vector2(lastPosition);
        if(isMoving()) {
            dt += elapsedTime;
            float alpha = (dt+frameTime*currentFrame)/(frameTime*nFrames);

            position.interpolate(newPosition, alpha,Interpolation.linear);
        }else{
            dt = 0;
        }

        if (dt > frameTime) {
            dt -= frameTime;
            currentFrame = (currentFrame + 1) % nFrames;

            if(currentFrame == 0){
                move = false;
                lastPosition = new Vector2(newPosition);
                position = new Vector2(newPosition);
            }
        }
    }

    /**
     * @return True if the hero is actually doing a step.
     */
    public boolean isMoving(){
        return move;
    }

    /**
     * @param speed The new speed of the hero.
     */
    public void setSpeed(float speed){
        this.speed = speed;
    }

    /**
     * Do a step on the given direction
     * @param direction The direction to go.
     */
    public void go(Direction direction){
        move = true;
        switch(direction){
            case RIGHT:
                newPosition.add(SPRITE_WIDTH, 0);
                break;
            case LEFT:
                newPosition.add(-SPRITE_WIDTH, 0);
                break;
            case UP:
                newPosition.add(0, SPRITE_HEIGHT);
                break;
            case DOWN:
                newPosition.add(0, -SPRITE_HEIGHT);
                break;
            default:
                break;
        }

        turn(direction);
    }

    /**
     * Turn the hero on the given direction without do any step.
     * @param direction The direction to turn.
     */
    public void turn(Direction direction){
        switch(direction){
            case RIGHT:
                textureY = 2;
                break;
            case LEFT:
                textureY = 1;
                break;
            case UP:
                textureY = 3;
                break;
            case DOWN:
                textureY = 0;
                break;
            default:
                break;
        }
    }

    /**
     * Draw the character on the graphic object.
     * @param g Graphic object.
     */
    public void draw(GdxGraphics g) {
        g.draw(ss.sprites[textureY][currentFrame], position.x, position.y);
    }
}
