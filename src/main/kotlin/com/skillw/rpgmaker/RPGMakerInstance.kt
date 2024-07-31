package com.skillw.rpgmaker

import com.skillw.rpgmaker.core.map.KeyMap
import com.skillw.rpgmaker.manager.Manager
import com.skillw.rpgmaker.manager.ManagerData
import com.skillw.rpgmaker.system.ServerProperties
import taboolib.common5.cbool

object RPGMakerInstance {
    @JvmStatic
    lateinit var rpgMaker: RPGMaker

    @JvmStatic
    lateinit var serverProperties: ServerProperties

    val allManagers = KeyMap<String, ManagerData>()

    internal fun Manager.reg() {
        rpgMaker.managerData.register(this)
    }

    private val debug: Boolean
        get() = serverProperties.get("debug").cbool

    internal fun debug(runnable: Runnable) {
        if (!debug) return
        runnable.run()
    }

}