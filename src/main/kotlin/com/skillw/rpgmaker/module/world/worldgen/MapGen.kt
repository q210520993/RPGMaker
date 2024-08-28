package com.skillw.rpgmaker.module.world.worldgen

import net.minestom.server.instance.Instance
import net.minestom.server.instance.generator.GenerationUnit
import java.util.*

open class MapGen(private val seed: Long) {
    private val offset: Int = 8
    private val random: Random = Random()

    fun generate(instance: Instance, cx: Int, cz: Int, chunkData: GenerationUnit?) {
        val k = this.offset

        random.setSeed(seed)
        val l: Long = random.nextLong() / 2L * 2L + 1L
        val i1: Long = random.nextLong() / 2L * 2L + 1L

        for (j1 in cx - k..(cx + k)) {
            for (k1 in cz - k..(cz + k)) {
                random.setSeed(j1.toLong() * l + k1.toLong() * i1 xor seed)
                if (chunkData != null) {
                    this.generate(instance, j1, k1, cx, cz, chunkData)
                }
            }
        }
    }

    private fun generate(instance: Instance, i: Int, j: Int, k: Int, l: Int, chunkData: GenerationUnit) {}
}