---
layout: page
permalink: doc/how-to/fr/
weight: 1
title: Comment utiliser la librairie ?
---

## Introduction

*Gdx2d* est une librairie graphique 2D qui permet de créer facilement des jeux pour différentes plateformes, comme Windows, Linux ou Android. Cette librairie Java propose un grand nombre de fonctionnalités qui facilitent le développement de jeux. Les différents composants disponibles dans cette librairie sont présentés brièvement dans ce document.

Voici quelques exemples d’applications développées à l’aide de *Gdx2d*. Différentes applications de démonstration (ainsi que le code correspondant) sont disponibles dans l’application DemoSelector.

<center>
    <img alt="Screenshots" src="{{site.url}}images/doc/screenshot.png">
</center>

## Hello World !

Les applications développées pour *Gdx2d* doivent hériter de la classe `PortableApplication`. Le code ci-dessous présente une application de démonstration basique :

```java
package hevs.gdx2d.demos.simple;

import com.badlogic.gdx.Gdx;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import com.badlogic.gdx.graphics.Color;

/**
 * A very simple demonstration on how to display something animated with the
 * library
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class DemoSimpleAnimation extends PortableApplication {
    int radius = 5, speed = 1;
    int screenHeight, screenWidth;

    public DemoSimpleAnimation(boolean onAndroid) {
        super(onAndroid);
    }

    @Override
    public void onInit() {
        // Sets the window title
        setTitle("Simple demo, mui 2013");

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void onGraphicRender(GdxGraphics g) {

        // Clears the screen
        g.clear();
        g.drawAntiAliasedCircle(screenWidth / 2, screenHeight / 2, radius, Color.BLUE);

        // If reaching max or min size, invert the growing direction
        if (radius >= 100 || radius <= 3) {
            speed *= -1;
        }

        // Modify the radius
        radius += speed;

        g.drawSchoolLogo();
    }

    public static void main(String[] args) {
        /**
         * Note that the constructor parameter is used to determine if running
         * on Android or not. As we are in main there, it means we are on
         * desktop computer.
         */
        new DemoSimpleAnimation(false);
    }
}

```

Le code ci-dessus correspond à l’application suivante :

<center>
    <img alt="Hello world application" src="{{site.url}}images/doc/hello-world-app.png" width="250" />
</center>

## Cycle de vie d’une application *Gdx2d*

Les applications *Gdx2d* (classe `PortableApplication`) ne disposent pas uniquement d’une fonction `main` comme les programmes Java standards. Des méthodes spécifiques sont appelées lorsque l’application est initialisée ou lorsqu’elle doit être mise à jour. Elles sont résumées à l’aide du diagramme suivant et détaillées dans le tableau ci-dessous.

<center>
    <img alt="Application lifecycle" src="{{site.url}}images/doc/llifecycle.png" width="600">
</center>

Le cycle de vie d’une application *Gdx2d* diffère légèrement entre une exécution sur Pc et sur Android. Les méthodes de l’interface `GameInterface` sont détaillées ci-dessous :

| Méthode               | Description      |
|-----------------------|------------------|
| `onInit`              | Méthode appelée lors de l’initialisation d’application (appelée une seule fois au démarrage). Elle permet d’initialiser les objets de l’application ainsi que charger les ressources (images, sons, etc.). Les ressources doivent être chargées une seule fois au démarrage et non pas périodiquement dans la fonction `onGraphicRender`. |
| `onGraphicRender`     | Cette méthode est appelée périodiquement, lorsque le rendu graphique de l’application doit être mis à jour (60 fois par seconde). L’objet `GdxGraphics` passé en paramètre de cette méthode permet de réaliser toutes les opérations graphiques (dessin de formes, d’images, de textes, etc.). |
| `onGameLogicUpdate`   | Appelée lorsque la physique/logique du jeu est mise à jour. |
| `onPause`             | Sur PC, cette méthode est appelée immédiatement avant `onDispose`, lorsque l’on quitte l’application. Sur Android, cette méthode est appelée lorsque une application prioritaire prend la main (exemple : un appel téléphonique qui met en pause l’application) ou lorsque l’on appuie sur le bouton "Home". |
| `onResume` | Méthode appelée uniquement sur Android lorsque l’application revient au premier plan (après une interruption). |
| `onDispose`           | Méthode appelée lorsque l’application est détruite. |

## Diagramme de classe

Afin d’interagir avec les applications *Gdx2d*, différentes interfaces sont à disposition. Elles permettent par exemple de détecter les clics de souris, les pressions de touches du clavier ou encore d’utiliser l’écran tactile sur Android. Toutes les applications *Gdx2d* disposent des méthodes suivantes, qui peuvent être surchargées selon les besoins de chaque application.

<center>
    <img alt="PortableApplication class diagram" src="{{site.url}}images/doc/class-diag.png" width="450">
</center>

## Opérations de dessin

