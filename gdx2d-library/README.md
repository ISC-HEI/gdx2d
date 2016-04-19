# gdx2d library

The gdx2d library project contains two modules:

1. The first module is the `core` library. It is not platform dependent.
2. The second module is the `desktop` native back end needed to run `gdx2d` on a PC. It uses the core module as a dependency.

At this time, the Android back end is not available. The current library implementation is based on Libgdx version `1.5.6`.

## Download

Download the latest JAR or grab it via Maven:

```xml
<dependency>
  <groupId>ch.hevs.gdx2d</groupId>
  <artifactId>gdx2d-desktop</artifactId>
  <version>1.2.0</version>
</dependency>
```
Or via Gradle:

```gradle
compile 'ch.hevs.gdx2d:gdx2d-desktop:1.2.0'
```

The snapshots artifacts are available in the [Sonatype snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/ch/hevs/gdx2d/) and official releases are available on [the Maven Central repository](http://search.maven.org/#search%7Cga%7C1%7Cch.hevs.gdx2d).

Group           | Artifact        | Versions              | Download
:---------------|:----------------|:----------------------|:--------
`ch.hevs.gdx2d` | `gdx2d-parent`  | pom                   | 1.0
`ch.hevs.gdx2d` | `gdx2d-lib`     | pom                   | 1.0
`ch.hevs.gdx2d` | `gdx2d-core`    | jar, sources, javadoc | [1.2.0][core]
`ch.hevs.gdx2d` | `gdx2d-desktop` | jar, sources, javadoc | [1.2.0][desktop]

[core]: http://search.maven.org/#artifactdetails%7Cch.hevs.gdx2d%7Cgdx2d-core%7C1.2.0%7Cjar
[desktop]: http://search.maven.org/#artifactdetails%7Cch.hevs.gdx2d%7Cgdx2d-desktop%7C1.2.0%7Cjar

## Building from sources

The core library and the desktop library can be compiled from the sources using Maven:

    $ mvn clean package
    [INFO] Scanning for projects...
    [INFO] Building ch.hevs.gdx2d:gdx2d-lib 1.2.0
    [INFO] BUILD SUCCESS

The generated files are available in the `target` folder (jar file, sources and javadoc).

## Library resources

All resources used by the library are available in the [gdx2d-core/src/main/resources/lib/](gdx2d-core/src/main/resources/lib/) folder.
These resources are shared for every target and included in the library `jar` for every projects, so please keep this folder light.

### Available resources

1. `GdxConfig`
    * icon16.png (Windows only)
    * icon32.png
    * icon64.png

2. `GdxGraphics`
    * logo_hes.png
    * circle.png
    * RobotoSlab-Regular.ttf (default font)

3. `ShaderRenderer`
    * fragment_include.glsl
    * default.vs

4. `CircleShaderRenderer`
    * circle_aa.fp

### How to use resources

Resources needed by the library are copied to the `res/lib` folder in the final `jar` file.
To load an internal file or resource, you can do for instance:

```java
FileHandle fh = Gdx.files.internal("res/lib/MyResource.bin");
BitmapImage logo = new BitmapImage("res/lib/icon64.png");
```

## License

The gdx2d project is licensed under the [Apache 2.0 license](https://github.com/hevs-isi/gdx2d/blob/master/LICENSE).