package com.skillw.rpgmaker.module.system.spark

import com.google.common.collect.ImmutableMap
import me.lucko.spark.common.monitor.ping.PlayerPingProvider
import net.minestom.server.MinecraftServer

object PlayerPing: PlayerPingProvider {
    override fun poll(): MutableMap<String, Int> {
        val map = ImmutableMap.builder<String, Int>()
        MinecraftServer.getConnectionManager().onlinePlayers.forEach {
            map.put(it.username, it.latency)
        }
        return map.build()
    }
}