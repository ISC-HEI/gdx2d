---
layout: page
permalink: "index.html"
title: Welcome
---

The gdx2d project is a simple to use 2d game and graphics framework. It is multi-platform (working on Windows, Linux and Android). It is Java based with the heavy-lifting done in native code (i.e. C/C++), for example for physics rendering.

<div style="text-align:center">
  <a href="https://www.youtube.com/watch?v=eoVrifa1Xd0" target="_blank">
    <img style="margin-left:auto;margin-right:auto;" src="{{ site.baseurl }}/assets/img/logo_640.png" alt="Logo"/>
  </a>
</div>

## What can it do?

The library contains simple to use graphical primitives such as lines, circles, rectangles. In addition, there are methods to draw pictures, rotate them, scale them etc. Physics simulation is also supported (using Box2D) as well as music and sound playing.

For each feature, a Java demo is provided for simple integration. Here are some examples of what the library is capable of (you can check the [YouTube demo reel](https://www.youtube.com/watch?v=eoVrifa1Xd0)):

<div style="text-align:center">
  <img style="margin-left:auto;margin-right:auto;" src="{{ site.baseurl }}/assets/doc/screenshot.png" alt="Screenshots"/>
</div>

## Why has framework been developed?

The advantage of using *gdx2d* is that you can develop your code in Java on your standard desktop computer like any other Java application and then deploy the same code, with a single click, on an Android phone or tablet. Of course, you have to take into consideration the fact that some of your code is a bit different for Android (for instance, you do not have accelerometers on your PC). 

The library itself is a wrapper around a very very nice library called [libgdx](https://libgdx.badlogicgames.com/) which provides almost everything to the library.

## Documentation

The Javadoc API of the library can be found [here](https://hevs-isi.github.io/gdx2d/javadoc/).

## What do I get?

It is multi-platform so that you can run your code on desktop computers (running either Windows, Linux or Mac) but also on mobile devices running Android. It is based on ´libgdx´ but provides a much simpler interface and multiple demo programs to get started.

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

    public DemoSimpleAnimation(boolean onAndroid) {
        super(onAndroid);
    }

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
        /**
         * Note that the constructor parameter is used to determine if running
         * on Android or not. As we are in main there, it means we are on
         * desktop computer.
         */
        new DemoSimpleAnimation(false);
    }
}
```

## Who did this and why?

It was developped for the [inf1 course](http://inf1.begincoding.net) taught at [HES-SO Valais / Systems engineering](http://www.hevs.ch/isi) by Pierre-André Mudry. The framework was written P.-A. Mudry & N. Chatton with the help of C. Métrailler.

## Installing the code

The library consists of two Eclipse projects (one for Android, one for desktop) that can be imported easily. Once downloaded, an archive can be imported in Eclipse using `File->Import->Existing project into workspace`. You can then either give the path of the archive or extract the data manually and then point to that directory.

`gdx2d-desktop`

This project contains the library as well as all the demo programs. All the demos can be run on PC or on Android. In order to run a demo, either use the `DemoSelector` class which provides a simple way to choose which demo to run or run each demo individually.

`gdx2d-android`

This is the Android counterpart of the library. This project depends on the gdx2d-desktop project, which means that this other project must be available in Eclipse. In addition, most of the files that will be executed come from that project as well. As such, it mainly consists of a Java file `GameActivity.java` which determines which is the main program that will be launched on Android.