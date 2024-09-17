package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.core.handlers.ClassQuery
import com.skillw.rpgmaker.core.handlers.registry.RegistryHandler
import com.skillw.rpgmaker.core.handlers.awake.AwakeHandler
import com.skillw.rpgmaker.core.handlers.awake.AwakeManager
import com.skillw.rpgmaker.core.handlers.awake.AwakeType
import com.skillw.rpgmaker.core.handlers.event.SubscribeEventHandler
import com.skillw.rpgmaker.core.handlers.hook.CoreHookHandler
import com.skillw.rpgmaker.dependency.DependenciesHandler

fun handler(pack: String = "com.skillw.rpgmaker",
            filterPack: Set<String> = emptySet()
) {
    val query = ClassQuery(pack, filterPack)
    query.register()
    //加载依赖
    DependenciesHandler()
    //服务端特有(加载服务端模块化插件)
    CoreHookHandler.handle()
    //初始化AutoRegistry
    RegistryHandler()

    //初始化Event
    SubscribeEventHandler().handle()

    //初始化Awake
    AwakeHandler().register()

    //执行所有的生命周期Load的方法
    AwakeManager[pack]?.isLoadedMethods?.get(AwakeType.Load)?.forEach {
        it.exec()
    }

}