package com.skillw.rpgmaker

import com.skillw.rpgmaker.core.handlers.awake.AwakeManager
import com.skillw.rpgmaker.core.handlers.awake.AwakeType
import com.skillw.rpgmaker.module.system.terminal.EasyTerminal
import com.skillw.rpgmaker.utils.ResourceUtil
import com.skillw.rpgmaker.utils.ResourceUtil.extractResource
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Point
import net.minestom.server.extras.MojangAuth
import net.minestom.server.instance.Instance
import net.minestom.server.instance.anvil.AnvilLoader
import net.minestom.server.instance.block.Block
import kotlin.math.min

fun main() {
    //释放文件
    extract()
    val minecraft = MinecraftServer.init()
    //终端初始化
    EasyTerminal.start()
    MinecraftServer.getSchedulerManager().buildShutdownTask {
        EasyTerminal.stop()
    }
    val rpgMaker = RPGMaker(minecraft)
    RPGMakerInstance.rpgMaker = rpgMaker
    //服务端初始化
    rpgMaker.init()
    //Mojang登录验证初始化
    if (RPGMakerInstance.serverConf.getBoolean("online", false)) {
        MojangAuth.init()
    }
    //设置UUIDProvider
    //启动！
    RPGMakerInstance.serverConf.getString("server.server-ip", "0.0.0.0")?.let {
        minecraft.start(
            it,
            RPGMakerInstance.serverConf.getInt("server.server-port", 25565)

        )
        AwakeManager.execAll(AwakeType.Active)
    }
//    val instanceTest = MinecraftServer.getInstanceManager().createInstanceContainer()
//    instanceTest.chunkLoader = AnvilLoader("TestWorld")
//    instanceTest.setGenerator { unit ->
//        val start: Point = unit.absoluteStart()
//        val size: Point = unit.size()
//        for (x in 0 until size.blockX()) {
//            for (z in 0 until size.blockZ()) {
//                for (y in 0 until min(40 - start.blockY(), size.blockY())) {
//                    unit.modifier().setBlock(start.add(x.toDouble(), y.toDouble(), z.toDouble()), Block.STONE)
//                }
//            }
//        }
//    }
//    MinecraftServer.getSchedulerManager().buildShutdownTask {
//        instanceTest.saveChunksToStorage()
//    }
}

fun extract() {
    extractResource("worlds")
    //println(File("configs/fightsystem/fight.conf").path)
    extractResource("server.conf")
    extractResource("option.conf")
    extractResource("Scripts")
}