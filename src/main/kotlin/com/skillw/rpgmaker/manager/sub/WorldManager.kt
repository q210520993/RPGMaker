package com.skillw.rpgmaker.manager.sub

import com.skillw.rpgmaker.manager.Manager
import net.minestom.server.instance.Instance
import java.io.File

interface WorldManager: Manager {
    fun loadWorld(file: File) {}
    fun loadWorld(instance: Instance) {}
}