package com.skillw.rpgmaker.server

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent

lateinit var rpgMaker: RPGMaker

fun main() {

    val minecraft = MinecraftServer.init()
    val server = RPGMaker(minecraft)
    rpgMaker = server
    server.init()

}