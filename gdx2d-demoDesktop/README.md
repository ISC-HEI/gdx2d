# gdx2d library and desktop demos

This project contains the library as well as all the demo programs. All the demos in this folder are destined to be run on desktop.

## How to run ?

1. From sources

    In order to run a demo, either use the `DemoSelector` class which provides a simple way to choose which demo to run. You can also run each demo, located in the `Demos` directory individually.

2. Using an `Ant` build script

    Demos, library and documentation (javadoc) can be easily exported using the `Ant` build script. Available targets are :

        clean          Clean all unused folders.
        demos-compile  Compile the library with all demos.
        demos-jar      Export all demos to a runnable jar.
        demos-run      Run demos using the compiled Jar file.
        init           Init build folders.
        lib-compile    Compile the gdx2d library
        lib-doc        Generate the library documentation (javadoc).
        lib-jar        Export the gdx2d library only.
    
        Default target: demos-jar

    To run the `DemoSelector` GUI, simply call `ant` in the project folder and then run the jar file in the `dist` folder.

The library API is available online: [http://hevs-isi.github.io/gdx2d/javadoc/1.0.0/]
