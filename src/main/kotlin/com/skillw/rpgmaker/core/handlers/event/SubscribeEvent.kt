package com.skillw.rpgmaker.core.handlers.event

import com.skillw.rpgmaker.core.handlers.annotations.RAnnotation


/**
 * 订阅事件注解 [@SubscribeEvent]，用于标记事件订阅方法。
 *
 * @property priority 事件处理优先级，默认为 [EventPriority.NORMAL]。
 * @property ignoreCancelled 是否忽略已取消的事件，默认为 false。
 */

@RAnnotation
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SubscribeEvent(
    val priority: EventPriority = EventPriority.NORMAL,
    val ignoreCancelled: Boolean = false
)
