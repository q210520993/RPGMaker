package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.RPGMakerInstance
import net.minestom.server.instance.Chunk
import net.minestom.server.instance.Instance
import net.minestom.server.instance.LightingChunk
import net.minestom.server.utils.chunk.ChunkUtils
import java.util.concurrent.CompletableFuture

object InstanceUtil {
    @JvmStatic
    fun instanceLighting(instance: Instance, range: Int) {
        val chunks = ArrayList<CompletableFuture<Chunk>>()
        ChunkUtils.forChunksInRange(0,0, range) { x, z ->
            chunks.add(instance.loadChunk(x, z))
        }

        CompletableFuture.runAsync {
            //堵塞线程
            CompletableFuture.allOf(*chunks.toTypedArray()).join()
            val nanoTime1 = System.nanoTime()
            RPGMakerInstance.debug {
                println("点亮中")
                println("点亮时: ${nanoTime1}")
            }
            //点亮！
            LightingChunk.relight(instance, instance.chunks)
            val nanoTime2 = System.nanoTime()
            RPGMakerInstance.debug {
                println("点亮完成")
                println("完成时: ${nanoTime2}")
                println("差: ${nanoTime2 - nanoTime1}")
            }
        }.thenRun {
            instance.saveChunksToStorage()
        }
    }
}