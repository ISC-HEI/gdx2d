package ch.hevs.gdx2d.demos.selector;

class SelectedDemos {
	static class DemoDescriptor {
		public String name;
		Class<?> clazz;
		String desc;

		DemoDescriptor(String n, Class<?> c, String d) {
			clazz = c;
			name = n;
			desc = d;
		}
	}

	static class DemoCategory {
		public String name;
		DemoDescriptor[] descs;

		DemoCategory(String n, DemoDescriptor[] d)
		{
			name = n;
			descs = d;
		}
	}

	static DemoCategory[] list =
	{
		new DemoCategory
		(
			"Getting started with simple demos",

			new DemoDescriptor[]
		    {
				new DemoDescriptor(
					"Simple shapes",
					ch.hevs.gdx2d.demos.simple.DemoSimpleShapes.class,
					"The simplest demo, ever. Nothing is moving but it shows how to get started with the library."
				),
				new DemoDescriptor(
					"Basic animation",
          ch.hevs.gdx2d.demos.simple.DemoSimpleAnimation.class,
					"Shows how to animate the radius of a circle."
				),
				new DemoDescriptor(
					"Moving circles",
          ch.hevs.gdx2d.demos.simple.DemoCircles.class,
					"Draws moving circles"
				),
				new DemoDescriptor(
					"Mistify screen saver",
          ch.hevs.gdx2d.demos.simple.mistify.DemoLines.class,
					"Shows a more complex demo using multiple lines"
				),
				new DemoDescriptor(
					"A Julia Fractal",
          ch.hevs.gdx2d.demos.simple.DemoJuliaFractal.class,
					"A graphical library without a fractal is not really a graphical library, isn't it?"
				),
				new DemoDescriptor(
					"Spritesheet support",
          ch.hevs.gdx2d.demos.spritesheet.DemoSpriteSheet.class,
					"Demonstrate how to use a sprite sheet"
				),
				new DemoDescriptor(
					"Tiled editor support",
          ch.hevs.gdx2d.demos.tilemap.advanced.DemoTileAdvanced.class,
					"From the map editor directly to GDX2D"
				),
		    }
		),
		new DemoCategory
		(
			"Image drawing",

			new DemoDescriptor[]
		    {
				new DemoDescriptor(
					"Basic image drawing",
          ch.hevs.gdx2d.demos.image_drawing.DemoSimpleImage.class,
					"Demonstrates how to load an image from the disk and to display it on the screen."
				),
				new DemoDescriptor(
					"Mirror an image",
          ch.hevs.gdx2d.demos.image_drawing.DemoMirrorImage.class,
					"Shows how draw a mirrored image and the various possibilities of it."
				),
				new DemoDescriptor(
					"Rotate image, with animation",
          ch.hevs.gdx2d.demos.image_drawing.DemoRotatingImage.class,
					"Shows how to animation the rotation of a picture."
				),
				new DemoDescriptor(
					"Retrieve an image color",
          ch.hevs.gdx2d.demos.image_drawing.DemoGetImageColor.class,
					"Demonstrates how read the color information out of an image."
				),
				new DemoDescriptor(
					"Draw an image with transparency (alpha)",
          ch.hevs.gdx2d.demos.image_drawing.DemoAlphaImage.class,
					"Shows how the alpha transparency of an image can be changed to make it more or less transparent."
				),
				new DemoDescriptor(
					"Animated images and shapes",
          ch.hevs.gdx2d.demos.complex_shapes.DemoComplexShapes.class,
					"Show that many images can be drawn simultaneously on the screen. The images are rotated and bounce around the screen. Click to change the rendered image."
				),
				new DemoDescriptor(
					"Scrolling",
          ch.hevs.gdx2d.demos.scrolling.DemoScrolling.class,
					"This demo demonstrates the usage of the scrolling technique which can be used to animate a game for instance."
				),
		    }
		),
		new DemoCategory
		(
			"Music, sound effects, text and fonts",

			new DemoDescriptor[]
		    {
				new DemoDescriptor(
					"Playing music",
          ch.hevs.gdx2d.demos.music.DemoMusicPlay.class,
					"You want to play a nice background music in your game? Want to know how to stop it or resume playing? This demo is for you!"
				),
				new DemoDescriptor(
					"Playing piano samples",
          ch.hevs.gdx2d.demos.music.DemoBabyPiano.class,
					"Play little pieces of music!"
				),
				new DemoDescriptor(
					"Displaying text using various fonts",
          ch.hevs.gdx2d.demos.fonts.DemoFontGeneration.class,
					"If you want to display text in your game with various fonts, this is the demo to launch!"
				),
				new DemoDescriptor(
					"Graphical user interface demonstration",
          ch.hevs.gdx2d.demos.menus.DemoGUI.class,
					"Basic GUI interface with buttons and edit text."
				),
				new DemoDescriptor(
					"Multiple screens and transitions",
          ch.hevs.gdx2d.demos.menus.screens.DemoScreen.class,
					"How to handle multiple screens in your game and transition between them."
				),
		    }
		),
		new DemoCategory
		(
			"Advanced techniques",

			new DemoDescriptor[]
		    {
				new DemoDescriptor(
					"Scrolling your game",
          ch.hevs.gdx2d.demos.scrolling.DemoScrolling.class,
					"Want to do a side-scroller game. Just look at this very complete demo to see how it can be done nicely using gdx2d."
				),
				new DemoDescriptor(
					"Position interpolation",
          ch.hevs.gdx2d.demos.tween.interpolatorengine.DemoPositionInterpolator.class,
					"Shows how to animate automatically from one value to another one, very simply."
				),
				new DemoDescriptor(
          "Adding lights to a game",
          ch.hevs.gdx2d.demos.lights.DemoLight.class,
					"If you want to create a special ambiance in your game, maybe using lights and shadows is a good idea. This demo demonstrates how it can be easily done in gdx2d"
				),
				new DemoDescriptor(
					"Rotating lights, animated",
          ch.hevs.gdx2d.demos.lights.DemoRotateLight.class,
					"Another demo on how to use lights, this time by making them move in circles!"
				),
		    }
		),
		new DemoCategory
		(
			"Physics simulations",

			new DemoDescriptor[]
		    {
				new DemoDescriptor(
					"Dominoes",
          ch.hevs.gdx2d.demos.physics.DemoSimplePhysics.class,
					"Enjoy looking at falling dominoes? This demo shows how to create basics physics objects and make them move."
				),
				new DemoDescriptor(
					"Falling balloons",
          ch.hevs.gdx2d.demos.physics.DemoPhysicsBalls.class,
					"A demo for soccer fans! Just interact with the screen to make balls fall. On Android, this demos uses the accelerometers."
				),
				new DemoDescriptor(
          "Polygons as physics objects",
          ch.hevs.gdx2d.demos.physics.DemoPolygonPhysics.class,
				  "This demos shows how to use general polygons as physics objects."
				),
				new DemoDescriptor(
					"Obstacles made of a chain of objects",
          ch.hevs.gdx2d.demos.physics.chains.DemoChainPhysics.class,
					"This demos shows how to create a chain of objects that are seen as obstacles for other physics objects."
				),
				new DemoDescriptor(
					"Collisions notification",
          ch.hevs.gdx2d.demos.physics.collisions.DemoCollisionListener.class,
					"Demonstrates how objects can be informed when they collide against other objects."
				),
				new DemoDescriptor(
					"Particle systems",
          ch.hevs.gdx2d.demos.physics.particle.DemoParticlePhysics.class,
					"Particles, particles everywhere."
				),
				new DemoDescriptor(
					"Physics joints - rotating joint",
          ch.hevs.gdx2d.demos.physics.joints.DemoWindmill.class,
					"An object that rotates around a circular joint object."
				),
				new DemoDescriptor(
					"Physics joints - rope",
          ch.hevs.gdx2d.demos.physics.joints.DemoRopeJoint.class,
					"Building a rope with joints."
				),
				new DemoDescriptor(
					"Physics joints and motor - mixing objects with a motor",
          ch.hevs.gdx2d.demos.physics.joints.DemoMixer.class,
					"No description yet."
				),
				new DemoDescriptor(
					"A rocket",
          ch.hevs.gdx2d.demos.physics.rocket.DemoPhysicsRocket.class,
					"To the moon!"
				),
				new DemoDescriptor(
					"Mouse interactions with physics",
          ch.hevs.gdx2d.demos.physics.mouse_interaction.DemoPhysicsMouse.class,
					"Shows how you can use the mouse to interact with physics objects."
				),
		    }
		),
		new DemoCategory
		(
			"Shaders (advanced)",

			new DemoDescriptor[]
		    {
				new DemoDescriptor(
					"Julia fractal on the GPU",
          ch.hevs.gdx2d.demos.shaders.advanced.DemoJulia.class,
          "What would be the world without fractals?"
				),
				new DemoDescriptor(
					"Scene post-processing",
          ch.hevs.gdx2d.demos.shaders.advanced.DemoPostProcessing.class,
          "Need to add some fancy effects to your game? That's the place to go!"
				),
				new DemoDescriptor(
					"Shaders collection",
          ch.hevs.gdx2d.demos.shaders.DemoAllShaders.class,
					"It shines, it is beautiful!"
				),
		    }
		),
	};
}
