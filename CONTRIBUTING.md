# Contributing

This is an open source project. Contributions via [pull request](https://github.com/hevs-isi/gdx2d/pulls), and [bug reports](https://github.com/hevs-isi/gdx2d/issues) are welcome! Please submit your pull request to the `develop` branch and use the GitHub issue tracker to report issues.

## Coding style

If you work on the gdx2d code, we require you to use the [Eclipse code formatter](https://github.com/hevs-isi/gdx2d/blob/master/gdx2d-formatter.xml) located in the root directory of the repository.
If you are using IntelliJ IDEA, [see this article](http://blog.jetbrains.com/idea/2014/01/intellij-idea-13-importing-code-formatter-settings-from-eclipse/) to import and use the Eclipse code formatter.

Please do use tabs, no spaces! Encoding of files should be defaulted to UTF-8. An [editor configuration file](https://github.com/hevs-isi/gdx2d/blob/master/.editorconfig) is also available.

## Import project in eclipse

```sh
cd rootOfThisRepository
mvn clean package
- import/Existing Projects into Workspace/   Don't use the maven import.
- Select the root of the gdx2d clone
- Enable "Search for nested projects"
- Finish
```

Note : sub projects like gdx2d-demoDesktop are using jar generated from the "mvn clean package".


## Update gdx2d-demoDesktop library jars from gdx2d-library

Assumption : 

- gdx2d-demoDesktop/build.properties/gdx2d.version=1.2.2-SNAPSHOT 
- gdx2d-library/gdx2d-core/src/main/java/ch/hevs/gdx2d/lib/Version.java/Version/VERSION=1.2.2-SNAPSHOT

```sh
cd rootOfThisRepository
mvn clean package
cp gdx2d-library/gdx2d-desktop/target/gdx2d-desktop-1.2.2-SNAPSHOT.jar gdx2d-demoDesktop/libs/gdx2d-desktop-1.2.2-SNAPSHOT.jar
cp gdx2d-library/gdx2d-desktop/target/gdx2d-desktop-1.2.2-SNAPSHOT-sources.jar gdx2d-demoDesktop/libs/gdx2d-desktop-1.2.2-SNAPSHOT-sources.jar
```


## Publishing

The gdxd library is built using Maven and its modules are distributed as jar files since version `1.2.0`. Both artifacts `gdx2d-core` and `gdx2d-desktop` are published on Sonatype as [snapshots releases](https://oss.sonatype.org/content/repositories/snapshots/ch/hevs/gdx2d/) and official releases are published [on the Maven Central repository](http://search.maven.org/#search%7Cga%7C1%7Cch.hevs.gdx2d).

In order to deploy to Maven central, the project has to meet a number of requirements (publish sources, javadoc, sign files, etc.). All requirements [are detailed here](http://central.sonatype.org/pages/requirements.html).

Snapshot and release versions of the gdx2d library can be uploaded [to Sonatype](https://oss.sonatype.org/#nexus-search;quick~ch.hevs) using the Maven deploy task (`$ mvn clean deploy`). Please have a look [at this guide](http://central.sonatype.org/pages/apache-maven.html) to setup the environment and read [this guide](http://central.sonatype.org/pages/releasing-the-deployment.html) to publish a release version from OSSRH to the Maven Central Repository.

### Publish gdx2d snapshot

Snapshots are automatically deployed from the `develop` branch to the [Sonatype repository](https://oss.sonatype.org/content/repositories/snapshots/ch/hevs/gdx2d/) using Travis (valid Sonatype OSS username and password is required).

1. Install the [Travis CI Client (CLI and Ruby library)](https://github.com/travis-ci/travis.rb)
2. [Encrypt](https://docs.travis-ci.com/user/encryption-keys/) your Sonatype OSS password with `travis encrypt "CI_DEPLOY_PASSWORD='<my-password>'" --add`
3. Add your Sonatype OSS login with `CI_DEPLOY_USERNAME='<my-username>'` in the `.travis.yml` file

Deployment will only happen when the build was successful and the encrypted variables are available. If the deployment itself was unsuccessful, then the build still passes.

### New gdx2d release

Before releasing any gdx2d version, please:

1. Create a release branch from the develop branch to start a release (see the [Gitflow Workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow)).
2. Update the `Version.java` file in the `gdx2d-core` project. The version name and the `isSnapshot` must be set correctly. Output debug information must be disabled for official releases.
3. Update the `CHANGELOG.md`, `README.md` files and guides before releasing a new version. These files are included in the released jar files.
4. Install the Maven artifacts locally. In the generated Jar file, check the Manifest and all included files. All artifacts must be signed (be sure you have installed and configured `gpg` [as described here](http://central.sonatype.org/pages/working-with-pgp-signatures.html)). The sources and the Javadoc must also be released.
5. Publish and test a `SNAPSHOT` releases first.
6. Finally, when ready, publish the final release to the Maven Central repository.
7. Close the current release branch. Merge into develop and master and add a tag for the latest version.
8. Prepare for the next `SNAPSHOT` by incrementing the version number.

After a release, both projects `gdx2d-demoDesktop` and `gdx2d-helloDesktop` must be updated to the latest gdx2d version manually.
