package hevs.gdx2d.demos.physics.pinball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import hevs.gdx2d.components.bitmaps.Spritesheet;
import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.components.physics.PhysicsMotor;
import hevs.gdx2d.components.physics.PhysicsPolygon;
import hevs.gdx2d.components.physics.PhysicsStaticBox;
import hevs.gdx2d.components.physics.PhysicsStaticCircle;
import hevs.gdx2d.components.physics.PhysicsStaticLine;
import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.utils.Logger;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

public class DemoPinball extends PortableApplication {

	World world = PhysicsWorld.getInstance();
	private DebugRenderer debugRenderer;

	public static void main(String[] args) {
		new DemoPinball();
	}

	private PhysicsBox leftFlipper;
	private PhysicsBox rightFlipper;
	private PhysicsMotor leftMotor;
	private PhysicsMotor rightMotor;
	private Ball ball;
	private Vector2 ball_position = new Vector2(51,150);
	private PhysicsStaticLine bumperLeft;
	private PhysicsStaticLine bumperRight;
	private Spritesheet ballSprite;
	private int displayHelp;
	private Renderer renderer;
	
	@Override
	public void onInit() {
		ballSprite = new Spritesheet("data/images/pinball/ball.png", 20, 20);

		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
		setTitle("Pinball, pim 2015");
		Logger.log("Hello");

		world.setGravity(new Vector2(0, -2f));
		new PhysicsScreenBoundaries(h, w * 2);
		debugRenderer = new DebugRenderer();
		renderer = new Renderer();

		Vector2 left_center = new Vector2(50, 50);
		Vector2 right_center = new Vector2(150, 50);

		PhysicsStaticBox leftFrame = new PhysicsStaticBox("left frame", left_center, .1f, .1f);
		PhysicsStaticBox rightFrame = new PhysicsStaticBox("right frame", right_center, .1f, .1f);

		leftFlipper = new PhysicsBox("left flipper",
				new Vector2(left_center).add(25, 0), 30, 5, 4.0f, 0.3f, 0.6f);

		rightFlipper = new PhysicsBox("right flipper",
				new Vector2(right_center).add(-25, 0), 30, 5, 4.0f, 0.3f, 0.6f);

		leftMotor = new PhysicsMotor(leftFrame.getBody(),
				leftFlipper.getBody(), leftFrame.getBody().getWorldCenter());

		rightMotor = new PhysicsMotor(rightFrame.getBody(),
				rightFlipper.getBody(), rightFrame.getBody().getWorldCenter());
		
		leftMotor.setLimits((float) Math.toRadians(-25), (float) Math.toRadians(+20));
		leftMotor.enableLimit(true);
		leftMotor.initializeMotor(50f, 50f, false);

		rightMotor.setLimits((float) Math.toRadians(-20), (float) Math.toRadians(+25));
		rightMotor.enableLimit(true);
		rightMotor.initializeMotor(-50f, 50f, false);
		
		new PhysicsPolygon("out1", new Vector2[] {
				new Vector2(0,0),
				new Vector2(0, left_center.y*2),
				new Vector2(left_center),
				new Vector2(left_center.x, 1),
			} , false);
		new PhysicsPolygon("out2", new Vector2[] {
				new Vector2(right_center.x, 0),
				new Vector2(right_center),
				new Vector2(w, h/2),
				new Vector2(w, 0),
			} , false);
		
		new PhysicsPolygon("bumperLeft_frame", new Vector2[] {
				new Vector2(left_center).add(-20,55),
				new Vector2(left_center).add(-20,95),
				new Vector2(left_center).add(5,55),
			} , false);
		
		bumperLeft = new PhysicsStaticLine("bumperLeft", 
				new Vector2(left_center).add(-19,96),
				new Vector2(left_center).add(4,56), 4f, 2f, .6f) {
			@Override
			public void collision(AbstractPhysicsObject theOtherObject,
					float energy) {
				super.collision(theOtherObject, energy);
				System.out.println("yahoo");
			}
		};

		new PhysicsPolygon("bumperRight_frame", new Vector2[] {
				new Vector2(right_center).add(20,55),
				new Vector2(right_center).add(20,95),
				new Vector2(right_center).add(-5,55),
			} , false);

		bumperRight = new PhysicsStaticLine("bumperRight", 
				new Vector2(right_center).add(19,96),
				new Vector2(right_center).add(-4,56), 4f, 2f, .6f);
		
		ball = new Ball("ball", ball_position, 10f, ballSprite.sprites[0]);
		ball.enableCollisionListener();
		
		new PhysicsStaticCircle("b1", new Vector2(250, 450), 20, 1f, 2f, .6f) {
			@Override
			public void collision(AbstractPhysicsObject theOtherObject, float energy) {
				System.out.println("houla");
			}
		};
		new PhysicsStaticCircle("b2", new Vector2(325, 525), 20, 1f, 2f, .6f);
		new PhysicsStaticCircle("b3", new Vector2(400, 450), 20, 1f, 2f, .6f);
	}

