package com.skillw.rpgmaker.module.system.spark

import me.lucko.spark.common.platform.PlatformInfo
import net.minestom.server.MinecraftServer

object Platform: PlatformInfo {
    override fun getType(): PlatformInfo.Type {
        return PlatformInfo.Type.SERVER
    }

    override fun getName(): String {
        return "RPGMaker"
    }

    override fun getBrand(): String {
        return "Minestom"
    }

    override fun getVersion(): String {
        return MinecraftServer.VERSION_NAME + "-" + MinecraftServer.getBrandName();
    }

    override fun getMinecraftVersion(): String {
        return MinecraftServer.VERSION_NAME
    }

}