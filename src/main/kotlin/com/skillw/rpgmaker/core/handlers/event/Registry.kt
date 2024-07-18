package com.skillw.rpgmaker.core.handlers.event

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.minestom.server.MinecraftServer
import net.minestom.server.event.EventNode

/**
 * 此对象负责根据优先级注册事件监听器。
 * 它利用 {@code EventPriority} 枚举来迭代每个优先级级别，并在 Minestom 核心的全局事件处理程序中注册相应的事件节点。
 * {@code @AutoRegistry(unsafe = true)} 表示注解无论实现Registrable接口都可以被注册。
 */

@AutoRegistry(unsafe = true)
object Registry {

    fun registry() {
        EventPriority.entries.forEach {
            MinecraftServer.getGlobalEventHandler().addChild(
                EventNode.all(it.name).setPriority(it.priority)
            )
        }
    }

}