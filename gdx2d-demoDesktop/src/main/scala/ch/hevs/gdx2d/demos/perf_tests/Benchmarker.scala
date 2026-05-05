package ch.hevs.gdx2d.demos.perf_tests

import java.util.Random

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color

/**
 * Micro-benchmark cycling through gdx2d's graphics primitives and printing
 * results to stdout.
 *
 * @author Pierre-André Mudry (mui)
 * @author Marc Pignat (pim)
 */
class Benchmarker extends DesktopApplication {
  import Benchmarker._

  private val r = new FastRandom(0L, 100000)

  private abstract class Tester(val name: String) {
    var speed: Float = 0f
    def draw(g: GdxGraphics, n: Long): Unit
  }

  private class FastRandom(seed: Long, cacheSize: Int) {
    private val rnd = new Random(seed)
    private val cache: Array[Float] = Array.fill(cacheSize)(rnd.nextFloat())
    private var i: Int = 0
    def nextFloat(): Float = {
      i = (i + 1) % cache.length
      cache(i)
    }
  }

  private val testers: Array[Tester] = Array(
    new Tester("setPixel") {
      override def draw(g: GdxGraphics, n: Long): Unit = {
        val h = g.getScreenHeight
        val w = g.getScreenWidth
        var i: Long = 0
        g.clear()
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat()))
        while (true) {
          var y = 0
          while (y < h) {
            var x = 0
            while (x < w) {
              g.setPixel(x.toFloat, y.toFloat)
              i += 1
              x += 1
            }
            if (i >= n * 1000) return
            y += 1
          }
        }
      }
    },
    new Tester("drawLine") {
      override def draw(g: GdxGraphics, n: Long): Unit = {
        val h = g.getScreenHeight
        val w = g.getScreenWidth
        var i: Long = 0
        g.clear()
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat()))
        while (true) {
          g.drawLine(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), h * r.nextFloat())
          i += 1
          if (i >= n) return
        }
      }
    },
    new Tester("drawRectangle") {
      override def draw(g: GdxGraphics, n: Long): Unit = {
        val h = g.getScreenHeight
        val w = g.getScreenWidth
        var i: Long = 0
        g.clear()
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat()))
        while (true) {
          g.drawRectangle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), h * r.nextFloat(), 360 * r.nextFloat())
          i += 1
          if (i >= n) return
        }
      }
    },
    new Tester("drawFilledRectangle") {
      override def draw(g: GdxGraphics, n: Long): Unit = {
        val h = g.getScreenHeight
        val w = g.getScreenWidth
        var i: Long = 0
        g.clear()
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat()))
        while (true) {
          g.drawFilledRectangle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), h * r.nextFloat(), 360 * r.nextFloat())
          i += 1
          if (i >= n) return
        }
      }
    },
    new Tester("drawCircle") {
      override def draw(g: GdxGraphics, n: Long): Unit = {
        val h = g.getScreenHeight
        val w = g.getScreenWidth
        var i: Long = 0
        g.clear()
        g.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat()))
        while (true) {
          g.drawCircle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat())
          i += 1
          if (i >= n) return
        }
      }
    },
    new Tester("drawAntiAliasedCircle") {
      override def draw(g: GdxGraphics, n: Long): Unit = {
        val h = g.getScreenHeight
        val w = g.getScreenWidth
        var i: Long = 0
        g.clear()
        val c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat())
        while (true) {
          g.drawAntiAliasedCircle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), c)
          i += 1
          if (i >= n) return
        }
      }
    },
    new Tester("drawFilledCircle") {
      override def draw(g: GdxGraphics, n: Long): Unit = {
        val h = g.getScreenHeight
        val w = g.getScreenWidth
        var i: Long = 0
        g.clear()
        val c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat())
        while (true) {
          g.drawFilledCircle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), c)
          i += 1
          if (i >= n) return
        }
      }
    })

  private var state: Int = 0
  private var start: Long = 0
  private var n: Long = 0
  private var fps: Int = 0

  override def onInit(): Unit = {
    state = -1
    start = System.currentTimeMillis()
    fps = 0
    setTitle(getClass.getSimpleName)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (state == -1) {
      g.clear()
      g.drawFPS()
      g.drawString(5f, 35f, "testing 'do nothing' fps")
      if (System.currentTimeMillis() > start + TIME_LOOP_MS) {
        fps = Gdx.graphics.getFramesPerSecond
        println(s"base fps = $fps")
        n = START_N.toLong
        state += 1
        start = System.currentTimeMillis()
      }
    } else if (state < testers.length) {
      if (System.currentTimeMillis() < start + TIME_LOOP_MS) {
        testers(state).draw(g, n)
        g.drawFPS()
        g.drawString(5f, 35f, s"testing '${testers(state).name} with n = $n")
      } else {
        start = System.currentTimeMillis()
        if (Gdx.graphics.getFramesPerSecond >= fps / 2) {
          n *= 2
          println(s"testing '${testers(state).name} with n = $n")
        } else {
          testers(state).speed = n.toFloat * Gdx.graphics.getFramesPerSecond.toFloat
          n = START_N.toLong
          state += 1
        }
      }
    } else {
      for (t <- testers) println(s"test '${t.name}'\t${t.speed}")
      System.exit(0)
    }
  }
}

object Benchmarker {
  private val TIME_LOOP_MS = 2000
  private val START_N = 100

  def main(args: Array[String]): Unit = new Benchmarker().launch()
}
