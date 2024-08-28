package com.skillw.rpgmaker.module.world.worldgen.noise

import java.util.Random

/**
 * 多层噪声生成器 (版本 2)，用于生成具有多种频率的噪声。
 */
class NoiseGeneratorOctaves2(random: Random, numOctaves: Int) {

    private val generators: Array<NoiseGenerator2> = Array(numOctaves) { NoiseGenerator2(random) }
    private val numGenerators: Int = numOctaves

    /**
     * 生成噪声数组，使用默认的缩放因子。
     */
    fun generateNoise(
        output: DoubleArray? = null,
        xOffset: Double,
        yOffset: Double,
        width: Int,
        height: Int,
        scaleX: Double,
        scaleY: Double,
        scaleZ: Double
    ): DoubleArray {
        return generateNoise(output, xOffset, yOffset, width, height, scaleX, scaleY, scaleZ, 0.5)
    }

    /**
     * 生成噪声数组，允许指定缩放因子。
     */
    fun generateNoise(
        output: DoubleArray? = null,
        xOffset: Double,
        yOffset: Double,
        width: Int,
        height: Int,
        scaleX: Double,
        scaleY: Double,
        scaleZ: Double,
        scaleFactor: Double
    ): DoubleArray {
        val adjustedScaleX = scaleX / 1.5
        val adjustedScaleY = scaleY / 1.5

        val noiseArray = output ?: DoubleArray(width * height)

        // Initialize noise array if not provided or too short
        if (output != null && output.size >= width * height) {
            noiseArray.fill(0.0)
        }

        var frequency = 1.0
        var amplitude = 1.0

        for (i in 0 until numGenerators) {
            generators[i].generateNoise(
                noiseArray, xOffset, yOffset, width, height, adjustedScaleX * amplitude, adjustedScaleY * amplitude, 0.55 / frequency
            )
            amplitude *= scaleZ
            frequency *= scaleFactor
        }

        return noiseArray
    }
}
