package com.skillw.rpgmaker.core.handlers.awake

import com.skillw.rpgmaker.core.map.KeyMap

object AwakeManager: KeyMap<String, AwakeHandler>() {
    private fun readResolve(): Any = AwakeManager



    fun execAll(awakeType: AwakeType) {
        this.forEach{ _, value ->
            value.isLoadedMethods[awakeType]?.forEach {
                it.exec()
            }
        }
    }

}