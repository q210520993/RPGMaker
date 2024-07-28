package com.skillw.rpgmaker.world

import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.manager.sub.WorldManagerImpl
import net.minestom.server.MinecraftServer
import net.minestom.server.instance.Instance

class SimpleWorld(val instance: Instance, val worldInfo: WorldInfo): RPGWorld(instance), Registrable<String> {

    override val key: String = worldInfo.name

    override fun register() {
        WorldManagerImpl.register(this)
    }

    override fun unregister() {
        MinecraftServer.getInstanceManager().unregisterInstance(instance)
        WorldManagerImpl.remove(worldInfo.name)
    }

}