package ch.hevs.gdx2d.demos.physics.pinball;

import java.util.LinkedList;

import ch.hevs.gdx2d.components.bitmaps.Spritesheet;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticCircle;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticLine;
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


/**
 * WORK IN PROGRESS, DO NOT USE
 */
public class DemoPinball extends PortableApplication {

	World world = PhysicsWorld.getInstance();
	private DebugRenderer debugRenderer;

	public static void main(String[] args) {
		new DemoPinball();
	}

	private enum draw_mode_e {
		STD, DEBUG, DEBUG_STD
	};

	private draw_mode_e draw_mode = draw_mode_e.DEBUG_STD;

	private class DrawablePhysicsStaticLine extends PhysicsStaticLine implements
    DrawableObject {

		private Vector2 p1;
		private Vector2 p2;

		public DrawablePhysicsStaticLine(String name, Vector2 p1, Vector2 p2,
				float density, float restitution, float friction) {
			super(name, p1, p2, density, restitution, friction);
			this.p1 = p1;
			this.p2 = p2;
		}

		@Override
		public void draw(GdxGraphics g) {
			g.setColor(Color.GRAY);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}

	private class DrawablePhysicsPolygon extends PhysicsPolygon implements DrawableObject {

		public DrawablePhysicsPolygon(String name, Vector2[] vertices, float density, float restitution, float friction, boolean dynamic) {
			super(name, vertices, density, restitution, friction, dynamic);

		}

		public DrawablePhysicsPolygon(String name, Vector2[] vertices,
				boolean dynamic) {
			super(name, vertices, dynamic);
		}

		@Override
		public void draw(GdxGraphics g) {
			g.setColor(Color.GRAY);
			g.drawPolygon(this.getPolygon());
		}
	}

	private class DrawablePhysicsStaticCircle extends PhysicsStaticCircle implements DrawableObject {
		private float radius;

		public DrawablePhysicsStaticCircle(String name, Vector2 position, float radius) {
			super(name, position, radius);
			this.radius = radius;
		}

		public DrawablePhysicsStaticCircle(String name, Vector2 position, float radius, float density, float restitution, float friction) {
			super(name, position, radius, density, restitution, friction);
			this.radius = radius;
		}

		@Override
		public void draw(GdxGraphics g) {
			g.setColor(Color.GRAY);
			g.drawCircle(this.getBodyPosition().x, this.getBodyPosition().y, radius);
		}

	}

	private Flipper leftFlipper;
	private Flipper rightFlipper;
	private Ball ball;
	private Vector2 ball_position = new Vector2(51, 150);
	private PhysicsStaticLine bumperLeft;
	private PhysicsStaticLine bumperRight;
	private Spritesheet ballSprite;
	private int displayHelp = 500;
	private Renderer renderer;
	private LinkedList<TemporaryDrawable> decorations;

	public float scale(float x) {
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();

		return x * Math.min(h, w);
	}

	@Override
	public void onInit() {
		ballSprite = new Spritesheet("data/images/pinball/sprites.png", 20, 20);
		decorations = new LinkedList<TemporaryDrawable>();
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
		setTitle("Pinball, pim 2015");
		Logger.log("Hello");

		world.setGravity(PinballSettings.G);
		new PhysicsScreenBoundaries(h * PinballSettings.PINBALL_SIZE.x, w * PinballSettings.PINBALL_SIZE.y);
		debugRenderer = new DebugRenderer();
		renderer = new Renderer();

		Vector2 left_center = new Vector2(50, 50);
		Vector2 right_center = new Vector2(170, 50);

		leftFlipper = new Flipper("left_flipper", new Vector2(left_center).add(
				3, -3), 50, 10, -25, 50, ballSprite.sprites[1]);
		rightFlipper = new Flipper("right_flipper",
				new Vector2(right_center).add(-3, -3), 50, 10, -180 + 25, -50,
				ballSprite.sprites[1]);

		new DrawablePhysicsPolygon("out1", new Vector2[] { new Vector2(0, 0),
				new Vector2(0, left_center.y * 2), new Vector2(left_center),
				new Vector2(left_center.x, 1), }, false);
		new DrawablePhysicsPolygon("out2", new Vector2[] {
				new Vector2(right_center.x, 0), new Vector2(right_center),
				new Vector2(w, h / 2), new Vector2(w, 0), }, false);
		new DrawablePhysicsPolygon("bumperLeft_frame", new Vector2[] {
				new Vector2(left_center).add(-20, 55),
				new Vector2(left_center).add(-20, 95),
				new Vector2(left_center).add(5, 55), }, false);

		bumperLeft = new DrawablePhysicsStaticLine("bumperLeft", new Vector2(
				left_center).add(-19, 96), new Vector2(left_center).add(4, 56),
				4f, 2f, .6f) {
			@Override
			public void collision(AbstractPhysicsObject theOtherObject,
					float energy) {
				super.collision(theOtherObject, energy);
				System.out.println("yahoo");
			}
		};

		new DrawablePhysicsPolygon("bumperRight_frame", new Vector2[] {
				new Vector2(right_center).add(20, 55),
				new Vector2(right_center).add(20, 95),
				new Vector2(right_center).add(-5, 55), }, false);

		new SensorSpinner("toto", new Vector2(0, left_center.y).add(
				12.5f, 55), 25, 25, ballSprite.sprites[2]);

		bumperRight = new DrawablePhysicsStaticLine("bumperRight", new Vector2(
				right_center).add(19, 96),
				new Vector2(right_center).add(-4, 56), 4f, 2f, .6f);

		ball = new Ball("ball", ball_position, scale(PinballSettings.BALL_DIAMETER/2), ballSprite.sprites[0]);
		ball.enableCollisionListener();

		new DrawablePhysicsStaticCircle("b1", new Vector2(250, 450), 20, 1f, 2f, .6f) {
			@Override
			public void collision(AbstractPhysicsObject theOtherObject,
					float energy) {
				System.out.println("houla");
			}
		};
		new DrawablePhysicsStaticCircle("b2", new Vector2(325, 525), 20, 1f, 2f, .6f);
		new DrawablePhysicsStaticCircle("b3", new Vector2(400, 450), 20, 1f, 2f, .6f);
	}

	private void moveCamera(GdxGraphics g, Vector2 p) {
		float h = Gdx.graphics.getHeight();
		// float w = Gdx.graphics.getWidth();

		// Set the ball in the upper part of the screen
		float y = p.y - h / PinballSettings.PINBALL_SIZE.y - h / 3;

		// Restrict the camera inside the world
		if (y > h) {
			y = h;
		}
		if (y < 0) {
			y = 0;
		}

		g.moveCamera(0, y);
	}

	public void drawStd(GdxGraphics g) {
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();

		g.setBackgroundColor(Color.WHITE);

		g.setColor(new Color(.8f, .8f, .8f, 1f));
		for (int x = 0; x < h; x += 25) {
			g.drawLine(x, 0, x, 2 * h);
		}

		for (int y = 0; y < 2 * h; y += 25) {
			g.drawLine(0, y, w, y);
		}

		renderer.draw(g, world, decorations);

		g.drawSchoolLogo();
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {

		g.clear();
		PhysicsWorld.updatePhysics();
		moveCamera(g, PhysicsConstants.coordMetersToPixels(ball.getBody()
				.getPosition()));
		//g.zoom(2f);

		switch (draw_mode) {
		case DEBUG:
			debugRenderer.render(world, g.getCamera().combined);
			g.drawFPS(Color.BLACK);
			break;
		case DEBUG_STD:
			debugRenderer.render(world, g.getCamera().combined);
			drawStd(g);
			g.drawFPS(Color.BLACK);
			break;
		case STD:
			drawStd(g);
			break;
		}

		if (displayHelp > 0) {
			displayHelp--;
			g.setColor(new Color(Color.BLACK).mul((float) displayHelp / 100));
			g.drawStringCentered(g.getCamera().position.y,
					"Warning : work in progress\n" +
					"CTRL keys : flippers\n" +
					"SPACE : new ball\n" +
					"d : debug view\n"
					);
		}
	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		switch (keycode) {
		case Keys.CONTROL_LEFT:
			leftFlipper.power(true);
			break;
		case Keys.CONTROL_RIGHT:
			rightFlipper.power(true);
			break;

		case Keys.SPACE:
		case Keys.D:
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
			leftFlipper.power(false);
			break;

		case Keys.CONTROL_RIGHT:
			rightFlipper.power(false);
			break;

		case Keys.D:
			switch (draw_mode) {
			case DEBUG:
				draw_mode = draw_mode_e.DEBUG_STD;
				break;

			case DEBUG_STD:
				draw_mode = draw_mode_e.STD;
				break;

			case STD:
				draw_mode = draw_mode_e.DEBUG;
				break;
			}
			break;

		case Keys.SPACE:
			float h = Gdx.graphics.getHeight();
			float w = Gdx.graphics.getWidth();
			ball.destroy();
			// ball = new Ball("ball", ball_position, 10f,
			// ballSprite.sprites[0]);
			ball = new Ball("ball", new Vector2(0, h), scale(PinballSettings.BALL_DIAMETER/2),	ballSprite.sprites[0]);
			ball.enableCollisionListener();
			break;

		default:
			displayHelp = 300;
			break;
		}
	}
}
