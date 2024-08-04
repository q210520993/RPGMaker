package com.skillw.rpgmaker.core.handlers.awake

import com.skillw.rpgmaker.core.handlers.AutoInit
import com.skillw.rpgmaker.core.handlers.annotations.AnnotationHandler
import com.skillw.rpgmaker.core.handlers.annotations.Awake
import com.skillw.rpgmaker.core.map.BaseMap
import com.skillw.rpgmaker.core.map.component.Registrable
import java.lang.reflect.Method
import java.util.*

/**
 * `AwakeHandler` 类用于处理 `Awake` 注解，管理和执行特定环节下的方法调用。
 *
 * @property pack 包名，默认为 "com.skillw.rpgmaker"，用于标识和组织相关的功能模块。
 * @property autoInit 是否在初始化时自动处理注解，默认为 true，即自动处理带有 `Awake` 注解的方法。
 * @constructor 创建一个 `AwakeHandler` 实例。
 */
class AwakeHandler(
    private val pack: String = "com.skillw.rpgmaker",
    override val autoInit: Boolean = true
) : AnnotationHandler<Awake>(), AutoInit, Registrable<String> {

    /**
     * 用于标识和组织相关功能模块的键。
     */

    override fun getKey(): String {
        return pack
    }

    /**
     * `AwakeHandler` 处理的注解类型，这里是 `Awake` 注解。
     */
    override val annotation: Class<Awake> = Awake::class.java

    /**
     * 存储按 `AwakeType` 分类的已加载方法列表。
     */
    val isLoadedMethods = BaseMap<AwakeType, LinkedList<AwakeMethod>>()

    /**
     * 获取所有已执行过的方法。
     *
     * @return 包含所有已执行方法的集合。
     */
    fun getExecMethods(): Set<Method> {
        val set = LinkedHashSet<Method>()
        isLoadedMethods.forEach { (_, value) ->
            value.filter {
                it.isExec
            }.forEach {
                set.add(it.method)
            }
        }
        return set
    }

    /**
     * 初始化操作，处理注解并初始化按 `AwakeType` 分类的方法列表。
     */
    init {
        handleAnnotation()
        AwakeType.entries.forEach {
            isLoadedMethods[it] = LinkedList()
        }
        if (autoInit) {
            handle()
        }
    }

    /**
     * 执行所有已加载方法。
     */
    fun exec() {
        isLoadedMethods.forEach { (_, value) ->
            value.forEach {
                it.exec()
            }
        }
    }

    /**
     * 对加载的方法列表按优先级进行排序。
     */
    private fun sort() {
        isLoadedMethods.forEach { (_, list) ->
            list.sortBy {
                it.priority
            }
        }
    }

    /**
     * 处理带有 `Awake` 注解的方法，将其加入对应的 `AwakeType` 分类中，并按优先级排序。
     */
    override fun handle() {
        methods.forEach {
            isLoadedMethods[it.getAnnotation(Awake::class.java).type]?.add(AwakeMethod(method = it, handler = this))
        }.also {
            sort()
        }
    }

    /**
     * 注册 `AwakeHandler` 实例，通常用于系统启动时注册。
     */
    override fun register() {
        AwakeManager.register(this)
    }
}
