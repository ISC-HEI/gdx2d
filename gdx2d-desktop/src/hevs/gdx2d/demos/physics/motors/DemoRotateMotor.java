package hevs.gdx2d.demos.physics.motors;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.physics.PhysicsMotor;
import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A demo on how to use {@link PhysicsMotor}.
 * 
 * Based on ex 5.9 from the Nature of code book The bodys are created without
 * the help of this library to show how two bodys for a joint motor can be
 * created.
 * 
 * The clock image source is from <a
 * href="http://dribbble.com/shots/408344-SBB-CFF-FFS">here<a/>.
 * 
 * @see <a href="http://natureofcode.com/book/chapter-5-physics-libraries/">The
 *      nature of code example</a>
 * 
 * @author Thierry Hischier (hit)
 * @author Christopher Métrailler (mei)
 * @version 1.2
 */
public class DemoRotateMotor extends PortableApplication {
	private World world = PhysicsWorld.getInstance();

	private DebugRenderer debugRenderer;
	private PhysicsMotor physicMotor;

	private BitmapImage clockBitmap, secondBitmap;
	private Body body, body2;

	private Vector2 CLOCK_CENTER = new Vector2(136.0f, 128.0f);

	public DemoRotateMotor(boolean onAndroid) {
		super(onAndroid, 512, 256);
	}

	@Override
	public void onInit() {
		setTitle("Simple rotate motor demo, hit, mei 2014");

		new PhysicsScreenBoundaries(getWindowWidth(), getWindowHeight());

		// A renderer that displays physics objects things simply
		debugRenderer = new DebugRenderer();

		clockBitmap = new BitmapImage("data/images/clock.png");

		// TODO: add hour and minute needles "clock_hour.png" and
		// "clock_minute.png" are already ready for you `hit`

		secondBitmap = new BitmapImage("data/images/clock_second.png");

		// The clock is the main static body
		BodyDef bd1 = new BodyDef();
		bd1.type = BodyType.StaticBody;
		bd1.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER));
		bd1.angle = 0;

		// Physical information about the static body
		body = world.createBody(bd1);
		FixtureDef fd1 = new FixtureDef();
		fd1.density = 20.0f;
		fd1.restitution = 0.0f;
		fd1.friction = 1.0f;
		fd1.shape = new CircleShape();
		fd1.shape.setRadius(PhysicsConstants.PIXEL_TO_METERS * 120);

		body.createFixture(fd1);
		fd1.shape.dispose();

		// Create the dynamic Body which acts as needle controller
		BodyDef bd2 = new BodyDef();
		bd2.type = BodyType.DynamicBody;
		bd2.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER));
		bd2.angle = 0; // FIXME: should be the current time

		// Physical information about the dynamic body
		body2 = world.createBody(bd2);
		FixtureDef fd2 = new FixtureDef();
		fd2.density = 20.0f;
		fd2.restitution = 0.0f;
		fd2.friction = 2.0f;
		fd2.shape = new CircleShape();
		fd2.shape.setRadius(PhysicsConstants.PIXEL_TO_METERS * 60);

		body2.createFixture(fd2);
		fd2.shape.dispose();

		/**
		 * Create a motor that will make the moving box move and rotate around
		 * the anchor point (which is the center of the clock)
		 */
		physicMotor = new PhysicsMotor(body, body2, body.getWorldCenter());
		world.setGravity(new Vector2(0, 0));

		// Initialize the motor with a speed and torque
		physicMotor.initializeMotor((float) (-2.8), 360.0f, true);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {

		final int w = getWindowWidth();
		final int h = getWindowHeight();

		g.clear();

		// Create a nice background from light gray to white without any images
		for (int i = 0; i <= h; i++) {
			float c = 255 - i * 0.2f;
			g.setColor(new Color(c / 255, c / 255, c / 255, 1.0f));
			g.drawLine(0, h - i, w, h - i);
		}

		// FIXME: remove when no more used
		// Render the world with the whole physics
		debugRenderer.render(world, g.getCamera().combined);

		PhysicsWorld.updatePhysics();
		// Draw the clock surface as background and the clock hand in front with
		// the same angle as the motor has.
		g.drawPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, clockBitmap);

		// Draw the second needle
		g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
				body2.getAngle(), 1.0f, secondBitmap);

		g.setColor(Color.BLACK);
		g.drawString(w - 10, h - 10, "Famous clock from\r\n"
				+ "the Swiss Railways.", HAlignment.RIGHT);

		g.drawString(w - 10, h - 80, displayTime(), HAlignment.RIGHT);

		g.drawSchoolLogo();
	}

	private String displayTime() {
		// Return the current time as a String (hours:minutes:seconds)
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return "Current time: " + df.format(new Date());
	}

	public static void main(String args[]) {
		new DemoRotateMotor(false);
	}
}
