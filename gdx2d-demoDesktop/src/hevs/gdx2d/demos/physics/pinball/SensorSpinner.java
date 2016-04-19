package hevs.gdx2d.demos.physics.pinball;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SensorSpinner extends Sensor implements DrawableObject {
	
	protected TextureRegion[] sprites;
	protected float speed;
	protected float pos;
	private float width;
	private float height;

	public SensorSpinner(String name, Vector2 position, float width, float height, TextureRegion sprites[]) {
		super(name, position, width, height);
		this.sprites = sprites;
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(GdxGraphics g) {
		pos += speed;
		speed -= speed * 0.8f*Gdx.graphics.getDeltaTime();
		int i = ((int) (pos)) % sprites.length;
		
		float w = sprites[i].getRegionWidth();
		float h = sprites[i].getRegionHeight();
		g.draw(sprites[i], getBodyPosition().x-w/2, getBodyPosition().y-h/2, w/2, h/2, w, h, width/w, height/h, 0);
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		super.collision(theOtherObject, energy);
		System.out.println(getClass()+" uohoho "+ theOtherObject.getBody().getLinearVelocity());
		speed = theOtherObject.getBody().getLinearVelocity().len()/2;
	}
}
