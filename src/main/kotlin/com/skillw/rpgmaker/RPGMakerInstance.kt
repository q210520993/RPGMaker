package com.skillw.rpgmaker

import com.skillw.rpgmaker.core.map.KeyMap
import com.skillw.rpgmaker.core.manager.Manager
import com.skillw.rpgmaker.core.manager.ManagerData
import com.skillw.rpgmaker.util.safe
import net.luckperms.api.LuckPerms
import taboolib.common5.cbool
import taboolib.module.configuration.Configuration

object RPGMakerInstance {
    @JvmStatic
    lateinit var rpgMaker: RPGMaker

    @JvmStatic
    lateinit var luckPerms: LuckPerms

    @JvmStatic
    lateinit var serverConf: Configuration

    @JvmStatic
    lateinit var option: Configuration


    val allManagers = KeyMap<String, ManagerData>()

    internal fun Manager.reg() {
        rpgMaker.managerData.register(this)
    }

    private val debug: Boolean
        get() = serverConf.getBoolean("server.debug")

    internal fun debug(runnable: Runnable) {
        if (!debug) return
        runnable.run()
    }

    internal fun reload() {
        safe {
            rpgMaker.managerData.reload()
        }
    }


}