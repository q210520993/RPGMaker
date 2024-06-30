package com.skillw.rpgmaker.core.handlers.annotations

import com.skillw.rpgmaker.core.Handler
import com.skillw.rpgmaker.utils.ClassUtil
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.annotation.AnnotationTarget.*

abstract class AnnotationHandler<V : Annotation>(private val pack: String = "com.skillw.rpgmaker", autoInit: Boolean = true): Handler {

    abstract val annotation: Class<V>

    fun handleAnnotation() {
        getTargets()
    }

    val Fields: HashSet<Field> = hashSetOf()
    val Methods: HashSet<Method> = hashSetOf()
    val classes: HashSet<Class<*>> = hashSetOf()

    private fun getAnnotationTargets(): Set<AnnotationTarget> {
        val targetAnnotation = annotation.getAnnotation(Target::class.java)
        return targetAnnotation.allowedTargets.toSet()
    }

    protected fun getTargets() {
        getAnnotationTargets().forEach { type ->
            when(type) {
                 CLASS -> {
                     classes.addAll(ClassUtil.isAnnotationPresent(pack = pack, annotation = annotation))
                 }
                FIELD -> {
                    ClassUtil.getClasses(pack).forEach {
                        Fields.addAll(ClassUtil.getAnnotationMember(it, annotation))
                    }
                }
                FUNCTION -> {
                    ClassUtil.getClasses(pack).forEach {
                        Methods.addAll(ClassUtil.getAnnotationMethod(it, annotation))
                    }
                }
                else -> {}
            }
        }

    }

}