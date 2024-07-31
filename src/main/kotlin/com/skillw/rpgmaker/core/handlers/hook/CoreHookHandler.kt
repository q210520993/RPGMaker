package com.skillw.rpgmaker.core.handlers.hook

import com.skillw.rpgmaker.RPGMaker
import com.skillw.rpgmaker.RPGMakerInstance
import com.skillw.rpgmaker.core.handlers.annotations.AnnotationHandler
import com.skillw.rpgmaker.core.handlers.annotations.CoreHook
import com.skillw.rpgmaker.util.safe
import com.skillw.rpgmaker.utils.instance

/**
 * `CoreHookHandler` 扩展了 `AnnotationHandler`，专门处理 `CoreHook` 注解。
 * 它识别带有 `CoreHook` 注解的类，并调用它们指定的函数，在 Minecraft 核心进程上下文中执行。
 *
 * 使用示例：
 * ```
 * // 定义一个带有 `CoreHook` 注解的类
 * @CoreHook("customFunction")
 * class MyExtension {
 *     fun customFunction() {
 *         // 实现自定义功能
 *     }
 * }
 *
 * // 在适当的时机，初始化并处理 `CoreHookHandler`
 * CoreHookHandler.handle()
 * ```
 */
object CoreHookHandler : AnnotationHandler<CoreHook>() {

    /**
     * 返回 `CoreHook` 注解的 Class 对象。
     */
    override val annotation: Class<CoreHook> = CoreHook::class.java

    /**
     * 处理 `CoreHook` 注解，识别带注解的类并调用其指定的方法。
     * 如果方法需要参数，则使用特定参数调用；否则，不带参数调用。
     *
     * 实现逻辑：
     * 1. 首先调用 `handleAnnotation()` 方法，准备好所有带 `CoreHook` 注解的类。
     * 2. 对每个类，获取其注解中指定的方法名称。
     * 3. 根据方法的参数类型决定如何调用方法：如果有参数，使用 `RPGMakerInstance.rpgMaker` 作为参数调用；否则，直接调用方法。
     *
     * 注意事项：
     * - 如果指定的方法不存在或不符合预期（比如参数类型不匹配），可能会引发 `NoSuchMethodException` 或 `IllegalAccessException` 异常。
     * - 确保在调用 `handle()` 方法之前，所有需要被处理的类都已正确初始化和加载。
     */
    override fun handle() {
        handleAnnotation()
        classes.forEach { clazz ->

            var runnerName = clazz.getAnnotation(CoreHook::class.java).functionName
            //如果实现了ICoreHook接口，哪就自动强绑定为hook方法
            if (clazz.isAssignableFrom(ICoreHook::class.java))
                runnerName = "hook"
            val method = clazz.getDeclaredMethod(runnerName, RPGMaker::class.java)
            safe {
                method.invoke(clazz.instance, RPGMakerInstance.rpgMaker)
            }
            return
        }
    }
}