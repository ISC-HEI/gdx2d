package hevs.gdx2d.components.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.physics.box2d.Transform;
import hevs.gdx2d.components.graphics.Polygon;
import hevs.gdx2d.components.physics.utils.PhysicsConstants;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

/**
 * A physical shape which collides as a polygon, see {@link AbstractPhysicsObject}.
 * <p>
 * <b>Note:</b> all dimensions and positions are in pixels.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public class PhysicsPolygon extends AbstractPhysicsObject {

    /**
     * Create a physics polygon.
     *
     * @param name        An optional name for the object (for debug)
     * @param vertices    The polygon vertices
     * @param density     The density of the object, in kg/ms2
     * @param restitution The restitution factor (energy given back on collision). 1 means all the energy is restituted, 0 means no energy is given back
     * @param friction    The friction factor (between 0 and 1)
     */
    public PhysicsPolygon(String name, Vector2[] vertices, float density, float restitution, float friction, boolean dynamic) {
        super(name, Vector2.Zero, vertices, density, restitution, friction, dynamic);
    }

    /**
     * Create a physics polygon.
     *
     * @param name     An optional name for the object (for debug)
     * @param vertices The polygon vertices
     * @param dynamic  Static or dynamic body
     */
    public PhysicsPolygon(String name, Vector2[] vertices, boolean dynamic) {
        super(name, Vector2.Zero, vertices, 1, 0.6f, 0.6f, dynamic);
    }

    /**
     * @return The current polygon shape, the one which is simulated
     */
    public Polygon getPolygon() {
        Fixture f = this.getBody().getFixtureList().get(0);
        assert (f.getType() == Type.Polygon);

        PolygonShape poly = (PolygonShape) f.getShape();
        assert (poly != null);

        Vector2[] vertices = new Vector2[poly.getVertexCount()];
        assert (poly.getVertexCount() != 0);

        for (int i = 0; i < poly.getVertexCount(); i++) {
            vertices[i] = new Vector2();
            poly.getVertex(i, vertices[i]);

            // Apply the transform to the object
            Transform t = f.getBody().getTransform();
            t.mul(vertices[i]);
            vertices[i].scl(PhysicsConstants.M2P);
        }
        return new Polygon(vertices);
    }
}
