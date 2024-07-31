package com.skillw.rpgmaker.core.handlers.annotations

/**
 * `CoreHookHandler` 是一个注解处理器，旨在方便核心内扩展，比如与 Spark集成 ，在 Minecraft 进程中使用。
 * 它允许模块通过 `CoreHook` 注解来钩入特定功能。
 *
 * 此处理器通过注解来模块化功能，指定应在核心框架中执行的方法。
 *
 * @see com.skillw.rpgmaker.core.handlers.hook.CoreHookHandler
 * @property functionName `CoreHook` 注解指定的应执行函数的名称。
 * @constructor 初始化 `CoreHookHandler` 实例。
 */
@RAnnotation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CoreHook(val functionName: String = "hook")