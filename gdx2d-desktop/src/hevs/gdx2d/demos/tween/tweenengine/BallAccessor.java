package hevs.gdx2d.demos.tween.tweenengine;

import hevs.gdx2d.demos.tween.Ball;
import aurelienribon.tweenengine.TweenAccessor;

/**
 * An accessor class for the modifying the parameters of the ball.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
class BallAccessor implements TweenAccessor<Ball> {

	public static final int POSITION_X = 1;
	public static final int POSITION_Y = 2;
	public static final int POSITION_XY = 3;

	public int getValues(Ball target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case POSITION_X:
			returnValues[0] = target.posx;
			return 1;
		case POSITION_Y:
			returnValues[0] = target.posy;
			return 1;
		case POSITION_XY:
			returnValues[0] = target.posx;
			returnValues[1] = target.posy;
			return 2;
		default:
			assert false;
			return -1;
		}
	}

	public void setValues(Ball target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case POSITION_X:
			target.posx = newValues[0];
			break;
		case POSITION_Y:
			target.posy = newValues[0];
			break;
		case POSITION_XY:
			target.posx = newValues[0];
			target.posy = newValues[1];
			break;
		default:
			assert false;
			break;
		}
	}

}