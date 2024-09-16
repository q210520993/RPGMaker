package com.skillw.rpgmaker.module.world.worldgen.noise

import java.util.Random
import kotlin.math.sqrt

/**
 * 生成噪声的类，主要用于噪声生成的数学运算
 */
class NoiseGenerator2(random: Random = Random()): Noise {
    private val permutationTable: IntArray = IntArray(512)
    var offsetX: Double = random.nextDouble() * 256.0
    var offsetY: Double = random.nextDouble() * 256.0
    var offsetZ: Double = random.nextDouble() * 256.0

    companion object {
        // 矢量方向表，用于计算噪声
        private val gradients = arrayOf(
            intArrayOf(1, 1, 0), intArrayOf(-1, 1, 0), intArrayOf(1, -1, 0), intArrayOf(-1, -1, 0),
            intArrayOf(1, 0, 1), intArrayOf(-1, 0, 1), intArrayOf(1, 0, -1), intArrayOf(-1, 0, -1),
            intArrayOf(0, 1, 1), intArrayOf(0, -1, 1), intArrayOf(0, 1, -1), intArrayOf(0, -1, -1)
        )
        private val skewFactor = 0.5 * (sqrt(3.0) - 1.0)
        private val unskewFactor = (3.0 - sqrt(3.0)) / 6.0

        // 计算带有偏移的整数部分
        private fun floor(value: Double) = if (value > 0.0) value.toInt() else value.toInt() - 1

        // 计算点积
        private fun dotProduct(gradient: IntArray, x: Double, y: Double) = (gradient[0] * x + gradient[1] * y)
    }

    init {
        // 初始化置换表
        for (i in 0 until 256) {
            permutationTable[i] = i
        }

        for (i in 0 until 256) {
            val randomIndex = random.nextInt(256 - i) + i
            val temp = permutationTable[i]
            permutationTable[i] = permutationTable[randomIndex]
            permutationTable[randomIndex] = temp
            permutationTable[i + 256] = permutationTable[i]
        }
    }

    /**
     * 生成噪声并填充到给定的数组中。
     */
    fun generateNoise(noiseArray: DoubleArray, startX: Double, startY: Double, width: Int, height: Int, scaleX: Double, scaleY: Double, amplitude: Double) {
        var index = 0

        for (x in 0 until width) {
            val scaledX = (startX + x.toDouble()) * scaleX + offsetX
            for (y in 0 until height) {
                val scaledY = (startY + y.toDouble()) * scaleY + offsetY
                val skewedX = (scaledX + scaledY) * skewFactor
                val floorX = floor(scaledX + skewedX)
                val floorY = floor(scaledY + skewedX)
                val unskewedX = (floorX + floorY) * unskewFactor
                val deltaX = scaledX - (floorX - unskewedX)
                val deltaY = scaledY - (floorY - unskewedX)

                val (i1, j1) = if (deltaX > deltaY) 1 to 0 else 0 to 1
                val offsetX1 = deltaX - i1 + unskewFactor
                val offsetY1 = deltaY - j1 + unskewFactor
                val offsetX2 = deltaX - 1.0 + 2.0 * unskewFactor
                val offsetY2 = deltaY - 1.0 + 2.0 * unskewFactor

                val permX = floorX and 255
                val permY = floorY and 255
                val grad1 = permutationTable[permX + permutationTable[permY]] % 12
                val grad2 = permutationTable[permX + i1 + permutationTable[floorY + j1]] % 12
                val grad3 = permutationTable[permX + 1 + permutationTable[floorY + 1]] % 12

                // 计算每个角点的贡献
                var weight1 = 0.5 - deltaX * deltaX - deltaY * deltaY
                val contribution1 = if (weight1 < 0.0) 0.0 else {
                    weight1 *= weight1
                    weight1 * weight1 * dotProduct(gradients[grad1], deltaX, deltaY)
                }

                var weight2 = 0.5 - offsetX1 * offsetX1 - offsetY1 * offsetY1
                val contribution2 = if (weight2 < 0.0) 0.0 else {
                    weight2 *= weight2
                    weight2 * weight2 * dotProduct(gradients[grad2], offsetX1, offsetY1)
                }

                var weight3 = 0.5 - offsetX2 * offsetX2 - offsetY2 * offsetY2
                val contribution3 = if (weight3 < 0.0) 0.0 else {
                    weight3 *= weight3
                    weight3 * weight3 * dotProduct(gradients[grad3], offsetX2, offsetY2)
                }

                // 将计算结果累加到噪声数组中
                noiseArray[index++] += 70.0 * (contribution1 + contribution2 + contribution3) * amplitude
            }
        }
    }
}
