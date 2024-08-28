package com.skillw.rpgmaker.module.world.manager

import com.skillw.rpgmaker.RPGMakerInstance.reg
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import java.io.File

@AutoRegistry
object InstantWorldManager: WorldManager {

    override fun getKey(): String {
        return "InstantWorldManager"
    }

    override fun getPriority(): Int {
        return 1
    }

    override fun loadWorld(file: File) {
        TODO("Not yet implemented")
    }

    override fun register() {
        reg()
    }

}