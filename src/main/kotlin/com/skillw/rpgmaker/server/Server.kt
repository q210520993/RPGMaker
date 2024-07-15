package com.skillw.rpgmaker.server

import net.minestom.server.MinecraftServer

fun main() {
    val minecraft = MinecraftServer.init()
    val server = RPGMaker(minecraft)
    server.init()
}