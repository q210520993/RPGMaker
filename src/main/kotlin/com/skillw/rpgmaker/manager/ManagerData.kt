package com.skillw.rpgmaker.manager

import com.skillw.rpgmaker.RPGMakerInstance
import com.skillw.rpgmaker.core.map.KeyMap
import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.util.safe

class ManagerData(override val key: String): KeyMap<String, Manager>(), Registrable<String> {

    private val managers = ArrayList<Manager>()
    private val reloadable = ArrayList<Manager>()

    override fun put(key: String, value: Manager): Manager? {
        managers.add(value)
        managers.sortBy { it.priority }
        if (value is Reloadable) reloadable.add(value)
        return super.put(key, value)
    }

    override fun register() {
        RPGMakerInstance.allManagers.register(this)
    }

    init {
        register()
    }

    fun onLoad() {
        managers.forEach {
            safe(it::onLoad)
        }
    }

    fun onEnable() {
        managers.forEach {
            safe(it::onEnable)
        }
    }

    fun onReload() {
        managers.forEach { _ ->
            reloadable.forEach{ (it as Reloadable).onReload() }
        }
    }

    fun active() {
        managers.forEach {
            safe(it::onActive)
        }
    }

    fun disable() {
        managers.forEach {
            safe(it::onDisable)
        }
    }


}