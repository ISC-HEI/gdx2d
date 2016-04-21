/**
 * A demo on how to use {@link PhysicsMotor} and GearJointDef.
 * <p/>
 * Based on DemoRotateMotor, simulate the famous Swiss railway clock (@see
 * http://en.wikipedia.org/wiki/Swiss_railway_clock). The second hand runs too
 * fast then wait for the synchronization at zero. The minute hand goes by step,
 * when the synchronisation signal comes.
 *
 * The synchronization signal is emulated by waiting the system time's seconds
 * equals zero.
 *
 * This software uses 2 motors, one for the second hand and another for the
 * minute hand. The hour hand is driven by a gear, connected to the minute
 * motor.
 * <p/>
 * The clock image source is from <a
 * href="http://dribbble.com/shots/408344-SBB-CFF-FFS">here<a/>.
 *
 * @author Marc Pignat (pim)
 */

package ch.hevs.gdx2d.demos.physics.gears;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.physics.primitives.PhysicsBox;
import hevs.gdx2d.components.physics.PhysicsMotor;
import hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoRotateGears extends PortableApplication {

	/**
	 * A simple class for reading time
	 *
	 */
	class TimeFloat {
		float hour;
		float second;
		float minute;

		TimeFloat() {
			String t[] = new SimpleDateFormat("HH:mm:ss").format(new Date())
					.split(":");
			hour = Float.parseFloat(t[0]);
			minute = Float.parseFloat(t[1]);
			second = Float.parseFloat(t[2]);
		}

		float getSecondAngle() {
			return (float) -(2 * Math.PI * second) / 60;
		}

		float getMinuteAngle() {
			return (float) -(2 * Math.PI * minute) / 60;
		}

		float getHourAngle() {
			return (float) -(2 * Math.PI * ((hour % 12) * 60 + minute))
					/ (12 * 60);
		}
	}

	private World world = PhysicsWorld.getInstance();

	/* Bitmaps */
	private BitmapImage bitmapClock;
	private BitmapImage bitmapSecond;
	private BitmapImage bitmapMinute;
	private BitmapImage bitmapHour;

	/* Hands */
	private PhysicsBox hand_second;
	private PhysicsBox hand_minute;
	private PhysicsBox hand_hour;

	/* Motors */
	PhysicsMotor motor_seconds;
	PhysicsMotor motor_minutes;

	private Vector2 CLOCK_CENTER = new Vector2(136.0f, 128.0f);

	/* Hand speed */
	private float MOTOR_SPEED_SECOND = (float) (-Math.PI / 58.5);
	private float MOTOR_SPEED_MINUTE = (float) (-Math.PI / 60);

	private DebugRenderer debugRenderer;
	private boolean debug_rendering = false;

	public DemoRotateGears() {
		super(512, 256);
	}

	public static void main(String args[]) {
		new DemoRotateGears();
	}

	@Override
	public void onInit() {
		/* Get time */
		TimeFloat time = new TimeFloat();

		/* Set title */
		setTitle("Simple rotate gears demo, pim 2015");

		/* Create a debug renderer */
		debugRenderer = new DebugRenderer();

		/* Load images */
		bitmapClock = new BitmapImage("data/images/clock.png");
		bitmapSecond = new BitmapImage("data/images/clock_second.png");
		bitmapMinute = new BitmapImage("data/images/clock_minute.png");
		bitmapHour = new BitmapImage("data/images/clock_hour.png");

		/* Create the frame, the motors will be attached to it */
		PhysicsStaticBox frame = new PhysicsStaticBox("frame", CLOCK_CENTER,
				10, 10);

		/* Create the hands, at the current system time */
		hand_second = new PhysicsBox("seconds", CLOCK_CENTER, 10, 50,
				time.getSecondAngle());
		hand_minute = new PhysicsBox("minutes", CLOCK_CENTER, 10, 40,
				time.getMinuteAngle());
		hand_hour = new PhysicsBox("hours", CLOCK_CENTER, 10, 30,
				time.getHourAngle());

		/* Prevent collision between hands */
		hand_second.setCollisionGroup(-2);
		hand_minute.setCollisionGroup(-2);
		hand_hour.setCollisionGroup(-2);

		/* Create the motors */
		motor_seconds = new PhysicsMotor(frame.getBody(),
				hand_second.getBody(), frame.getBody().getWorldCenter());

		motor_minutes = new PhysicsMotor(frame.getBody(),
				hand_minute.getBody(), frame.getBody().getWorldCenter());

		/*
		 * This motor will only be used as a skeleton for the gear.
		 */
		PhysicsMotor motor_m2h = new PhysicsMotor(frame.getBody(),
				hand_hour.getBody(), frame.getBody().getWorldCenter());

		/*
		 * Create the gear between the minute hand and the hour hand
		 */
		GearJointDef gear_m2h = new GearJointDef();

		/* Do the connections */
		gear_m2h.bodyA = hand_minute.getBody();
		gear_m2h.bodyB = hand_hour.getBody();
		gear_m2h.joint1 = motor_minutes.getJoint();
		gear_m2h.joint2 = motor_m2h.getJoint();

		/*
		 * Negative ratio because the minute and hour hand rotates the same
		 * direction
		 */
		gear_m2h.ratio = -60;
		world.createJoint(gear_m2h);

		/*
		 * Start the clock, the second hand is running and the minute hand is
		 * stopped
		 */
		motor_seconds.initializeMotor(MOTOR_SPEED_SECOND, 1.0f, true);
		motor_minutes.initializeMotor(0.0f, 1.0f, true);

		System.out.println("click to switch debug/rendering mode");
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {

		/* Update the world physics */
		PhysicsWorld.updatePhysics();

		/* Sync signal emulation using system time */
		boolean sync_signal = new TimeFloat().second % 60.0 == 0.0;

		/*
		 * Second hand logic
		 *
		 * Stop when vertical, start when sync_sygnal
		 */
		if (sync_signal) {
			motor_seconds.setMotorSpeed(MOTOR_SPEED_SECOND);
		} else {
			double angle = -hand_second.getBody().getAngle() % (2 * Math.PI);

			if (angle > 2 * Math.PI * 0.995 && motor_seconds.getSpeed() != 0.0) {
				motor_seconds.setMotorSpeed(0.0f);
			}
		}

		/*
		 * Minute hand logic
		 *
		 * Move from 1 minute when sync_signal
		 */
		if (sync_signal) {
			motor_minutes.setMotorSpeed(MOTOR_SPEED_MINUTE);
		} else {
			motor_minutes.setMotorSpeed(0.0f);
		}

		/* Get the size of the window */
		final int w = getWindowWidth();
		final int h = getWindowHeight();

		/* Clear the graphic to draw the new image */
		g.clear();

		if (debug_rendering)
		{
			debugRenderer.render(world, g.getCamera().combined);
			g.setColor(Color.WHITE);
		}
		else
		{
			/* Create a nice grey gradient for the background */
			for (int i = 0; i <= h; i++) {
				float c = 255 - i * 0.3f;
				g.setColor(new Color(c / 255, c / 255, c / 255, 1.0f));
				g.drawLine(0, h - i, w, h - i);
			}

			/* Draw the clock frame */
			g.drawPicture(CLOCK_CENTER.x, CLOCK_CENTER.y, bitmapClock);

			/* Draw the hands */
			g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
					(float) (Math.toDegrees(hand_hour.getBody().getAngle())), 1.0f,
					bitmapHour);

			g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
					(float) (Math.toDegrees(hand_minute.getBody().getAngle())),
					1.0f, bitmapMinute);

			g.drawTransformedPicture(CLOCK_CENTER.x, CLOCK_CENTER.y,
					(float) (Math.toDegrees(hand_second.getBody().getAngle())),
					1.0f, bitmapSecond);
			g.setColor(Color.BLACK);
		}

		/* Display time in text */
		g.drawString(w - 200, h - 10, "Famous clock from\r\n"
				+ "the Swiss Railways.");

		g.drawString(w - 200, h - 80, displayTime());

		g.drawSchoolLogo();
	}

	@Override
	/**
	 * Change shape on click
	 */
	public void onClick(int x, int y, int button) {
		debug_rendering  = !debug_rendering;
	}

	private String displayTime() {
		// Return the current time as a String (hours:minutes:seconds)
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return "Current time: " + df.format(new Date());
	}

}
