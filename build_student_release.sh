#!/bin/sh
# Build a portable student release of gdx2d as a pre-configured IntelliJ
# project. The release is self-contained: all jars (including native
# libraries for Windows/macOS/Linux) are shipped in lib/, and the IntelliJ
# metadata is minimal and version-agnostic so any student setup works.
#
# gdx2d-game/ layout:
#   .idea/
#     modules.xml, misc.xml, libraries/*.xml   (minimal, no version locks)
#   lib/                                       (gdx2d-desktop + accordion jars)
#   data/                                      (resources: images, fonts, ...)
#   src/main/scala/                            (Scala sources from demoDesktop)
#   gdx2d-game.iml                             (module def, inheritedJdk)
#
# Deliberately NOT shipped (for maximum portability):
#   - project-jdk-name / languageLevel in misc.xml  (student picks)
#   - scala-sdk-X.Y.Z reference in .iml              (student picks)
#   - .idea/workspace.xml                            (per-user IDE state)
#   - .idea/scala_compiler.xml                       (plugin-version fragile)
#   - original-gdx2d-desktop-*.jar                   (unshaded duplicate)

set -e

# Build the fat jar first so the release carries the latest gdx2d-desktop
# (shaded with libgdx, box2d, freetype, native .so/.dylib/.dll).
mvn clean
cd gdx2d-library
mvn clean compile package
cd ..

# Wipe and rebuild the gdx2d-game folder from scratch.
rm -rf gdx2d-game
mkdir gdx2d-game

# Sources and resources
mkdir -p gdx2d-game/src/main
cp -r gdx2d-demoDesktop/src/main/scala gdx2d-game/src/main/
cp -r gdx2d-demoDesktop/data gdx2d-game/

# Libraries. Ship only what the classpath actually needs.
mkdir -p gdx2d-game/lib
cp gdx2d-library/gdx2d-desktop/target/gdx2d-desktop-1.2.2.jar           gdx2d-game/lib/
cp gdx2d-library/gdx2d-desktop/target/gdx2d-desktop-1.2.2-sources.jar   gdx2d-game/lib/
cp gdx2d-library/gdx2d-desktop/target/gdx2d-desktop-1.2.2-javadoc.jar   gdx2d-game/lib/
cp lib/accordion-1.2.0-jar-with-dependencies.jar                        gdx2d-game/lib/

# -----------------------------------------------------------------------------
# IntelliJ metadata (minimal, portable)
# -----------------------------------------------------------------------------
# All XML below uses heredocs with single-quoted markers so IntelliJ tokens
# like $PROJECT_DIR$ and $MODULE_DIR$ are written literally (no shell expansion).

mkdir -p gdx2d-game/.idea/libraries

# --- .idea/modules.xml: points at the single module file --------------------
cat > gdx2d-game/.idea/modules.xml <<'XML'
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="ProjectModuleManager">
    <modules>
      <module fileurl="file://$PROJECT_DIR$/gdx2d-game.iml" filepath="$PROJECT_DIR$/gdx2d-game.iml" />
    </modules>
  </component>
</project>
XML

# --- .idea/misc.xml: project-wide settings, no JDK name or language level ---
# Omitting project-jdk-name and languageLevel forces IntelliJ to show the
# "Project SDK is not defined" banner on first open, where the student
# picks any installed JDK. This is the whole point: no version lock.
cat > gdx2d-game/.idea/misc.xml <<'XML'
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="ProjectRootManager" version="2">
    <output url="file://$PROJECT_DIR$/out" />
  </component>
</project>
XML

# --- .idea/libraries/gdx2d_desktop.xml: main gdx2d library with sources/doc -
# Sources and javadoc are attached so "Go to declaration" and "Quick Doc"
# work inside IntelliJ for gdx2d classes out of the box.
cat > gdx2d-game/.idea/libraries/gdx2d_desktop.xml <<'XML'
<component name="libraryTable">
  <library name="gdx2d-desktop">
    <CLASSES>
      <root url="jar://$PROJECT_DIR$/lib/gdx2d-desktop-1.2.2.jar!/" />
    </CLASSES>
    <JAVADOC>
      <root url="jar://$PROJECT_DIR$/lib/gdx2d-desktop-1.2.2-javadoc.jar!/" />
    </JAVADOC>
    <SOURCES>
      <root url="jar://$PROJECT_DIR$/lib/gdx2d-desktop-1.2.2-sources.jar!/" />
    </SOURCES>
  </library>
</component>
XML

# --- .idea/libraries/accordion.xml: accordion demo selector library --------
cat > gdx2d-game/.idea/libraries/accordion.xml <<'XML'
<component name="libraryTable">
  <library name="accordion">
    <CLASSES>
      <root url="jar://$PROJECT_DIR$/lib/accordion-1.2.0-jar-with-dependencies.jar!/" />
    </CLASSES>
    <JAVADOC />
    <SOURCES />
  </library>
</component>
XML

# --- gdx2d-game.iml: single JAVA_MODULE, inheritedJdk, no Scala SDK ref ----
# inheritedJdk means "use whatever Project SDK the student picks". Omitting
# any scala-sdk-* orderEntry means IntelliJ's Scala plugin will prompt the
# student to attach a Scala SDK on first open -- any 2.13.x works.
cat > gdx2d-game/gdx2d-game.iml <<'XML'
<?xml version="1.0" encoding="UTF-8"?>
<module type="JAVA_MODULE" version="4">
  <component name="NewModuleRootManager" inherit-compiler-output="true">
    <exclude-output />
    <content url="file://$MODULE_DIR$">
      <sourceFolder url="file://$MODULE_DIR$/src/main/scala" isTestSource="false" />
      <sourceFolder url="file://$MODULE_DIR$/data" type="java-resource" />
    </content>
    <orderEntry type="inheritedJdk" />
    <orderEntry type="sourceFolder" forTests="false" />
    <orderEntry type="library" name="gdx2d-desktop" level="project" />
    <orderEntry type="library" name="accordion" level="project" />
  </component>
</module>
XML

echo "Student workflow:"
echo "  1. File > Open, select the gdx2d-game/ folder"
echo "  2. 'Project SDK is not defined' banner -> pick any JDK 8+"
echo "  3. 'Set up Scala SDK' prompt          -> pick any Scala 2.13.14+"
echo "  4. Right-click any object with main() -> Run"
