package com.skillw.rpgmaker

import com.skillw.rpgmaker.core.handlers.awake.AwakeManager
import com.skillw.rpgmaker.core.handlers.awake.AwakeType
import com.skillw.rpgmaker.core.handlers.hook.CoreHookHandler
import com.skillw.rpgmaker.core.manager.ManagerData
import com.skillw.rpgmaker.module.player.RPGPlayer
import com.skillw.rpgmaker.module.player.RPGUuid
import com.skillw.rpgmaker.module.system.terminal.EasyTerminal
import com.skillw.rpgmaker.utils.ConfigUtil
import com.skillw.rpgmaker.utils.handler
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.extras.lan.OpenToLAN
import java.io.File
import java.util.UUID
import java.util.function.Consumer

class RPGMaker(private val minecraftServer: MinecraftServer) {

    val managerData = ManagerData("Server")

    //manager进入初始化阶段
    private fun managerLoader() {
        RPGMakerInstance.allManagers.forEach { (_, value) ->
            value.load()
        }
    }

    /*
    * 初始化Awake
    *      Manager
    *      Registry
    *      ClassQuery
    * */
    private fun handlerInit() {
        handler(
            "com.skillw.rpgmaker",
        )
    }
    fun init() {
        RPGMakerInstance.serverConf = ConfigUtil.loadFile(File("server.conf"))!!
        RPGMakerInstance.option = ConfigUtil.loadFile(File("option.conf"))!!
        //加载基本的注解处理
        handlerInit()
        //加载manager
        managerLoader()
        AwakeManager.execAll(AwakeType.Enable)
        if (RPGMakerInstance.serverConf.getBoolean("server.openToLan")) {
            MinecraftServer.process().scheduler().scheduleNextProcess(OpenToLAN::open)
        }
        //将Player对象转化为自己的RPGPlayer
        MinecraftServer.getConnectionManager().setPlayerProvider { u,i,p ->
            RPGPlayer(RPGMakerInstance.luckPerms, u, i, p)
        }
        //设置玩家的UUIDProvider
        MinecraftServer.getConnectionManager().setUuidProvider(RPGUuid)
        //服务器关闭时
        MinecraftServer.getSchedulerManager().buildShutdownTask {
            RPGMakerInstance.allManagers.forEach { (_, value) ->
                value.disable()
            }
            MinecraftServer.getConnectionManager().onlinePlayers.forEach(Consumer { player: Player ->
                // TODO: 保存玩家数据
                player.kick("Server is closing.")
            })
            if (OpenToLAN.isOpen()) {
                OpenToLAN.close()
            }
            AwakeManager.execAll(AwakeType.Disable)
        }
    }
}