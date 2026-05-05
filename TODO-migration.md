# gdx2d migration to libgdx 1.14.0 + Scala

This document tracks the multi-phase migration of gdx2d from
libgdx 1.9.8 (Kotlin + Android + LWJGL2) to libgdx 1.14.0
(Scala + desktop-only + LWJGL3).

## Phase status

- [x] **Phase 0** — Park non-desktop / non-Scala code, introduce Scala toolchain, Scala hello smoke test, bump `maven-shade-plugin` 2.4.2 → 3.5.0, bump `maven-javadoc-plugin` 2.10.3 → 3.6.3 (fixes JDK 24 compatibility)
- [x] **Phase 1** — Strip Android code from `gdx2d-library`, rename `PortableApplication` → `DesktopApplication`, rename `TouchInterface` → `MouseInterface`
- [x] **Phase 2** — Drop `GestureInterface` entirely (deleted `GestureInterface.java`, `GdxGestureDetector.java`, and 6 gesture callback stubs from `DesktopApplication` + `RenderingScreen`; removed `GestureDetector` registration from `Game2D`)
- [x] **Phase 3** — Bump libgdx 1.9.8 → 1.14.0, migrate LWJGL2 → LWJGL3, bump box2dlights to 1.5, fix scrolled() signature, fix `com.badlogic.gdx.utils.StringBuilder` removal, add macOS `-XstartOnFirstThread` auto-restart helper
- [ ] **Phase 4** — Scala port of hello/demos, Scala rewrite of `DemoSelectorGUI`
- [ ] **Phase 5** — Port demos one by one

## Parked code (needs porting / rewrite later)

### Kotlin sources

Moved out of the compiled source trees to `src_to_do/` so they are
preserved as reference material for the Scala rewrite.

- `gdx2d-helloDesktop/src_to_do/main/kotlin/` — contains `HelloKotlin.kt`
- `gdx2d-demoDesktop/src_to_do/main/kotlin/` — contains all demos and the
  demo selector GUI (`ch/hevs/gdx2d/demos/selector/DemoSelectorGUI.kt`,
  etc.)

When Phase 4/5 is executed, each `.kt` file is rewritten as `.scala`
and the corresponding parked file is deleted.

### Controllers support

Gamepad support was built on the old `com.badlogicgames.gdx:gdx-controllers:1.9.x`
artifact, which has no 1.14-compatible release. The replacement is
`com.badlogicgames.gdx-controllers:gdx-controllers-desktop:2.2.3` with
a rewritten API:

- `PovDirection` is removed; d-pad is exposed as regular buttons.
- `ControllerAdapter.povMoved/xSliderMoved/ySliderMoved/accelerometerMoved`
  are removed.
- Button/axis IDs come from `controller.getMapping()`, making the
  hard-coded `Xbox` constants redundant.

The following files are parked under `src_to_do/` and must be
rewritten (or dropped) if gamepad support comes back:

- `gdx2d-library/gdx2d-desktop/src_to_do/main/java/ch/hevs/gdx2d/desktop/Xbox.java`
- `gdx2d-library/gdx2d-desktop/src_to_do/main/java/ch/hevs/gdx2d/desktop/GdxControllersProcessor.java`
- `gdx2d-library/gdx2d-core/src_to_do/main/java/ch/hevs/gdx2d/lib/interfaces/ControllersInterface.java`
- `gdx2d-demoDesktop/src_to_do/main/kotlin/ch/hevs/gdx2d/demos/controllers/DemoControllers.kt`

The `gdx-controllers*` Maven dependencies have been removed from
both `gdx2d-library/pom.xml` and `gdx2d-library/gdx2d-desktop/pom.xml`.
The `onController*` callbacks have been removed from
`PortableApplication.java`.

## Phase 1 changes (done)

- Deleted `gdx2d-library/gdx2d-core/src/main/java/ch/hevs/gdx2d/lib/interfaces/AndroidResolver.java`
- Deleted old `PortableApplication.java`; replaced with `DesktopApplication.java` (simpler: no Android detection, no `onAndroid` field, no deprecated `boolean onAndroid` constructors, no `getAndroidResolver()`/`setAndroidResolver()`, no `Gdx.input.vibrate(...)` call, no controllers hooks)
- Renamed `TouchInterface` → `MouseInterface` (rewrote javadoc to drop Android notes)
- Updated references across `Game2D`, `GdxInputProcessor`, `GdxGestureDetector`, `RenderingScreen`, `Utils`, `GdxGraphics` (javadoc), `HelloScala`
- Removed the stale `HelloWorld.java` that lived under `gdx2d-helloDesktop/src/main/java/` — helloDesktop is a Scala-only module now.

The `DesktopApplication.CreateLwjglApplication` static boolean is
preserved for future demo-selector / Swing-integration compatibility.
Kotlin sources parked in `src_to_do/` reference this under the old
name `PortableApplication.CreateLwjglApplication`; the Scala rewrites
in Phase 4/5 must use `DesktopApplication.CreateLwjglApplication`.

## Phase 3 changes (done)

- Bumped `<libgdx.version>` from 1.9.8 to **1.14.0** in `gdx2d-library/pom.xml`
- Bumped box2dlights from 1.4 to **1.5**
- Replaced `gdx-backend-lwjgl` with `gdx-backend-lwjgl3` in `gdx2d-library/gdx2d-desktop/pom.xml`
- Rewrote `GdxConfig.java` to use `Lwjgl3ApplicationConfiguration` (`setResizable`, `useVsync`, `setForegroundFPS`, `setIdleFPS`, `setBackBufferConfig` for samples, `setTitle`, `setFullscreenMode`/`setWindowedMode`, `setWindowIcon`).
- Rewrote `DesktopApplication.createLwjglApplication` to instantiate `Lwjgl3Application` with `Lwjgl3ApplicationConfiguration`.
- Fixed `GdxInputProcessor.scrolled(int)` → `scrolled(float amountX, float amountY)`; forwards `Math.round(amountY)` to keep `MouseInterface.onScroll(int)` stable.
- Removed now-removed `com.badlogic.gdx.utils.StringBuilder` import from `ShaderRenderer.java` (the JDK one is resolved automatically).
- Added `startNewJvmIfRequired()` helper to `DesktopApplication`: on macOS, if the JVM was not launched with `-XstartOnFirstThread`, spawn a child JVM with the flag and wait for it. This is required because LWJGL3's GLFW must run on the first thread on macOS.

## Phase 3 runtime notes

The Scala hello runs on arm64 macOS with `libgdx 1.14.0` — verified
by launching `gdx2d-helloDesktop-1.2.2.jar`: window opens and the log
prints `libgdx v1.14.0`. Two harmless LWJGL warnings appear on modern
JDKs (`Unsupported JNI version`, `sun.misc.Unsafe` deprecation); they
are JVM-side issues that LWJGL will address in future releases. A
shader linker warning (`vTexCoord/vSurfacePosition not read by
fragment shader`) also appears — it comes from gdx2d's own shaders in
`res/lib/fragment_include.glsl` and was always there; modern GLSL
compilers are more vocal about it.
