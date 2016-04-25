# gdx2d desktop demos

This project provides several demonstration programs and code examples made using the gdx2d library.
All the demos are destined to be run on desktop. A short video demonstrates the capabilities of the gdx2d library, [have a look at it here](https://youtu.be/eoVrifa1Xd0). The Javadoc of the gdx2d library [is available online](https://hevs-isi.github.io/gdx2d/javadoc/index.html).

## How to run ?

All the source of the demo are provided. You can choose a demo to run or you can use the demo selector window to see all of them.

### 1. Run from sources

In order to run a demo, run the `DemoSelector` class which provides a simple way to choose which demo you want to display. You can also run each demo individually. They are located in the `ch.hevs.gdx2d.demos` package. Each directory is aimed at a particular topic.

### 2. Using an `Ant` build script

The demo selector can be exported using the `Ant` build script. Available targets are:

```
clean          Clean all unused folders.
init           Init build folders.
compile        Compile all demos.
jar            Export all demos to a runnable jar.
run            Run demos using the compiled Jar file.

Default target: jar
```

To run the `DemoSelector` GUI, simply call `ant` in the project folder and then run the jar file in the `dist` folder.