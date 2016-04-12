# Contributing

This is an open source project. Contributions via [pull request](https://github.com/hevs-isi/gdx2d/pulls), and [bug reports](https://github.com/hevs-isi/gdx2d/issues) are welcome! Please submit your pull request to the `develop` branch and use the GitHub issue tracker to report issues.

## Coding style

If you work on the gdx2d code, we require you to use the [Eclipse code formatter](https://github.com/hevs-isi/gdx2d/blob/master/gdx2d-formatter.xml) located in the root directory of the repository.
If you are using IntelliJ IDEA, [see this article](http://blog.jetbrains.com/idea/2014/01/intellij-idea-13-importing-code-formatter-settings-from-eclipse/) to import and use the Eclipse code formatter.

Please do use tabs, no spaces! Encoding of files should be defaulted to UTF-8. An [editor configuration file](https://github.com/hevs-isi/gdx2d/blob/master/.editorconfig) is also available.

## Publishing

The gdxd library is built using Maven and its modules are distributed as jar files since version `1.2.0`. Both artifacts `gdx2d-core` and `gdx2d-desktop` are published on Sonatype as [snapshots releases](https://oss.sonatype.org/content/repositories/snapshots/ch/hevs/gdx2d/) and official releases are published [on the Maven Central repository](http://search.maven.org/#search%7Cga%7C1%7Cch.hevs.gdx2d).

In order to deploy to Maven central, the project has to meet a number of requirements (publish sources, javadoc, sign files, etc.). All requirements [are detailed here](http://central.sonatype.org/pages/requirements.html).

Snapshot and release versions of the gdx2d library can be uploaded [to Sonatype](https://oss.sonatype.org/#nexus-search;quick~ch.hevs) using the Maven deploy task (`$ mvn clean deploy`). Please have a look [at this guide](http://central.sonatype.org/pages/apache-maven.html) to setup the environment and read [this guide](http://central.sonatype.org/pages/releasing-the-deployment.html) to publish a release version from OSSRH to the Maven Central Repository.