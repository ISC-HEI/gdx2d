# Welcome to gdx2d [![Build Status](https://travis-ci.org/hevs-isi/gdx2d.svg?branch=master)](https://travis-ci.org/hevs-isi/gdx2d)

The gdx2d project is a simple to use 2d game and graphics framework. It is multi-platform (working on Windows, Linux and Android). It is Java based with the heavy-lifting done in native code (i.e. C/C++), for example for physics rendering. 

<p align="center">
  <a href="https://www.youtube.com/watch?v=eoVrifa1Xd0" target="_blank"><img src="https://rawgit.com/hevs-isi/gdx2d-videofile/master/logo/logo_640.png?raw=true" alt="Logo"/></a>
</p>

More information about gdx2d can be found on the [official project website](https://hevs-isi.github.io/gdx2d/).

## What can it do?
The library contains simple to use graphical primitives such as lines, circles, rectangles. In addition, there are methods to draw pictures, rotate them, scale them etc. Physics simulation is also supported (using Box2D) as well as music and sound playing.

For each feature, a Java demo is provided for simple integration. Here are some examples of what the library is capable of (you can check the [YouTube demo reel](https://www.youtube.com/watch?v=eoVrifa1Xd0)):

![Screen shot](https://raw.github.com/wiki/pmudry/gdx2d/multi_screenshot.png)

## Why has this framework been developed?
The advantage of using gdx2d is that you can develop your code in Java on your standard desktop computer like any other Java application and then deploy the same code, with a single click, on an Android phone or tablet. Of course, you have to take into consideration the fact that some of your code is a bit different for Android (for instance, you do not have accelerometers on your PC). 

The library itself is a wrapper around a very nice library called [libGDX](https://libgdx.badlogicgames.com/) which provides almost everything to the library. The current project version is based on libGDX version `1.5.6`.

## Documentation
The Javadoc API of this library can be found [here](https://hevs-isi.github.io/gdx2d/javadoc/). More information about the project can be found on the [official website](https://hevs-isi.github.io/gdx2d/).

## What do I get?
It is multi-platform so that you can run your code on desktop computers (running either Windows, Linux or Mac) but also on mobile devices running Android. It is based on libGDX but provides a much simpler interface and multiple demo programs to get started.

Using the framework, the following code displays a shrinking/growing circle running smoothly:

```java
package hevs.gdx2d.demos.simple;

import com.badlogic.gdx.Gdx;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import com.badlogic.gdx.graphics.Color;

/**
 * A very simple demonstration on how to display something animated with the
 * library
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSimpleAnimation extends PortableApplication {
    int radius = 5, speed = 1;
    int screenHeight, screenWidth;

    @Override
    public void onInit() {
        // Sets the window title
        setTitle("Simple demo, mui 2013");

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void onGraphicRender(GdxGraphics g) {

        // Clears the screen
        g.clear();
        g.drawAntiAliasedCircle(screenWidth / 2, screenHeight / 2, radius, Color.BLUE);

        // If reaching max or min size, invert the growing direction
        if (radius >= 100 || radius <= 3) {
            speed *= -1;
        }

        // Modify the radius
        radius += speed;

        g.drawSchoolLogo();
    }

    public static void main(String[] args) {
        new DemoSimpleAnimation();
    }
}
```

## Installing the code
The library consists of two Eclipse projects (one for Android, one for desktop) that can be imported easily. Once downloaded, an archive can be imported in Eclipse using `File->Import->Existing project into workspace`. You can then either give the path of the archive or extract the data manually and then point to that directory.

`gdx2d-desktop`

This project contains the library as well as all the demo programs. All the demos can be run on PC or on Android. In order to run a demo, either use the `DemoSelector` class which provides a simple way to choose which demo to run or run each demo individually.

`gdx2d-android`

This is the Android counterpart of the library. This project depends on the `gdx2d-desktop` project, which means that this other project must be available in Eclipse. In addition, most of the files that will be executed come from that project as well. As such, it mainly consists of a Java file `GameActivity.java` which determines which is the main program that will be launched on Android.

## Who did this and why?
It was developped for the [inf1 course](http://inf1.begincoding.net) taught at [HES-SO Valais / Systems engineering](http://hevs.ch/isi) by Pierre-André Mudry. The framework was written by P.-A. Mudry & N. Chatton with the help of C. Métrailler. The list of contributors is [available here](https://github.com/hevs-isi/gdx2d/graphs/contributors).

## License
The gdx2d project is licensed under the [Apache 2.0 license](https://github.com/hevs-isi/gdx2d/blob/master/LICENSE).