package hevs.gdx2d.lib.utils.catmull;

/**
 * 1D Catmull-Rom spline
 * Adapted from http://hawkesy.blogspot.ch/2010/05/catmull-rom-spline-curve-implementation.html
 */
public class CatmullRomSpline {
    private float p0, p1, p2, p3;

    public CatmullRomSpline(float p0, float p1, float p2, float p3) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public float q(float t) {
        return 0.5f * ((2 * p1) +
                      (p2 - p0) * t +
                      (2*p0 - 5*p1 + 4*p2 - p3) * t * t +
                      (3*p1 -p0 - 3 * p2 + p3) * t * t * t);    
    }


    /**
     * Example implementation
     */
    public static void main(String[] args) {
        CatmullRomSpline crs = new CatmullRomSpline(1f, 2f, 2f, 1f);
        System.out.println(crs.q(0f));
        System.out.println(crs.q(0.25f));
        System.out.println(crs.q(0.5f));
        System.out.println(crs.q(0.75f));
        System.out.println(crs.q(1f));
    }

    /**
     * @return the p0
     */
    public float getP0() {
        return p0;
    }

    /**
     * @param p0 the p0 to set
     */
    public void setP0(float p0) {
        this.p0 = p0;
    }

    /**
     * @return the p1
     */
    public float getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(float p1) {
        this.p1 = p1;
    }

    /**
     * @return the p2
     */
    public float getP2() {
        return p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(float p2) {
        this.p2 = p2;
    }

    /**
     * @return the p3
     */
    public float getP3() {
        return p3;
    }

    /**
     * @param p3 the p3 to set
     */
    public void setP3(float p3) {
        this.p3 = p3;
    }
}