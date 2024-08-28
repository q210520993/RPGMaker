package com.skillw.rpgmaker

import com.skillw.rpgmaker.core.handlers.event.EventPriority
import com.skillw.rpgmaker.module.world.manager.WorldManagerImpl
import net.minestom.server.MinecraftServer
import net.minestom.server.event.Event
import net.minestom.server.event.EventListener
import net.minestom.server.event.EventNode
import java.io.File

/**
 * 提供用于处理 RPG 制作的简单事件监听器的 API 方法。
 */

object RPGMakerAPI {

    /**
     * 向全局事件处理器添加一个简单的事件监听器，使用默认的事件优先级 [EventPriority.NORMAL]。
     *
     * @param listener 要添加的事件监听器
     * @return 添加的事件节点 [EventNode]
     */
    fun <T : Event> addSimpleListener(listener: EventListener<T>): EventNode<Event> {
        return addSimpleListener(listener, EventPriority.NORMAL)
    }

    /**
     * 向全局事件处理器添加一个简单的事件监听器，指定特定的事件优先级。
     *
     * @param listener 要添加的事件监听器
     * @param eventPriority 事件的优先级 [EventPriority]
     * @return 添加的事件节点 [EventNode]
     */
    fun <T: Event> addSimpleListener(listener: EventListener<T>, eventPriority: EventPriority): EventNode<Event> {
        val child = MinecraftServer.getGlobalEventHandler().findChildren(eventPriority.name)
        // 做个判断，以提升性能，微乎其微
        if (child.size == 1) {
            return child[0].addListener(listener)
        }
        // 找到与指定优先级匹配的事件节点并添加监听器
        return child.filter {
            it.priority == eventPriority.priority
        }[0].addListener(listener)
    }

    /**
     * 添加一个普通的世界
     *
     */
    fun <T: Event> addSimpleWorld(file: File) {
        WorldManagerImpl.loadWorld(file)
    }

}