package com.skillw.rpgmaker

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Entity
import net.minestom.server.utils.time.TimeUnit


fun main() {

    val minecraft = MinecraftServer.init()
    val rpgMaker = RPGMaker(minecraft)
    RPGMakerInstance.rpgMaker = rpgMaker
    rpgMaker.init()

    // Schedule player getting out of bed
    MinecraftServer.getSchedulerManager().buildTask {
        println("113")
    }
        .delay(101, TimeUnit.SERVER_TICK)
        .schedule()

}