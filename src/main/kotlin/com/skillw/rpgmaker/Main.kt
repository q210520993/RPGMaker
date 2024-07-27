package com.skillw.rpgmaker

import net.minestom.server.MinecraftServer

fun main() {

    val minecraft = MinecraftServer.init()
    val rpgMaker = RPGMaker(minecraft)
    RPGMakerInstance.rpgMaker = rpgMaker
    rpgMaker.init()


}