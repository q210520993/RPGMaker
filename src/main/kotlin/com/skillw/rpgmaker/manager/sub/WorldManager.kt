package com.skillw.rpgmaker.manager.sub

import com.skillw.rpgmaker.manager.Manager
import java.io.File

interface WorldManager: Manager {
    fun loadWorld(file: File)
}