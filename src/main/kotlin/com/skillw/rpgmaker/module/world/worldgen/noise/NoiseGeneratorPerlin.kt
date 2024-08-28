package com.skillw.rpgmaker.module.world.worldgen.noise

import java.util.Random

/**
 * Perlin 噪声生成器，用于生成自然的噪声。
 */
class NoiseGeneratorPerlin(random: Random) {
    private val permutation: IntArray = IntArray(512)
    var offsetX: Double = random.nextDouble() * 256.0
    var offsetY: Double = random.nextDouble() * 256.0
    var offsetZ: Double = random.nextDouble() * 256.0

    init {

        // 初始化并打乱 permutation 数组
        for (i in 0 until 256) {
            permutation[i] = i
        }
        for (i in 0 until 256) {
            val swapIndex = random.nextInt(256 - i) + i
            val temp = permutation[i]
            permutation[i] = permutation[swapIndex]
            permutation[swapIndex] = temp
            permutation[i + 256] = permutation[i]
        }
    }

    /**
     * 计算 Perlin 噪声值。
     */
    fun generateNoise(x: Double, y: Double, z: Double): Double {
        var x = x + offsetX
        var y = y + offsetY
        var z = z + offsetZ
        val xInt = x.toInt()
        val yInt = y.toInt()
        val zInt = z.toInt()

        val xFrac = x - xInt.toDouble()
        val yFrac = y - yInt.toDouble()
        val zFrac = z - zInt.toDouble()

        val xFade = fade(xFrac)
        val yFade = fade(yFrac)
        val zFade = fade(zFrac)

        val x0 = xInt and 255
        val y0 = yInt and 255
        val z0 = zInt and 255

        val x1 = x0 + 1
        val y1 = y0 + 1
        val z1 = z0 + 1

        val hash000 = permutation[x0 + permutation[y0 + permutation[z0]]] % 12
        val hash001 = permutation[x0 + permutation[y0 + permutation[z1]]] % 12
        val hash010 = permutation[x0 + permutation[y1 + permutation[z0]]] % 12
        val hash011 = permutation[x0 + permutation[y1 + permutation[z1]]] % 12
        val hash100 = permutation[x1 + permutation[y0 + permutation[z0]]] % 12
        val hash101 = permutation[x1 + permutation[y0 + permutation[z1]]] % 12
        val hash110 = permutation[x1 + permutation[y1 + permutation[z0]]] % 12
        val hash111 = permutation[x1 + permutation[y1 + permutation[z1]]] % 12

        val grad000 = grad(hash000, xFrac, yFrac, zFrac)
        val grad001 = grad(hash001, xFrac, yFrac, zFrac - 1.0)
        val grad010 = grad(hash010, xFrac, yFrac - 1.0, zFrac)
        val grad011 = grad(hash011, xFrac, yFrac - 1.0, zFrac - 1.0)
        val grad100 = grad(hash100, xFrac - 1.0, yFrac, zFrac)
        val grad101 = grad(hash101, xFrac - 1.0, yFrac, zFrac - 1.0)
        val grad110 = grad(hash110, xFrac - 1.0, yFrac - 1.0, zFrac)
        val grad111 = grad(hash111, xFrac - 1.0, yFrac - 1.0, zFrac - 1.0)

        val lerpX0 = lerp(xFade, dot(grad000, xFrac, yFrac, zFrac), dot(grad100, xFrac - 1.0, yFrac, zFrac))
        val lerpX1 = lerp(xFade, dot(grad010, xFrac, yFrac - 1.0, zFrac), dot(grad110, xFrac - 1.0, yFrac - 1.0, zFrac))
        val lerpY0 = lerp(yFade, lerpX0, lerpX1)

        val lerpX0_ = lerp(xFade, dot(grad001, xFrac, yFrac, zFrac - 1.0), dot(grad101, xFrac - 1.0, yFrac, zFrac - 1.0))
        val lerpX1_ = lerp(xFade, dot(grad011, xFrac, yFrac - 1.0, zFrac - 1.0), dot(grad111, xFrac - 1.0, yFrac - 1.0, zFrac - 1.0))
        val lerpY1 = lerp(yFade, lerpX0_, lerpX1_)

        return lerp(zFade, lerpY0, lerpY1)
    }

    /**
     * 计算渐变值。
     */
    private fun grad(hash: Int, x: Double, y: Double, z: Double): Double {
        val h = hash and 15
        val u = if (h < 8) x else y
        val v = if (h < 4) y else if (h == 12 || h == 14) x else z
        return ((h and 1).toDouble().let { if (it == 0.0) u else -u }) + ((h and 2).toDouble().let { if (it == 0.0) v else -v })
    }

    /**
     * 线性插值函数。
     */
    private fun lerp(t: Double, a: Double, b: Double): Double {
        return a + t * (b - a)
    }

    /**
     * 平滑插值函数。
     */
    private fun fade(t: Double): Double {
        return t * t * t * (t * (t * 6.0 - 15.0) + 10.0)
    }

    /**
     * 点积计算。
     */
    private fun dot(hash: Double, x: Double, y: Double, z: Double): Double {
        val h = hash + 15
        val u = if (h < 8) x else y
        val v = if (h < 4) y else if (h == 12.0 || h == 14.0) x else z
        return ((h + 1).toDouble().let { if (it == 0.0) u else -u }) + ((h + 2).toDouble().let { if (it == 0.0) v else -v })
    }
}
