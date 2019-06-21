package ch.hevs.gdx2d.demos.perf_tests

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color

import java.util.Random

/**
 * @author Pierre-André Mudry (mui)
 * @author Marc Pignat (pim)
 */
class Benchmarker : PortableApplication() {

    private val r = FastRandom(0, 100000)

    private val testers = arrayOf(object : Tester("setPixel") {
        override fun draw(g: GdxGraphics, n: Long) {
            val h = g.screenHeight
            val w = g.screenWidth
            var i: Long = 0
            g.clear()
            g.setColor(Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
                    .nextFloat()))

            while (true) {
                for (y in 0 until h) {
                    for (x in 0 until w) {
                        g.setPixel(x.toFloat(), y.toFloat())
                        i++
                    }
                    if (i >= n * 1000) {
                        return
                    }
                }
            }
        }
    },

            object : Tester("drawLine") {
                override fun draw(g: GdxGraphics, n: Long) {
                    val h = g.screenHeight
                    val w = g.screenWidth
                    var i: Long = 0
                    g.clear()
                    g.setColor(Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
                            .nextFloat()))

                    while (true) {
                        g.drawLine(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), h * r.nextFloat())
                        i++
                        if (i >= n) {
                            return
                        }
                    }
                }
            },

            object : Tester("drawRectangle") {
                override fun draw(g: GdxGraphics, n: Long) {
                    val h = g.screenHeight
                    val w = g.screenWidth
                    var i: Long = 0
                    g.clear()
                    g.setColor(Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
                            .nextFloat()))

                    while (true) {
                        g.drawRectangle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), h * r.nextFloat(), 360 * r.nextFloat())
                        i++
                        if (i >= n) {
                            return
                        }
                    }
                }
            },

            object : Tester("drawFilledRectangle") {
                override fun draw(g: GdxGraphics, n: Long) {
                    val h = g.screenHeight
                    val w = g.screenWidth
                    var i: Long = 0
                    g.clear()
                    g.setColor(Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
                            .nextFloat()))

                    while (true) {
                        g.drawFilledRectangle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), h * r.nextFloat(), 360 * r.nextFloat())
                        i++
                        if (i >= n) {
                            return
                        }
                    }
                }
            },

            object : Tester("drawCircle") {
                override fun draw(g: GdxGraphics, n: Long) {
                    val h = g.screenHeight
                    val w = g.screenWidth
                    var i: Long = 0
                    g.clear()
                    g.setColor(Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r
                            .nextFloat()))

                    while (true) {
                        g.drawCircle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat())
                        i++
                        if (i >= n) {
                            return
                        }
                    }
                }
            },

            object : Tester("drawAntiAliasedCircle") {
                override fun draw(g: GdxGraphics, n: Long) {
                    val h = g.screenHeight
                    val w = g.screenWidth
                    var i: Long = 0
                    g.clear()
                    val c = Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat())

                    while (true) {
                        g.drawAntiAliasedCircle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), c)
                        i++
                        if (i >= n) {
                            return
                        }
                    }
                }
            },

            object : Tester("drawFilledCircle") {
                override fun draw(g: GdxGraphics, n: Long) {
                    val h = g.screenHeight
                    val w = g.screenWidth
                    var i: Long = 0
                    g.clear()
                    val c = Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat())

                    while (true) {
                        g.drawFilledCircle(w * r.nextFloat(), h * r.nextFloat(), w * r.nextFloat(), c)
                        i++
                        if (i >= n) {
                            return
                        }
                    }
                }
            })

    private var state: Int = 0
    private var start: Long = 0
    private var n: Long = 0
    private var fps: Int = 0

    private inner class FastRandom internal constructor(seed: Long, cache_size: Int) {
        private val r: Random
        private val cache: FloatArray
        private var i: Int = 0

        init {
            r = Random(seed)
            cache = FloatArray(cache_size)

            for (i in cache.indices) {
                cache[i] = r.nextFloat()
            }

        }

        internal fun nextFloat(): Float {
            i = (i + 1) % cache.size
            return cache[i]
        }
    }

    private abstract inner class Tester internal constructor(var name: String) {
        var speed: Float = 0.toFloat()

        internal abstract fun draw(g: GdxGraphics, n: Long)
    }

    override fun onInit() {
        state = -1
        start = System.currentTimeMillis()
        fps = 0

        setTitle(this.javaClass.getSimpleName())
    }

    override fun onGraphicRender(g: GdxGraphics) {

        if (state == -1) {
            g.clear()
            g.drawFPS()
            g.drawString(5f, 35f, "testing 'do nothing' fps")

            if (System.currentTimeMillis() > start + TIME_LOOP_MS) {
                fps = Gdx.graphics.framesPerSecond
                println("base fps = $fps")
                n = START_N.toLong()
                state++
                start = System.currentTimeMillis()
            }
        } else if (state < testers.size) {
            if (System.currentTimeMillis() < start + TIME_LOOP_MS) {
                testers[state].draw(g, n)
                g.drawFPS()
                val msg = ("testing '" + testers[state].name + " with n = "
                        + n)
                g.drawString(5f, 35f, msg)
            } else {
                start = System.currentTimeMillis()
                if (Gdx.graphics.framesPerSecond >= fps / 2) {
                    n *= 2
                    val msg = ("testing '" + testers[state].name
                            + " with n = " + n)
                    println(msg)
                } else {
                    testers[state].speed = n.toFloat() * Gdx.graphics.framesPerSecond.toFloat()
                    n = START_N.toLong()
                    state++
                }
            }
        } else {
            for (i in testers.indices) {
                println("test '" + testers[i].name + "'\t"
                        + testers[i].speed)
            }
            System.exit(0)
        }

    }
}



// FIXME: should not be included in the lib project. Requires native to be ran.

private val TIME_LOOP_MS = 2000
private val START_N = 100

fun main(args: Array<String>) {
  Benchmarker()
}
