# Contributing

This is an open source project. Contributions via [pull request](https://github.com/hevs-isi/gdx2d/pulls), and [bug reports](https://github.com/hevs-isi/gdx2d/issues) are welcome! Please submit your pull request to the `develop` branch and use the GitHub issue tracker to report issues.

## Intellij import

- Import project
- Select the root of the gdx2d clone
- Press next until the end of the wizard

## Jars generation

- 'mvn package' in the root folder

## Student project generation

- './student.sh' in the root folder
- Take the generated student folder

## Release

1. Create a release branch from the develop branch to start a release (see the [Gitflow Workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow)).
2. Update the <gdx2d.version>x.y.z</gdx2d.version> properties in ./pom.xml and ./gdx2d-library/pom.xml
3. Update the `CHANGELOG.md`, `README.md` files and guides before releasing a new version. These files are included in the released jar files.
4. Merge the branch into master and tag it
5. Add the jars and the student project on the github
 
## Maven scripts modifications (pom.xml)

If you do so, you will have to refresh intellij by : 

- Opening the view/Tool Windows/Maven
- Click on the 'Reimport All Maven Projects' button (the recycle icon)

Note that in case you have a broken build script, the intellij maven error reporting isn't very useful.
It is better to just run some maven commands in the shell to get a better insight (ex : mvn clean compile).

## Coding style

If you work on the gdx2d code, we require you to use the [Eclipse code formatter](https://github.com/hevs-isi/gdx2d/blob/master/gdx2d-formatter.xml) located in the root directory of the repository.
If you are using IntelliJ IDEA, [see this article](http://blog.jetbrains.com/idea/2014/01/intellij-idea-13-importing-code-formatter-settings-from-eclipse/) to import and use the Eclipse code formatter.

Please do use tabs, no spaces! Encoding of files should be defaulted to UTF-8. An [editor configuration file](https://github.com/hevs-isi/gdx2d/blob/master/.editorconfig) is also available.

## Java -> Kotlin

In intellij, you can right click on a java file and ask to translate it into Kotlin. It generally work well.

Kotlin has a few noticeable characteristics : 
- lateinit / null safety
- No single line multiple declaration
- No lateinit on non-nullable stuff (int, ...)
- No implicit conversions of numeric type (ex : no int to float), but you can do Float * Int
- Code written outside a class (ex: main) will be stored as static elements of a 'FileName'Kt class
- Things are public by default
