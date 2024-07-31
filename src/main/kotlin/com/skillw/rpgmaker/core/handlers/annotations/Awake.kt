package com.skillw.rpgmaker.core.handlers.annotations

import com.skillw.rpgmaker.core.handlers.awake.AwakeType

/**
 * `Awake` 注解用于标记某个方法在特定环节下执行的定义。
 *
 * 通过 `Awake` 注解，可以指定方法在特定的触发类型（`type`）和执行优先级（`priority`）下被调用。
 * 这种机制可以用于定义和管理程序执行的生命周期或事件驱动的特定行为。
 *
 * @property type 触发方法执行的类型，通常用于指定触发时机或事件。
 * @property priority 方法执行的优先级，允许控制多个方法在相同触发类型下的执行顺序。
 *
 * 示例用法：
 * ```
 * // 定义一个带有 `Awake` 注解的方法
 * @Awake(AwakeType.Load, 10.0f)
 * fun onStartup() {
 *     // 在初始化时执行
 * }
 *
 * // 在适当的时机，处理带有 `Awake` 注解的方法
 * AwakeHandler.handle()
 * ```
 *
 * @see AwakeType 定义了 `Awake` 注解中可用的触发类型。
 */

@RAnnotation
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Awake(val type: AwakeType,val priority: Float)
