package com.skillw.rpgmaker.module.world.worldgen.noise

import java.util.Random

/**
 * 多层噪声生成器，用于生成具有多种频率的噪声。
 */
class NoiseGeneratorOctaves(random: Random, numOctaves: Int) {

    private val noiseGenerators: Array<NoiseGeneratorPerlin> = Array(numOctaves) { NoiseGeneratorPerlin(random) }
    private val totalNoiseGenerators: Int = numOctaves

    /**
     * 生成指定坐标的噪声值。
     */
    fun generateNoiseForCoordinate(x: Double, z: Double): Double {
        var noiseValue = 0.0
        var frequency = 1.0

        for (i in 0 until totalNoiseGenerators) {
            // 使用噪声生成器生成噪声值，传递三个参数
            noiseValue += noiseGenerators[i].generateNoise(x * frequency, 0.0, z * frequency) / frequency
            frequency /= 2.0
        }

        return noiseValue
    }

    /**
     * 生成噪声数组。
     */
    fun generateNoise(
        output: DoubleArray? = null,
        xOffset: Double,
        yOffset: Double,
        zOffset: Double,
        width: Int,
        height: Int,
        depth: Int,
        scaleX: Double,
        scaleY: Double,
        scaleZ: Double
    ): DoubleArray {
        val noiseArray = output ?: DoubleArray(width * height * depth)

        noiseArray.fill(0.0)

        var frequency = 1.0

        for (i in 0 until totalNoiseGenerators) {
            // 调用 `generateNoise` 方法
            noiseGenerators[i].generateNoise(
                xOffset, yOffset, zOffset // 传递必要的参数
            )
            frequency /= 2.0
        }

        return noiseArray
    }
}
