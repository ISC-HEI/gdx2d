# gdx2d library project

First compile the project. Compiled `.class` files will be available in the `target/classes` directory:

    $ mvn compile
    [INFO] Scanning for projects...
    [INFO] Building ch.hevs.gdx2d:gdx2d-lib 1.2-SNAPSHOT
    [INFO] BUILD SUCCESS

Then run the package goal to run tests and package the Java code in a `jar` package:

    $ mvn package
    [INFO] Scanning for projects...
    [INFO] Building ch.hevs.gdx2d:gdx2d-lib 1.2-SNAPSHOT
    [INFO] BUILD SUCCESS

The library is available in the `target` folder.