La méthode onGraphicRender est appelée périodiquement (60 fois par seconde) afin de mettre à jour le rendu (le dessin) de l’application. La scène doit d’abord être effacée, puis les objets qui composent l’application doivent être dessinés les uns après les autres, en sachant que les objets dessinés en dernier peuvent recouvrir les objets précédemment dessinés.

```java
@Override
public void onGraphicRender(GdxGraphics g) {
    g.clear(Color.BLACK);

    g.setColor(Color.RED);

    // TODO: add all drawing stuff here...

    g.drawSchoolLogo();
    g.drawFPS();
}
```

La classe `GdxGraphics` met à disposition un nombre varié de méthodes qui peuvent être utilisées pour afficher des images, dessiner des formes, afficher du texte, etc. La Javadoc de la classe `GdxGraphics` contient une description détaillée des méthodes disponibles.

## Utilisation des ressources (images, son, etc.)

Les classes `BitmapImage`, `FileHandle`, `MusicPlayer` et `Spritesheet` gèrent les ressources de la même manière. Pour être utilisées dans votre application, elles doivent être placées dans le dossier "data/". Vous pouvez les classer selon vos besoin dans ce dossier.

### Affichage d’une image

```java
BitmapImage imgBitmap;

@Override
public void onInit() {
  imgBitmap = new BitmapImage("data/images/compass_150.png");  
}

@Override
public void onGraphicRender(GdxGraphics g) {
    g.clear();              
    g.drawPicture(getWindowWidth()/2, getWindowHeight()/2, imgBitmap); 
}
```

### Ecriture d’un texte avec une police personnalisée

```java
BitmapFont optimus40;

@Override
public void onInit() {
    FileHandle optimusF = Gdx.files.internal("data/font/OptimusPrinceps.ttf");

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(optimusF);
    optimus40 = generator.generateFont(40);
    optimus40.setColor(Color.BLUE);
    optimus60 = generator.generateFont(60);
    generator.dispose();
}

@Override
public void onGraphicRender(GdxGraphics g) {
    g.clear();
    g.drawStringCentered(50, "Optimus size 40", optimus40);

}

@Override
public void onDispose() {
    optimus40.dispose();
}
```

### Utilisation d’un lecteur de musique

```java
MusicPlayer player;

@Override
public void onInit() {
    player = new MusicPlayer("data/music/Blues-Loop.mp3");
}

@Override
public void onClick(int x, int y, int button) {
   if (player.isPlaying())
      player.stop();
   else
      player.loop();
}
```

### Spritesheet pour créer des animations

```java
Spritesheet sprites;
@Override
public void onInit() {
    sprites = new Spritesheet("data/images/lumberjack_sheet.png", 64, 64);
}

@Override
public void onGraphicRender(GdxGraphics g) {
    g.clear();
    g.spriteBatch.draw(sprites.sprites[1][1], 0, 0); // Draw a region
}
```

Voici un exemple de texture qui peut être utilisé comme "spritesheet". La même image contient différentes régions de textures qui peuvent être sélectionnées lors du dessin.

<center>
  <img alt="Spritesheet" src="{{site.url}}images/doc/lumberjack_sheet.png" height="150" width="150" />
</center>

## Interaction clavier, souris et écran

L’exemple ci-dessous permet de capturer certains événements du clavier (`KeyboardInterface`) et de la souris (`TouchInterface`) :

```java
Vector2 mouse = new Vector2();
boolean isMousPressed = false ;

@Override
public void onClick(int x, int y, int button) {
    super.onClick(x, y, button);

    if (button == Input.Buttons.RIGHT) {
        Logger.log("Right button clicked");
        isMousPressed = true;
    } else
        Logger.log("Left button clicked");

    mouse.x = x;
    mouse.y = y;
}

@Override
public void onDrag(int x, int y) {
    super.onDrag(x, y);
    mouse.x = x;
    mouse.y = y;
}


@Override
public void onRelease(int x, int y, int button) {
    super.onRelease(x, y, button);
    mouse.x = x;
    mouse.y = y;

    if (button == Input.Buttons.RIGHT) {
        isMousPressed = false;
        Logger.log("Right button released");
    }
}

@Override
public void onKeyDown(int keycode) {
    super.onKeyDown(keycode);

    switch (keycode) {

        case Input.Keys.DOWN:
            Logger.log("Down key pressed");
            break;
        case Input.Keys.UP:
            Logger.log("Up key pressed");
            break;

        default:
            Logger.log(String.format("Key '%d' pressed", keycode));
            break;
    }
}

@Override
public void onKeyUp(int keycode) {
    Logger.log(String.format("Key '%d' released", keycode));
}
```

## Moteur physique Box2D

Le moteur physique *Box2D* peut être utilisé afin de développer des applications qui utilisent des phénomènes physiques, comme la gravité ou des collisions entre objets (avec énergie, vitesse, etc.).

### Objets physique disponibles

Différents objets physiques (statiques ou dynamiques) sont à disposition et peuvent être utilisés directement. La taille et les positions de de ces objets peuvent être spécifiées en pixels directement. De plus, ils sont ajoutés automatiquement dans le monde physique `PhysicsWorld` une fois créés.

