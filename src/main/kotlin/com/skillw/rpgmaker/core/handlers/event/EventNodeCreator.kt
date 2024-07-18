package com.skillw.rpgmaker.core.handlers.event

import com.skillw.rpgmaker.core.map.component.Registrable
import net.minestom.server.MinecraftServer
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode

/**
 * [EventNodeCreator] 接口用于创建事件节点，并将其注册到全局事件处理器中。
 * 实现 [Registrable] 接口以指定注册的类型为 [EventNode<T>]。
 */
interface EventNodeCreator<T: Event> : Registrable<EventNode<T>> {

    /**
     * 实现 [Registrable] 接口中的注册方法。
     * 将当前实例代表的事件节点添加到全局事件处理器中。
     */
    override fun register() {
        MinecraftServer.getGlobalEventHandler().addChild(key)
    }

}