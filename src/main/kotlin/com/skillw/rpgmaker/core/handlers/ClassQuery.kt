package com.skillw.rpgmaker.core.handlers

import com.skillw.rpgmaker.core.handlers.Cache.functions
import com.skillw.rpgmaker.core.handlers.Cache.get
import com.skillw.rpgmaker.core.handlers.annotations.RAnnotation
import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.core.set.WeakHashSet
import com.skillw.rpgmaker.utils.ClassUtil
import java.lang.reflect.Field
import java.lang.reflect.Method

open class ClassQuery(
    val pack: String,
    private val filterPackages: Set<String> = emptySet()
) : Registrable<String> {

    override fun getKey(): String {
        return pack
    }

    private val classes: Set<Class<*>> = ClassUtil.getClasses(pack).filter { clazz ->
        //过滤
        filterPackages.none { pack ->
            clazz.packageName.startsWith(pack)
        }
    }.toSet()

    private val methods: HashSet<Method> = run {
        val functions = hashSetOf<Method>()
        classes.forEach {
            functions.addAll(it.declaredMethods.toSet())
        }
        return@run functions
    }

    private val fields: HashSet<Field> = run {
        val functions = hashSetOf<Field>()
        classes.forEach {
            functions.addAll(it.declaredFields.toSet())
        }
        return@run functions
    }


    val handlerPostClasses = WeakHashSet<Class<*>>()
    val handlerPostMethods = WeakHashSet<Method>()
    val handlerPostFields = WeakHashSet<Field>()

    init {

        handlerPostClasses.addAll(findClassesWithRAnnotations(classes))
        handlerPostMethods.addAll(findMethodWithRAnnotations(methods))
        handlerPostFields.addAll(findFieldRAnnotations(fields))

    }

    override fun register() {
        Cache.register(getKey(), this) { _, value ->
            functions.addAll(value.handlerPostMethods)
            Cache.classes.addAll(value.handlerPostClasses)
            Cache.fields.addAll(value.handlerPostFields)
        }
    }

}

fun findClassesWithRAnnotations(classes: Set<Class<*>>): Set<Class<*>> {
    return classes.filter { clazz ->
        clazz.annotations.any { annotation ->
            annotation.annotationClass.annotations.any { metaAnnotation ->
                metaAnnotation.annotationClass == RAnnotation::class
            }
        }
    }.toSet()
}
fun findMethodWithRAnnotations(method: Set<Method>): Set<Method> {
    return method.filter { clazz ->
        clazz.annotations.any { annotation ->
            annotation.annotationClass.annotations.any { metaAnnotation ->
                metaAnnotation.annotationClass == RAnnotation::class
            }
        }
    }.toSet()
}
fun findFieldRAnnotations(field: Set<Field>): Set<Field> {
    return field.filter { f ->
        f.annotations.any { annotation ->
            annotation.annotationClass.annotations.any { metaAnnotation ->
                metaAnnotation.annotationClass == RAnnotation::class
            }
        }
    }.toSet()
}