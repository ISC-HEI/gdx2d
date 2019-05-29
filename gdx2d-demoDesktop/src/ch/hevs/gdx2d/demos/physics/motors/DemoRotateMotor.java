package ch.hevs.gdx2d.demos.physics.motors;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.physics.PhysicsMotor;
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A demo on how to use {@link PhysicsMotor}.
 * <p/>
 * Based on ex 5.9 from the Nature of code book The bodys are created without
 * the help of this library to show how two bodys for a joint motor can be
 * created.
 * <p/>
 * The clock image source is from <a
 * href="http://dribbble.com/shots/408344-SBB-CFF-FFS">here<a/>.
 *
 * @author Thierry Hischier (hit)
 * @author Christopher Métrailler (mei)
 * @version 1.3
 * @see <a href="http://natureofcode.com/book/chapter-5-physics-libraries/">The
 * nature of code example</a>
 */
public class DemoRotateMotor extends PortableApplication {
	private World world = PhysicsWorld.getInstance();

	private PhysicsMotor physicMotorSeconds;
	private PhysicsMotor physicMotorMinutes;
	private PhysicsMotor physicMotorHours;

	private BitmapImage clockBitmap, secondBitmap, minuteBitmap, hourBitmap;
	private Body body, body2, body3, body4;

	private Vector2 CLOCK_CENTER = new Vector2(136.0f, 128.0f);

	private float minuteDrawAngle;

	public DemoRotateMotor() {
		super(512, 256);
	}

	public static void main(String[] args) {
		new DemoRotateMotor();
	}

	@Override
	public void onInit() {
		setTitle("Simple rotate motor demo, hit, mei 2014");

		new PhysicsScreenBoundaries(getWindowWidth(), getWindowHeight());

		clockBitmap = new BitmapImage("images/clock.png");

		secondBitmap = new BitmapImage("images/clock_second.png");
		minuteBitmap = new BitmapImage("images/clock_minute.png");
		hourBitmap = new BitmapImage("images/clock_hour.png");

		minuteDrawAngle = 0;

		// Get the actual time to calibrate the clock
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String time = df.format(new Date());
		String[] timeList = time.split(":");
		float hourAngle = Float.parseFloat(timeList[0]);
		float minuteAngle = Float.parseFloat(timeList[1]);
		float secondAngle = Float.parseFloat(timeList[2]);

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
		bd2.angle = calcSecondAngle(secondAngle);

		// Physical information about the dynamic body for the second needle
		body2 = world.createBody(bd2);
		FixtureDef fd2 = new FixtureDef();
		fd2.density = 20.0f;
		fd2.restitution = 0.0f;
		fd2.friction = 2.0f;
		fd2.shape = new CircleShape();
		fd2.shape.setRadius(PhysicsConstants.PIXEL_TO_METERS * 60);

		body2.createFixture(fd2);
		fd2.shape.dispose();

		BodyDef bd3 = new BodyDef();
		bd3.type = BodyType.DynamicBody;
		bd3.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER));
		bd3.angle = calcMinuteAngle(minuteAngle, secondAngle);

		// Physical information about the dynamic body for the minute needle
		body3 = world.createBody(bd3);
		FixtureDef fd3 = new FixtureDef();
		fd3.density = 20.0f;
		fd3.restitution = 0.0f;
		fd3.friction = 2.0f;
		fd3.shape = new CircleShape();
		fd3.shape.setRadius(PhysicsConstants.PIXEL_TO_METERS * 60);

		body3.createFixture(fd3);
		fd3.shape.dispose();

		// Initialize the minute needle that it points exactly on the minutes marks
		minuteDrawAngle = (float) (calcMinuteAngle(minuteAngle, 0) * 180 / Math.PI);

		BodyDef bd4 = new BodyDef();
		bd4.type = BodyType.DynamicBody;
		bd4.position.set(PhysicsConstants.coordPixelsToMeters(CLOCK_CENTER));
		bd4.angle = calcHourAngle(hourAngle, minuteAngle);

		// Physical information about the dynamic body for the hour needle
		body4 = world.createBody(bd4);
		FixtureDef fd4 = new FixtureDef();
		fd4.density = 20.0f;
		fd4.restitution = 0.0f;
		fd4.friction = 2.0f;
		fd4.shape = new CircleShape();
		fd4.shape.setRadius(PhysicsConstants.PIXEL_TO_METERS * 60);

		body4.createFixture(fd4);
		fd4.shape.dispose();

		/**
		 * Create 3 motors that will make the moving box move and rotate around
		 * the anchor point (which is the center of the clock) for each needle
		 */
		world.setGravity(new Vector2(0, 0));
		physicMotorSeconds = new PhysicsMotor(body, body2, body.getWorldCenter());
		physicMotorMinutes = new PhysicsMotor(body, body3, body.getWorldCenter());
		physicMotorHours = new PhysicsMotor(body, body4, body.getWorldCenter());

		// Initialize the motor with a speed and torque
		physicMotorSeconds.initializeMotor((float) (-(3 * Math.PI / 180.0f)), 360.0f, true);
		physicMotorMinutes.initializeMotor((float) (-(0.0475 * Math.PI / 180.0f)), 360.0f, true);
		physicMotorHours.initializeMotor((float) (-(0.003 * Math.PI / 180.0f)), 360.0f, true);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Control the second needle, change the position of the minute needle only when
		// a whole minute is passed
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String time = df.format(new Date());
		String[] timeList = time.split(":");
		float seconds = Float.parseFloat(timeList[2]);

		if (seconds == 0) {
			minuteDrawAngle = (float) (body3.getAngle() * 180 / Math.PI);
		}

		// Get the size of the window
		final int w = getWindowWidth();
		final int h = getWindowHeight();

		// Clear the graphic to draw the new image
		g.clear();

		// Create a nice background from light gray to white without any images
		for (int i = 0; i <= h; i++) {
			float c = 255 - i * 0.2f;
			g.setColor(new Color(c / 255, c / 255, c / 255, 1.0f));
			g.drawLine(0, h - i, w, h - i);
		}

		PhysicsWorld.updatePhysics();
		// Draw the clock surface as background and the clock hand in front with
		// the same angle as the motor has.
		g.drawPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, clockBitmap);

		// Draw the hour needle
		g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
				(float) (body4.getAngle() * 180 / Math.PI), 1.0f, hourBitmap);

		// Draw the minute needle
		// (the position of the image change every whole minute, the motor turns continuously)
		g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
				minuteDrawAngle, 1.0f, minuteBitmap);

		// Draw the second needle
		g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
				(float) (body2.getAngle() * 180 / Math.PI), 1.0f, secondBitmap);

		g.setColor(Color.BLACK);
		g.drawString(w - 200, h - 10, "Famous clock from\r\n"
				+ "the Swiss Railways.");

		g.drawString(w - 200, h - 80, displayTime());

		g.drawSchoolLogo();
	}

	private String displayTime() {
		// Return the current time as a String (hours:minutes:seconds)
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return "Current time: " + df.format(new Date());
	}

	private float calcSecondAngle(float seconds) {
		float result = (float) ((360 - seconds * 6) * Math.PI / 180.0f);
		return result;
	}

	private float calcMinuteAngle(float minutes, float seconds) {
		float result = (float) ((360 - ((minutes * 6) + (seconds * 6 / 60))) * Math.PI / 180.0f);
		return result;
	}

	private float calcHourAngle(float hours, float minutes) {
		if (hours > 12) {
			hours -= 12;
		}
		float result = (float) ((360 - ((hours * 30) + (minutes * 30 / 60))) * Math.PI / 180.0f);
		return result;
	}
}
