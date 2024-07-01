package com.skillw.rpgmaker.core.handlers.awake

import com.skillw.rpgmaker.core.handlers.AutoInit
import com.skillw.rpgmaker.core.handlers.annotations.AnnotationHandler
import com.skillw.rpgmaker.core.handlers.annotations.Awake
import com.skillw.rpgmaker.core.map.BaseMap
import com.skillw.rpgmaker.core.map.component.Registrable
import java.lang.reflect.Method
import java.util.*

open class AwakeHandler(pack: String = "com.skillw.rpgmaker", override val autoInit: Boolean = true) : AnnotationHandler<Awake>(
    pack
), AutoInit, Registrable<String> {

    override val key: String = pack
    override val annotation: Class<Awake> = Awake::class.java
    val isLoadedMethods = BaseMap<AwakeType, LinkedList<AwakeMethod>>()

    fun getExecedMethods(): Set<Method> {
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

    init {

        handleAnnotation()
        AwakeType.entries.forEach {
            isLoadedMethods[it] = LinkedList()
        }
        if (autoInit) {
            handle()
        }
        println(isLoadedMethods)
    }


    //排序操作
    private fun sort() {
        isLoadedMethods.forEach { (_, list) ->
            list.sortBy {
                it.priority
            }
        }
    }

    override fun handle() {
        methods.forEach {
            isLoadedMethods[it.getAnnotation(Awake::class.java).type]?.add(AwakeMethod(method = it, handler = this))
        }.also {
            sort()
        }
    }

    override fun register() {
        AwakeManager.register(this)
    }

}