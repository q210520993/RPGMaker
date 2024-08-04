package com.skillw.rpgmaker.manager

import com.skillw.rpgmaker.core.Priority
import com.skillw.rpgmaker.core.map.component.Keyable
import com.skillw.rpgmaker.core.map.component.Registrable

interface Manager : Registrable<String> ,Priority<Int> {

    fun onLoad() {}
    fun onDisable() {}
    fun onEnable() {}
    fun onActive() {}

}