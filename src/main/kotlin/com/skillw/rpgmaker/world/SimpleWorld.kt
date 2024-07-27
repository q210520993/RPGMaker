package com.skillw.rpgmaker.world

import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.manager.sub.WorldManager
import com.skillw.rpgmaker.manager.sub.WorldManagerImpl
import net.minestom.server.instance.Instance

class SimpleWorld(val instance: Instance, override val key: WorldInfo): RPGWorld(instance), Registrable<WorldInfo> {

    override fun register() {
        WorldManagerImpl.register(this)
        WorldManagerImpl.Worlds.register(key.name, this)
    }

}