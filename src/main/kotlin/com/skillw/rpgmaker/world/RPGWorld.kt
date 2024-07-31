package com.skillw.rpgmaker.world

import net.minestom.server.MinecraftServer
import net.minestom.server.instance.Instance

interface RPGWorld {
    val instance: Instance

    fun unRegisterInstance() {
        MinecraftServer.getInstanceManager().unregisterInstance(instance)
    }

}