package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.core.handlers.ClassQuery
import com.skillw.rpgmaker.core.handlers.RegistryHandler
import com.skillw.rpgmaker.core.handlers.awake.AwakeHandler
import com.skillw.rpgmaker.core.handlers.awake.AwakeManager
import com.skillw.rpgmaker.core.handlers.awake.AwakeType
import com.skillw.rpgmaker.core.handlers.event.SubscribeEventHandler

fun handler(pack: String = "com.skillw.rpgmaker", filterPack: Set<String> = emptySet()) {
    val query = ClassQuery(pack, filterPack)
    query.register()

    //初始化AutoRegistry
    RegistryHandler()

    //初始化Event
    SubscribeEventHandler().handle()

    //初始化Awake
    AwakeHandler().register()

    AwakeManager[pack]?.isLoadedMethods?.get(AwakeType.Load)?.forEach {
        it.exec()
    }

}