package com.skillw.rpgmaker.module.world.manager

import com.skillw.rpgmaker.module.world.SimpleWorld
import java.io.File

interface WorldLoader {
    fun loadWorld(file: File): SimpleWorld
}