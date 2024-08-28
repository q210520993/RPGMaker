package com.skillw.rpgmaker.module.system.spark

import me.lucko.spark.common.tick.AbstractTickReporter
import net.minestom.server.MinecraftServer
import net.minestom.server.event.EventNode

object TickReporter : AbstractTickReporter() {

    private val node = EventNode.all("CORE-RPGMAKER-SPARK").setPriority(999)

    override fun start() {
        MinecraftServer.getGlobalEventHandler().addChild(node)
    }

    override fun close() {
        MinecraftServer.getGlobalEventHandler().removeChild(node)
    }
}