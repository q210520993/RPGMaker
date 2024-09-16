package com.skillw.rpgmaker.module.world

import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.module.world.manager.WorldLoader
import com.skillw.rpgmaker.module.world.manager.WorldManagerImpl
import net.minestom.server.instance.Instance

open class SimpleWorld(override val instance: Instance, val worldInfo: WorldInfo): RPGWorld,
    Registrable<String>
{

    var worldLoader: WorldLoader? = null

    override fun getKey(): String {
        return worldInfo.name
    }

    override fun register() {
        WorldManagerImpl.register(this)
        WorldManagerImpl.worldUUIDManager[this.instance.uniqueId] = this
    }

    override fun unregister() {
        super.unregister()
        unRegisterInstance()
    }

}