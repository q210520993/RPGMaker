package com.skillw.rpgmaker.server

import com.skillw.rpgmaker.core.handlers.RegistryHandler
import com.skillw.rpgmaker.core.handlers.awake.AwakeManager
import com.skillw.rpgmaker.core.handlers.awake.AwakeType
import net.minestom.server.MinecraftServer

fun main() {
    RegistryHandler()
    AwakeManager.execAll(AwakeType.Load)
}