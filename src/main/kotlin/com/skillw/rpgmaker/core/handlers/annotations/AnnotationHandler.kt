package com.skillw.rpgmaker.core.handlers.annotations

import com.skillw.rpgmaker.core.handlers.Cache
import com.skillw.rpgmaker.core.handlers.Handler
import com.skillw.rpgmaker.core.set.WeakHashSet
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.annotation.AnnotationTarget.*

abstract class AnnotationHandler<V : Annotation>(
    private val pack: String = "com.skillw.rpgmaker",
): Handler {

    abstract val annotation: Class<V>

    fun handleAnnotation() {
        getTargets()
    }

    //这里用weak和hash没啥区别
    val fields = WeakHashSet<Field>()
    val methods = WeakHashSet<Method>()
    val classes = WeakHashSet<Class<*>>()

    private fun getAnnotationTargets(): Set<AnnotationTarget> {
        val targetAnnotation = annotation.getAnnotation(Target::class.java)
        return targetAnnotation.allowedTargets.toSet()
    }


    //拿到所有的对象并存入
    private fun getTargets() {
        getAnnotationTargets().forEach { type ->
            when(type) {
                CLASS -> {
                    classes.addAll(Cache.classes.filter { it.isAnnotationPresent(annotation) }.toSet())
                }
                FIELD -> {
                    fields.addAll(Cache.fields.filter { it.isAnnotationPresent(annotation) }.toSet())
                }
                FUNCTION -> {
                    methods.addAll(Cache.functions.filter { it.isAnnotationPresent(annotation) }.toSet())
                }
                else -> {}
            }
        }
    }

}