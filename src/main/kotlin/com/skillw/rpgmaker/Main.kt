package com.skillw.rpgmaker

import com.skillw.rpgmaker.utils.ResourceUtil.extractResource
import net.minestom.server.MinecraftServer
import com.skillw.rpgmaker.module.system.terminal.EasyTerminal


fun main() {
    extract()
    val minecraft = MinecraftServer.init()
    EasyTerminal.start()
    MinecraftServer.getSchedulerManager().buildShutdownTask {
        EasyTerminal.stop()
    }
    val rpgMaker = RPGMaker(minecraft)
    RPGMakerInstance.rpgMaker = rpgMaker
    rpgMaker.init()
}

fun extract() {
    extractResource("worlds")
    extractResource("server.conf")
}