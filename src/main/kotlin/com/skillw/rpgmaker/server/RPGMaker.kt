package com.skillw.rpgmaker.server

import com.skillw.rpgmaker.utils.handler
import net.minestom.server.MinecraftServer

class RPGMaker(val minecraftServer: MinecraftServer) {

    private fun handlerInit() {
        handler(
            "com.skillw.rpgmaker",
            filterPack = setOf(
                "com.skillw.rpgmaker.server",
                "com.skillw.rpgmaker.utils"
            )
        )
    }
    fun init() {

        handlerInit()
        minecraftServer.start("127.0.0.1", 25565)

    }

}