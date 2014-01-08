package hevs.gdx2d.demos.tween.tweenengine;

import hevs.gdx2d.demos.tween.Ball;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;

/**
 * Demonstrates the usage of tweening for
 * animation.
 * 
 * @author Pierre-André Mudry (mui)
 * @version 1.1
 *
 */
public class DemoTween extends PortableApplication {
	final TweenManager tm = new TweenManager();	
	Ball ball1, ball2, ball3;
	
	int height, width;
	
	@Override
	public void onInit() {
		setTitle("Tweening animation parameters, mui 2013");
		
		// The class that makes the animation
		Tween.registerAccessor(Ball.class, new BallAccessor());		
		
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		
		int first =  height / 4;
		int second = 2 * first;
		int third = 3 * first;
		
		ball1 = new Ball(width / 10, first);		
		ball2 = new Ball(width / 10, second);
		ball3 = new Ball(width / 10, third);
						
		// Make the ball move following different curves
		Tween.to(ball1, BallAccessor.POSITION_XY, 1.5f)
	    	.target(width - width / 10, first).ease(aurelienribon.tweenengine.equations.Linear.INOUT).repeatYoyo(-1, 0.3f).start(tm);
		
		Tween.to(ball2, BallAccessor.POSITION_XY, 1.5f)
    		.target(width - width / 10, second).ease(aurelienribon.tweenengine.equations.Sine.INOUT).repeatYoyo(-1, 0.3f).start(tm);
		
		Tween.to(ball3, BallAccessor.POSITION_XY, 1.5f)
    		.target(width - width / 10, third).ease(aurelienribon.tweenengine.equations.Elastic.INOUT).repeatYoyo(-1, 0.3f).start(tm);
		
		Tween.ensurePoolCapacity(10);
	}
	
	
	@Override
	public void onGraphicRender(GdxGraphics g) {		
		g.clear();										

		ball1.draw(g);
		ball2.draw(g);
		ball3.draw(g);				
		
		g.drawFPS();
		g.drawSchoolLogo();
		
		tm.update(Gdx.graphics.getDeltaTime());
	}	
	
	@Override
	public void onDispose() {
		super.onDispose();
		tm.killAll();
	}
	
	public DemoTween(boolean onAndroid){
		super(onAndroid);
	}
	
	public static void main(String args[]){
		new DemoTween(false);
	}
}
