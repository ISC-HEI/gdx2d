package ch.hevs.gdx2d.demos.selector

internal object SelectedDemos {

    var list = arrayOf(DemoCategory(
            "Getting started with simple demos",

            arrayOf(DemoDescriptor(
                    "Simple shapes",
                    ch.hevs.gdx2d.demos.simple.DemoSimpleShapes::class.java,
                    "The simplest demo, ever. Nothing is moving but it shows how to get started with the library."
            ), DemoDescriptor(
                    "Basic animation",
                    ch.hevs.gdx2d.demos.simple.DemoSimpleAnimation::class.java,
                    "Shows how to animate the radius of a circle."
            ), DemoDescriptor(
                    "Moving circles",
                    ch.hevs.gdx2d.demos.simple.DemoCircles::class.java,
                    "Draws moving circles"
            ), DemoDescriptor(
                    "Mistify screen saver",
                    ch.hevs.gdx2d.demos.simple.mistify.DemoLines::class.java,
                    "Shows a more complex demo using multiple lines"
            ), DemoDescriptor(
                    "A Julia Fractal",
                    ch.hevs.gdx2d.demos.simple.DemoJuliaFractal::class.java,
                    "A graphical library without a fractal is not really a graphical library, isn't it?"
            ), DemoDescriptor(
                    "Spritesheet support",
                    ch.hevs.gdx2d.demos.spritesheet.DemoSpriteSheet::class.java,
                    "Demonstrate how to use a sprite sheet"
            ), DemoDescriptor(
                    "Tiled editor support",
                    ch.hevs.gdx2d.demos.tilemap.advanced.DemoTileAdvanced::class.java,
                    "From the map editor directly to GDX2D"
            ))
    ), DemoCategory(
            "Image drawing",

            arrayOf(DemoDescriptor(
                    "Basic image drawing",
                    ch.hevs.gdx2d.demos.image_drawing.DemoSimpleImage::class.java,
                    "Demonstrates how to load an image from the disk and to display it on the screen."
            ), DemoDescriptor(
                    "Mirror an image",
                    ch.hevs.gdx2d.demos.image_drawing.DemoMirrorImage::class.java,
                    "Shows how draw a mirrored image and the various possibilities of it."
            ), DemoDescriptor(
                    "Rotate image, with animation",
                    ch.hevs.gdx2d.demos.image_drawing.DemoRotatingImage::class.java,
                    "Shows how to animation the rotation of a picture."
            ), DemoDescriptor(
                    "Retrieve an image color",
                    ch.hevs.gdx2d.demos.image_drawing.DemoGetImageColor::class.java,
                    "Demonstrates how read the color information out of an image."
            ), DemoDescriptor(
                    "Draw an image with transparency (alpha)",
                    ch.hevs.gdx2d.demos.image_drawing.DemoAlphaImage::class.java,
                    "Shows how the alpha transparency of an image can be changed to make it more or less transparent."
            ), DemoDescriptor(
                    "Animated images and shapes",
                    ch.hevs.gdx2d.demos.complex_shapes.DemoComplexShapes::class.java,
                    "Show that many images can be drawn simultaneously on the screen. The images are rotated and bounce around the screen. Click to change the rendered image."
            ), DemoDescriptor(
                    "Scrolling",
                    ch.hevs.gdx2d.demos.scrolling.DemoScrolling::class.java,
                    "This demo demonstrates the usage of the scrolling technique which can be used to animate a game for instance."
            ))
    ), DemoCategory(
            "Music, sound effects, text and fonts",

            arrayOf(DemoDescriptor(
                    "Playing music",
                    ch.hevs.gdx2d.demos.music.DemoMusicPlay::class.java,
                    "You want to play a nice background music in your game? Want to know how to stop it or resume playing? This demo is for you!"
            ), DemoDescriptor(
                    "Playing piano samples",
                    ch.hevs.gdx2d.demos.music.DemoBabyPiano::class.java,
                    "Play little pieces of music!"
            ), DemoDescriptor(
                    "Displaying text using various fonts",
                    ch.hevs.gdx2d.demos.fonts.DemoFontGeneration::class.java,
                    "If you want to display text in your game with various fonts, this is the demo to launch!"
            ), DemoDescriptor(
                    "Graphical user interface demonstration",
                    ch.hevs.gdx2d.demos.menus.DemoGUI::class.java,
                    "Basic GUI interface with buttons and edit text."
            ), DemoDescriptor(
                    "Multiple screens and transitions",
                    ch.hevs.gdx2d.demos.menus.screens.DemoScreen::class.java,
                    "How to handle multiple screens in your game and transition between them."
            ))
    ), DemoCategory(
            "Advanced techniques",

            arrayOf(DemoDescriptor(
                    "Scrolling your game",
                    ch.hevs.gdx2d.demos.scrolling.DemoScrolling::class.java,
                    "Want to do a side-scroller game. Just look at this very complete demo to see how it can be done nicely using gdx2d."
            ), DemoDescriptor(
                    "Position interpolation",
                    ch.hevs.gdx2d.demos.tween.interpolatorengine.DemoPositionInterpolator::class.java,
                    "Shows how to animate automatically from one value to another one, very simply."
            ), DemoDescriptor(
                    "Adding lights to a game",
                    ch.hevs.gdx2d.demos.lights.DemoLight::class.java,
                    "If you want to create a special ambiance in your game, maybe using lights and shadows is a good idea. This demo demonstrates how it can be easily done in gdx2d"
            ), DemoDescriptor(
                    "Rotating lights, animated",
                    ch.hevs.gdx2d.demos.lights.DemoRotateLight::class.java,
                    "Another demo on how to use lights, this time by making them move in circles!"
            ))
    ), DemoCategory(
            "Physics simulations",

            arrayOf(DemoDescriptor(
                    "Dominoes",
                    ch.hevs.gdx2d.demos.physics.DemoSimplePhysics::class.java,
                    "Enjoy looking at falling dominoes? This demo shows how to create basics physics objects and make them move."
            ), DemoDescriptor(
                    "Falling balloons",
                    ch.hevs.gdx2d.demos.physics.DemoPhysicsBalls::class.java,
                    "A demo for soccer fans! Just interact with the screen to make balls fall. On Android, this demos uses the accelerometers."
            ), DemoDescriptor(
                    "Polygons as physics objects",
                    ch.hevs.gdx2d.demos.physics.DemoPolygonPhysics::class.java,
                    "This demos shows how to use general polygons as physics objects."
            ), DemoDescriptor(
                    "Obstacles made of a chain of objects",
                    ch.hevs.gdx2d.demos.physics.chains.DemoChainPhysics::class.java,
                    "This demos shows how to create a chain of objects that are seen as obstacles for other physics objects."
            ), DemoDescriptor(
                    "Collisions notification",
                    ch.hevs.gdx2d.demos.physics.collisions.DemoCollisionListener::class.java,
                    "Demonstrates how objects can be informed when they collide against other objects."
            ), DemoDescriptor(
                    "Particle systems",
                    ch.hevs.gdx2d.demos.physics.particle.DemoParticlePhysics::class.java,
                    "Particles, particles everywhere."
            ), DemoDescriptor(
                    "Physics joints - rotating joint",
                    ch.hevs.gdx2d.demos.physics.joints.DemoWindmill::class.java,
                    "An object that rotates around a circular joint object."
            ), DemoDescriptor(
                    "Physics joints - rope",
                    ch.hevs.gdx2d.demos.physics.joints.DemoRopeJoint::class.java,
                    "Building a rope with joints."
            ), DemoDescriptor(
                    "Physics joints and motor - mixing objects with a motor",
                    ch.hevs.gdx2d.demos.physics.joints.DemoMixer::class.java,
                    "No description yet."
            ), DemoDescriptor(
                    "A rocket",
                    ch.hevs.gdx2d.demos.physics.rocket.DemoPhysicsRocket::class.java,
                    "To the moon!"
            ), DemoDescriptor(
                    "Mouse interactions with physics",
                    ch.hevs.gdx2d.demos.physics.mouse_interaction.DemoPhysicsMouse::class.java,
                    "Shows how you can use the mouse to interact with physics objects."
            ))
    ), DemoCategory(
            "Shaders (advanced)",

            arrayOf(DemoDescriptor(
                    "Julia fractal on the GPU",
                    ch.hevs.gdx2d.demos.shaders.advanced.DemoJulia::class.java,
                    "What would be the world without fractals?"
            ), DemoDescriptor(
                    "Scene post-processing",
                    ch.hevs.gdx2d.demos.shaders.advanced.DemoPostProcessing::class.java,
                    "Need to add some fancy effects to your game? That's the place to go!"
            ), DemoDescriptor(
                    "Shaders collection",
                    ch.hevs.gdx2d.demos.shaders.DemoAllShaders::class.java,
                    "It shines, it is beautiful!"
            ))
    ))

    internal class DemoDescriptor(var name: String, var clazz: Class<*>, var desc: String)

    internal class DemoCategory(var name: String, var descs: Array<DemoDescriptor>)
}
