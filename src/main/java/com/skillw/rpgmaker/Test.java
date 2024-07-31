package com.skillw.rpgmaker;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.utils.chunk.ChunkUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * @author ：clok
 * @project RPGMaker
 * @date ：2024/7/28 下午10:17
 */
public class Test {
    static void a() {
        Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        var chunks = new ArrayList<CompletableFuture<Chunk>>();
        ChunkUtils.forChunksInRange(0, 0, 32, (x, z) -> chunks.add(instance.loadChunk(x, z)));

        CompletableFuture.runAsync(() -> {
            CompletableFuture.allOf(chunks.toArray(CompletableFuture[]::new)).join();
            System.out.println("load end");
            LightingChunk.relight(instance, instance.getChunks());
            System.out.println("light end");
        });
    }
}
