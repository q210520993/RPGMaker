package com.skillw.rpgmaker.module.world.manager

import com.skillw.rpgmaker.core.manager.Manager
import net.minestom.server.instance.Instance
import java.io.File

interface WorldManager: Manager {
    fun loadWorld(file: File) {}
    fun loadWorld(instance: Instance) {}
}