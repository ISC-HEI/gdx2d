## What is gdx2d ?

*gdx2d* is a simple to use 2d game and graphics framework. It is multi-platform (working on Windows, Linux and Android). It is Java based with the heavy-lifting done in native code (i.e. C/C++), for example for physics rendering. Here is an example of what the library is capable of:

![Screen shot](https://raw.github.com/wiki/pmudry/gdx2d/multi_screenshot.png)

## Why this framework?
The advantage of using *gdx2d* is that you can develop your code in Java on your standard desktop computer like any other Java application and then deploy the same code, with a single click, on an Android phone or tablet. Of course, you have to take into consideration the fact that some of your code is a bit different for Android (for instance, you do not have accelerometers on your PC). 

The library itself is a wrapper around a very very nice library called [libgdx](http://code.google.com/libgdx) which provides almost everything to the library.

## What do I get?
It is multi-platform so that you can run your code on desktop computers (running either Windows, Linux or Mac) but also on mobile devices running Android. It is based on ´libgdx´ but provides a much simpler interface and multiple demo programs to get started.

Using the framework, the following code displays a shrinking/growing circle running smoothly:

```java
package hevs.gdx2d.demos.simple;

import com.badlogic.gdx.graphics.Color;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

/**
 * A very simple demonstration on how to display something animated with the library
 * @author Pierre-André Mudry (mui)
 */
public class DemoSimpleAnimation extends PortableApplication {
	int radius = 5, speed = 1;
	
	public DemoSimpleAnimation(boolean onAndroid) {
		super(onAndroid);
	}

	public void onInit() {
		// Sets the window title
		setTitle("Simple demo, mui 2013");
	}

	public void onGraphicRender(GdxGraphics g) {		
		
		// Clears the screen
		g.clear();
		g.drawFilledCircle(g.getScreenWidth()/2, g.getScreenHeight()/2, radius, Color.BLUE);		

		// Update the circle radius
		if (radius >= 50|| radius <= 3)
			speed *= -1;

		radius += speed;
		
		g.drawSchoolLogo();
	}

	public static void main(String[] args) {
		/**
		 * Note that the constructor parameter is used to determine if running on Android or not.
		 * As we are in main there, it means we are on desktop computer.
		 */
		new DemoSimpleAnimation(false);
	}
}
```java

## Who did this and why?
It was developped for the [inf1 course](http://inf1.begincoding.net) taught at [HES-SO Valais / Systems engineering](http://www.hevs.ch) by Pierre-André Mudry. The framework was written P.-A. Mudry & N. Chatton with the help of C. Métrailler.