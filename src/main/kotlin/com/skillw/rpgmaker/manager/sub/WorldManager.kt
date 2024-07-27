package com.skillw.rpgmaker.manager.sub

import com.skillw.rpgmaker.core.map.BaseMap
import com.skillw.rpgmaker.manager.Manager
import net.minestom.server.instance.Instance
import java.io.File

interface WorldManager: Manager {
    abstract fun loadWorld(file: File)
}