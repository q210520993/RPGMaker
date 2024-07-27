package com.skillw.rpgmaker.core.handlers.event

import com.skillw.rpgmaker.RPGMakerAPI
import com.skillw.rpgmaker.core.handlers.Handler
import com.skillw.rpgmaker.core.handlers.annotations.AnnotationHandler
import com.skillw.rpgmaker.utils.ClassUtil.isSingle
import com.skillw.rpgmaker.utils.run
import net.minestom.server.event.Event
import net.minestom.server.event.EventListener

/**
 * 订阅事件处理器，负责处理带有 [@SubscribeEvent] 注解的事件监听器注册。
 * 实现 [Handler] 接口，并扩展 [AnnotationHandler] 来处理 [@SubscribeEvent] 注解。
 */
class SubscribeEventHandler :
    Handler, AnnotationHandler<SubscribeEvent>() {
    override val annotation: Class<SubscribeEvent> = SubscribeEvent::class.java

    /**
     * 处理带有 [@SubscribeEvent] 注解的事件监听器注册。
     * 遍历标注了注解的方法，验证参数类型，并注册监听器。
     * 如果方法参数类型不正确，抛出 [IllegalArgumentException] 异常。
     */
    override fun handle() {
        handleAnnotation()

        methods.forEach { function ->
            println(function.declaringClass.isSingle())

            // 验证参数类型
            if (!Event::class.java.isAssignableFrom(function.parameterTypes[0])) {
                throw IllegalArgumentException("${function.name} 参数不正确")
            }

            // 获取注解值
            val annotation = function.getAnnotation(annotation)
            val priority = annotation.priority
            val ignoreCancelled = annotation.ignoreCancelled

            // 安全地将参数类型转换为 Event 类
            val eventClass = (function.parameterTypes[0] as? Class<Event>) ?: throw IllegalArgumentException("${function.name} 参数不对")
            // 构建事件监听器
            val listener = EventListener.builder(eventClass).
                ignoreCancelled(ignoreCancelled).
                handler {
                    function.run(it)
                }.
                build()

            // 使用 RPGMakerAPI 注册监听器
            RPGMakerAPI.addSimpleListener(listener, priority)
        }
    }

}