---
layout: page
permalink: doc/physics
weight: 3
title: Physics with Box2D
---

Le moteur physique *Box2D* peut être utilisé afin de développer des applications qui utilisent des phénomènes physiques, comme la gravité ou des collisions entre objets (avec énergie, vitesse, etc.).

## Objets physique disponibles

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

## Dessin des objets physiques avec textures

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

## Destruction des objets physiques

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

## Groupes de collisions

Des groupes de collisions peuvent être mis en place afin d’activer ou désactiver les collisions entre des objets physiques de même type. Pour ce faire, la méthode `setCollisionGroup` est disponible pour tous les `AbstractPhysicsObject` :

```java
PhysicsCircle b = new PhysicsCircle(null, new Vector2(x, y), 30);
b.setCollisionGroup(-2);

PhysicsBox box = new PhysicsBox(null, new Vector2(x, y), 30, 30);
box.setCollisionGroup(1);
```

Un identifiant négatif permet de désactiver les collisions entre les objets du même type. Dans cet exemple, les collisions entre les cercles sont désactivées. Toutes les autres collisions (box contre box et box contre cercles) restent actives.