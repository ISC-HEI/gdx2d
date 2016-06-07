package ch.hevs.gdx2d.demos.physics.pinball;

import ch.hevs.gdx2d.components.physics.PhysicsMotor;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Flipper {

	class Sub extends PhysicsBox implements DrawableObject {

		private float width;
		private float height;

		Sub(String name, Vector2 position, float width, float height, float angle, float density, float restitution, float friction)
		{
			super("name"+"_flipper", position, width, height, 4.0f, 0.3f, 0.6f, angle);
			this.width = width;
			this.height = height;
		}

		@Override
		public void draw(GdxGraphics g) {
			int currentLFFrame = ((int) Math.abs(getBodyAngleDeg()))%sprites.length;
			float w = sprites[currentLFFrame].getRegionWidth();
			float h = sprites[currentLFFrame].getRegionHeight();
			g.draw(sprites[currentLFFrame], getBodyPosition().x-w/2, getBodyPosition().y-h/2, w/2, h/2, w, h, width/w, height/h, getBodyAngleDeg());
		}
	}

	protected TextureRegion[] sprites;
	protected PhysicsBox flipper;
	protected PhysicsMotor motor;

	public Flipper(String name, Vector2 position, float width, float height, float angle, float angle_var, TextureRegion[] sprites) {

		PhysicsStaticBox frame = new PhysicsStaticBox(name +"_frame", position, .1f, .1f);
		Vector2 flipper_pos = new Vector2(position).add(new Vector2(width/2, 0).rotate(angle));

		flipper = new Sub("name"+"_flipper", flipper_pos, width, height, (float)Math.toRadians(angle), 4.0f, 0.3f, 0.6f);
		motor = new PhysicsMotor(frame.getBody(), flipper.getBody(), frame.getBody().getWorldCenter());
		this.sprites = sprites;

		if (angle_var > 0)
		{
			motor.setLimits(motor.getAngle(), motor.getAngle()+ (float)Math.toRadians(angle_var));
			System.out.println("limist = " + Math.toDegrees(motor.getAngle()) + " , "+ angle_var);
			motor.initializeMotor(50f, 50f, false);
		}
		else
		{
			System.out.println("limist = " + Math.toDegrees(motor.getAngle()) + " , "+ angle_var);
			motor.setLimits(motor.getAngle()+ (float)Math.toRadians(angle_var), motor.getAngle());
			motor.initializeMotor(-50f, 50f, false);
		}
		motor.enableLimit(true);
	}

	public void power(boolean on) {
		motor.enableMotor(on);
	}
}
