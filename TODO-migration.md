# gdx2d migration to libgdx 1.14.0 + Scala

This document tracks the multi-phase migration of gdx2d from
libgdx 1.9.8 (Kotlin + Android + LWJGL2) to libgdx 1.14.0
(Scala + desktop-only + LWJGL3).

## Phase status

- [x] **Phase 0** — Park non-desktop / non-Scala code, introduce Scala toolchain, Scala hello smoke test, bump `maven-shade-plugin` 2.4.2 → 3.5.0, bump `maven-javadoc-plugin` 2.10.3 → 3.6.3 (fixes JDK 24 compatibility)
- [x] **Phase 1** — Strip Android code from `gdx2d-library`, rename `PortableApplication` → `DesktopApplication`, rename `TouchInterface` → `MouseInterface`
- [ ] **Phase 2** — Drop `GestureInterface` entirely
- [ ] **Phase 3** — Bump libgdx 1.9.8 → 1.14.0, migrate LWJGL2 → LWJGL3, bump box2dlights to 1.5
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

## Phase 3 checklist (libgdx bump)

- [ ] `<libgdx.version>1.9.8</libgdx.version>` → `1.14.0` in `gdx2d-library/pom.xml`
- [ ] Bump box2dlights to `1.5` in `gdx2d-library/pom.xml`
- [ ] Replace `gdx-backend-lwjgl` with `gdx-backend-lwjgl3` in `gdx2d-library/gdx2d-desktop/pom.xml`
- [ ] Rewrite `GdxConfig.java`: `LwjglApplicationConfiguration` → `Lwjgl3ApplicationConfiguration` with setter API (`setWindowedMode`, `useVsync`, `setForegroundFPS`, `setIdleFPS`, `setBackBufferConfig` for samples, `setWindowIcon`)
- [ ] Rewrite `DesktopApplication.createLwjglApplication`: `LwjglApplication` → `Lwjgl3Application`
- [ ] Fix `GdxInputProcessor.scrolled(int)` → `scrolled(float amountX, float amountY)`; forward `Math.round(amountY)`
- [ ] Keep the `CreateLwjglApplication` static flag name for student-code continuity (still needed by future Swing-integration demos)
- [ ] If `maven-shade-plugin` 3.5.0 + libgdx 1.14 fat-jar fails on service file conflicts, add `META-INF/services/*` merger transformer
