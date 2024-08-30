package com.skillw.rpgmaker

import com.skillw.rpgmaker.utils.ResourceUtil.extractResource
import net.minestom.server.MinecraftServer
import com.skillw.rpgmaker.module.system.terminal.EasyTerminal
import org.graalvm.polyglot.Source
import java.io.File


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
    //println(File("configs/fightsystem/fight.conf").path)
    extractResource("server.conf")
    extractResource("option.conf")
}