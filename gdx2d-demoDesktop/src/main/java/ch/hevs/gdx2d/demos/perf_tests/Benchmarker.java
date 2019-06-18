package ch.hevs.gdx2d.demos.perf_tests;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

/**
 * @author Pierre-André Mudry (mui)
 * @author Marc Pignat (pim)
 */
public class Benchmarker extends PortableApplication {

  // FIXME: should not be included in the lib project. Requires native to be ran.

  private static int TIME_LOOP_MS = 2000;
  private static int START_N = 100;

  private class FastRandom
  {
    private Random r;
    private float[] cache;
    private int i;
    FastRandom(long seed, int cache_size)
    {
      r = new Random(seed);
      cache = new float[cache_size];

      for (int i = 0 ; i < cache.length;i++)
      {
        cache[i] = r.nextFloat();
      }

    }
    float nextFloat() {
      i = (i + 1) % cache.length;
      return cache[i];
    }
  }

  private FastRandom r = new FastRandom(0, 100000);

  private abstract class Tester {
    Tester(String n) {
      name = n;
    }

    String name;
    float speed;

    abstract void draw(GdxGraphics g, long n);
  }

  private Tester[] testers = {
    new Tester("setPixel") {
      @Override
      void draw(GdxGraphics g, long n) {
        int h = g.getScreenHeight();
        int w = g.getScreenWidth();
        long i = 0;
        g.clear();
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
          .nextFloat()));

        for (;;) {
          for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
              g.setPixel(x, y);
              i++;
            }
            if (i >= n * 1000) {
              return;
            }
          }
        }
      }
    },

    new Tester("drawLine") {
      @Override
      void draw(GdxGraphics g, long n) {
        int h = g.getScreenHeight();
        int w = g.getScreenWidth();
        long i = 0;
        g.clear();
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
          .nextFloat()));

        for (;;) {
          g.drawLine(w*r.nextFloat(), h*r.nextFloat(), w*r.nextFloat(), h*r.nextFloat());
          i++;
          if (i >= n) {
            return;
          }
        }
      }
    },

    new Tester("drawRectangle") {
      @Override
      void draw(GdxGraphics g, long n) {
        int h = g.getScreenHeight();
        int w = g.getScreenWidth();
        long i = 0;
        g.clear();
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
          .nextFloat()));

        for (;;) {
          g.drawRectangle(w*r.nextFloat(), h*r.nextFloat(), w*r.nextFloat(), h*r.nextFloat(), 360*r.nextFloat());
          i++;
          if (i >= n) {
            return;
          }
        }
      }
    },

    new Tester("drawFilledRectangle") {
      @Override
      void draw(GdxGraphics g, long n) {
        int h = g.getScreenHeight();
        int w = g.getScreenWidth();
        long i = 0;
        g.clear();
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
          .nextFloat()));

        for (;;) {
          g.drawFilledRectangle(w*r.nextFloat(), h*r.nextFloat(), w*r.nextFloat(), h*r.nextFloat(), 360*r.nextFloat());
          i++;
          if (i >= n) {
            return;
          }
        }
      }
    },

    new Tester("drawCircle") {
      @Override
      void draw(GdxGraphics g, long n) {
        int h = g.getScreenHeight();
        int w = g.getScreenWidth();
        long i = 0;
        g.clear();
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
          .nextFloat()));

        for (;;) {
          g.drawCircle(w*r.nextFloat(), h*r.nextFloat(), w*r.nextFloat());
          i++;
          if (i >= n) {
            return;
          }
        }
      }
    },

    new Tester("drawAntiAliasedCircle") {
      @Override
      void draw(GdxGraphics g, long n) {
        int h = g.getScreenHeight();
        int w = g.getScreenWidth();
        long i = 0;
        g.clear();
        Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat());

        for (;;) {
          g.drawAntiAliasedCircle(w*r.nextFloat(), h*r.nextFloat(), w*r.nextFloat(), c);
          i++;
          if (i >= n) {
            return;
          }
        }
      }
    },

    new Tester("drawFilledCircle") {
      @Override
      void draw(GdxGraphics g, long n) {
        int h = g.getScreenHeight();
        int w = g.getScreenWidth();
        long i = 0;
        g.clear();
        Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat());

        for (;;) {
          g.drawFilledCircle(w*r.nextFloat(), h*r.nextFloat(), w*r.nextFloat(), c);
          i++;
          if (i >= n) {
            return;
          }
        }
      }
    }
  };

  public static void main(String[] args) {
    new Benchmarker();
  }

  private int state;
  private long start;
  private long n;
  private int fps;

  @Override
  public void onInit() {
    state = -1;
    start = System.currentTimeMillis();
    fps = 0;

    setTitle(this.getClass().getSimpleName());
  }

  @Override
  public void onGraphicRender(GdxGraphics g) {

    if (state == -1) {
      g.clear();
      g.drawFPS();
      g.drawString(5, 35, "testing 'do nothing' fps");

      if (System.currentTimeMillis() > start + TIME_LOOP_MS) {
        fps = Gdx.graphics.getFramesPerSecond();
        System.out.println("base fps = " + fps);
        n = START_N;
        state++;
        start = System.currentTimeMillis();
      }
    }

    else if (state < testers.length) {
      if (System.currentTimeMillis() < start + TIME_LOOP_MS) {
        testers[state].draw(g, n);
        g.drawFPS();
        String msg = "testing '" + testers[state].name + " with n = "
          + n;
        g.drawString(5, 35, msg);
      } else {
        start = System.currentTimeMillis();
        if (Gdx.graphics.getFramesPerSecond() >= fps / 2) {
          n *= 2;
          String msg = "testing '" + testers[state].name
            + " with n = " + n;
          System.out.println(msg);
        } else {
          testers[state].speed = (float) n
            * (float) Gdx.graphics.getFramesPerSecond();
          n = START_N;
          state++;
        }
      }
    } else {
      for (int i = 0; i < testers.length; i++) {
        System.out.println("test '" + testers[i].name + "'\t"
          + testers[i].speed);
      }
      System.exit(0);
    }

  }
}
