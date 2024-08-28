package com.skillw.rpgmaker.module.world

import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.module.world.manager.WorldManagerImpl
import net.minestom.server.instance.Instance

class SimpleWorld(override val instance: Instance, val worldInfo: WorldInfo): RPGWorld,
    Registrable<String>
{

    override fun getKey(): String {
        return worldInfo.name
    }

    override fun register() {
        WorldManagerImpl.register(this)
    }

    override fun unregister() {
        super.unregister()
        unRegisterInstance()
    }

}