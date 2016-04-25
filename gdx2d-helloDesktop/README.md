# Hello gdx2d

Desktop bootstrap project based on the latest gdx2d library. The project is ready to use in Eclipse or IntelliJ IDE. It uses the `gdx2d-desktop` library version `1.2.1`.

The project can be imported directly in Eclipse. The `gdx2d` sources are available in the library Jar file (in the `libs` folder). The Javadoc API of the library can be found [here](https://hevs-isi.github.io/gdx2d/javadoc/).

Java 1.6 or higher is required to run this project. An `Ant` build script is provided to export the project as a runnable Jar (available in the `dist` folder).

To export the project, you can use the provided Ant script. Run `$ ant jar` and then `$ ant run` to launch it. The main class of the project must be configured in the `build.properties` file. The source code of the project are also included in the exported Jar.
