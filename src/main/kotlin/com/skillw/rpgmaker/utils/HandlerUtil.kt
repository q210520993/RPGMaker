package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.core.handlers.ClassQuery
import com.skillw.rpgmaker.core.handlers.RegistryHandler
import com.skillw.rpgmaker.core.handlers.awake.AwakeHandler
import com.skillw.rpgmaker.core.handlers.awake.AwakeManager
import com.skillw.rpgmaker.core.handlers.awake.AwakeType

fun handler(pack: String, filterPack: Set<String> = emptySet()) {
    val query = ClassQuery(pack)
    query.register()
    //初始化AutoRegistry
    RegistryHandler()

    //初始化Awake
    AwakeHandler().register()

    AwakeManager[pack]?.isLoadedMethods?.get(AwakeType.Load)?.forEach {
        it.exec()
    }

}