<center>
    <img alt="Physics class" src="{{site.url}}images/doc/class-physics.png" width="500">
</center>

L’application `DemoSimplePhysics`, présentée dans la figure ci-dessous, utilise le moteur physique *Box2D*. Les applications physiques peuvent être composées des attributs suivants :

* `PhysicsWorld` : le monde unique dans lequel les objets physiques sont ajoutés pour être simulés. La méthode `setGravity` permet par exemple de spécifier une gravité terrestre.
* `DebugRenderer` : permet de dessiner les contours des objets physiques, lorsque ces derniers ne possèdent pas de texture (mode debug). Les objets dont la position est statique sont dessinés en vert.
* La classe `PhysicsScreenBoundaries` peut être utilisée afin de limiter automatiquement le monde physique à la taille de la fenêtre. Par défaut, les collisions sont activées entre tous les objets.

<center>
    <img alt="Physics demo" src="{{site.url}}images/doc/physics.png" width="250">
</center>

Pour cette application, la méthode de dessin est la suivante :

```java
@Override
public void onGraphicRender(GdxGraphics g) {
    g.clear();

    debugRenderer.render(world, g.getCamera().combined);
    PhysicsWorld.updatePhysics();

    g.drawSchoolLogoUpperRight();
    g.drawFPS();
}
```

Elle consiste à faire le rendu du monde physique à l’aide de la classe `DebugRenderer`, puis de recalculer la position des objets du monde en utilisant la méthode `updatePhysics`.

### Dessin des objets physiques avec textures

La classe `DebugRenderer` permet de dessiner le contour des objets physiques automatiquement. Il est possible d’ajouter une texture à ces objets en dessinant périodiquement une image à la position actuelle des objets. Pour ce faire, les objets du monde doivent être sauvegardés dans une liste, comme le montre l’exemple suivant :

```java
// List of physics objets available in the world
LinkedList<AbstractPhysicsObject> list = new LinkedList<AbstractPhysicsObject>();
BitmapImage img;

@Override
public void onInit() {
    img = new BitmapImage("data/images/soccer.png"); // Load the circle texture

    // Create and add circles to the world
    list.add(new PhysicsCircle(null, new Vector2(250, 500), 30));
    list.add(new PhysicsCircle(null, new Vector2(150, 500), 40));
}

@Override
public void onGraphicRender(GdxGraphics g) {
   g.clear();

   // Not necessary any more
   // debugRenderer.render(world, g.getCamera().combined);

   // Draw every body in the world
   for (Iterator<AbstractPhysicsObject> it = list.iterator(); it.hasNext(); ) {
      AbstractPhysicsObject c = it.next();
      Float radius = c.getBodyRadius();
      Vector2 pos = c.getBodyPosition();
      g.drawTransformedPicture(pos.x, pos.y, c.getBodyAngleDeg(), radius, radius, img);
      c.setBodyAwake(true);
   }

   PhysicsWorld.updatePhysics();
}
```

Ainsi, il n’est plus nécessaire de dessiner le `DebugRenderer`.

### Destruction des objets physiques

Il est important de détruire les objets physiques qui ne sont plus utilisés (par exemple lorsque ces derniers sont en dehors de la fenêtre) à l’aide de la méthode `destroy`. Pour ce faire, le code suivant peut être utilisé :

```java
List<PhysicsBall> balls = new LinkedList<PhysicsBall>();
// balls.add(new PhysicsBall(...));

@Override
public void onGraphicRender(GdxGraphics g) {
    g.clear(Color.LIGHT_GRAY);

    // Draws the balls
    for (Iterator<PhysicsBall> iter = balls.iterator(); iter.hasNext(); ) {
        PhysicsBall ball = iter.next();
        ball.draw(g);

        Vector2 p = ball.getBodyPosition();

        // If a ball is not visible anymore, it should be destroyed
        if (p.y > height || p.y < 0 || p.x > width || p.x < 0) {
            ball.destroy();// Mark the ball for deletion when possible
            iter.remove();// Remove the ball from the collection as well
        }
    }

    g.drawString(5, 30, "#Obj: " + world.getBodyCount());
    PhysicsWorld.updatePhysics();
}
```

### Groupes de collisions

Des groupes de collisions peuvent être mis en place afin d’activer ou désactiver les collisions entre des objets physiques de même type. Pour ce faire, la méthode `setCollisionGroup` est disponible pour tous les `AbstractPhysicsObject` :

```java
PhysicsCircle b = new PhysicsCircle(null, new Vector2(x, y), 30);
b.setCollisionGroup(-2);

PhysicsBox box = new PhysicsBox(null, new Vector2(x, y), 30, 30);
box.setCollisionGroup(1);
```

Un identifiant négatif permet de désactiver les collisions entre les objets du même type. Dans cet exemple, les collisions entre les cercles sont désactivées. Toutes les autres collisions (box contre box et box contre cercles) restent actives.