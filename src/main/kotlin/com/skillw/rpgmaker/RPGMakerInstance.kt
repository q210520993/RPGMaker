package com.skillw.rpgmaker

import com.skillw.rpgmaker.core.map.KeyMap
import com.skillw.rpgmaker.manager.Manager
import com.skillw.rpgmaker.manager.ManagerData

object RPGMakerInstance {
    @JvmStatic
    lateinit var rpgMaker: RPGMaker

    val allManagers = KeyMap<String, ManagerData>()

    internal fun Manager.reg() {
        rpgMaker.managerData.register(this)
    }
}