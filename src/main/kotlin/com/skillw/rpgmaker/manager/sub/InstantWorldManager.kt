package com.skillw.rpgmaker.manager.sub

import com.skillw.rpgmaker.RPGMakerInstance.reg
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import java.io.File

@AutoRegistry
object InstantWorldManager: WorldManager {

    private fun readResolve(): Any = InstantWorldManager

    override val key: String = "InstantWorldManager"

    override val priority: Int = 1
    override fun loadWorld(file: File) {
        TODO("Not yet implemented")
    }

    override fun register() {
        reg()
    }

}