	private void moveCamera(GdxGraphics g, Vector2 p) {
		float h = Gdx.graphics.getHeight();
		//float w = Gdx.graphics.getWidth();
		
		// Set the ball in the upper part of the screen
		float y = p.y - h/2 - h/3;

		// Restrict the camera inside the world
		if (y > h) {
			y = h;
		} 
		if (y < 0) {
			y = 0;
		}

		g.moveCamera(0, y);
	}
	
	@Override
	public void onGraphicRender(GdxGraphics g) {
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();

		g.clear();

		// There is something weird in the debug renderer and the timings
		debugRenderer.render(world, g.getCamera().combined);
		
		PhysicsWorld.updatePhysics();

		moveCamera(g, PhysicsConstants.coordMetersToPixels(ball.getBody().getPosition()));
		//g.zoom(2f);

		g.setBackgroundColor(Color.WHITE);

		g.setColor(new Color(.8f,.8f,.8f,1f));
		for (int x = 0; x < h ; x+= 25) {
			g.drawLine(x, 0, x, 2*h);
		}

		for (int y = 0; y < 2*h ; y+= 25) {
			g.drawLine(0, y, w, y);
		}

		//ball.draw(g);
		renderer.draw(g);
		
		int currentLFFrame = ((int) Math.abs(leftFlipper.getBodyAngleDeg()))%ballSprite.sprites[1].length;
		g.draw(ballSprite.sprites[1][currentLFFrame], leftFlipper.getBodyPosition().x-10, leftFlipper.getBodyPosition().y-10, 10, 10, 20, 20, 2f, .5f, leftFlipper.getBodyAngleDeg());
		int currentRFFrame = ((int) Math.abs(leftFlipper.getBodyAngleDeg()))%ballSprite.sprites[1].length;
		g.draw(ballSprite.sprites[1][currentRFFrame], rightFlipper.getBodyPosition().x-10, rightFlipper.getBodyPosition().y-10, 10, 10, 20, 20, 2f, .5f, rightFlipper.getBodyAngleDeg()+180);
		
		if (displayHelp > 0) {
			displayHelp--;
			g.setColor(new Color(Color.BLACK).mul((float)displayHelp/100));
			g.drawStringCentered(h/2, "CTRL keys : flippers, space : new ball");
		}
		
		g.drawSchoolLogo();
	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		switch (keycode) {
			case Keys.CONTROL_LEFT:
				leftMotor.enableMotor(true);
			break;
			case Keys.CONTROL_RIGHT:
				rightMotor.enableMotor(true);
			break;
			
			case Keys.SPACE:
			break;
			
			default:
				displayHelp = 100;
			break;
		}
	}

	@Override
	public void onKeyUp(int keycode) {
		super.onKeyUp(keycode);
		switch (keycode) {
			case Keys.CONTROL_LEFT:
				leftMotor.enableMotor(false);
			break;
			
			case Keys.CONTROL_RIGHT:
				rightMotor.enableMotor(false);
			break;

			case Keys.SPACE:
				float h = Gdx.graphics.getHeight();
				float w = Gdx.graphics.getWidth();
				ball.destroy();
				ball = new Ball("ball", ball_position, 10f, ballSprite.sprites[0]);
				ball.enableCollisionListener();
			break;
			
			default:
				displayHelp = 100;
			break;
		}
	}
}
