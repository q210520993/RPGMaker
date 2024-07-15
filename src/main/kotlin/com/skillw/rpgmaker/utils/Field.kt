package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.util.safe
import java.lang.reflect.Field
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.kotlinProperty

inline fun <reified T> Field.getFieldValue(obj: Any): T? {
    val property = this.kotlinProperty
    return property?.let {
        // 设置属性可访问性为 true，以便获取私有属性
        it.isAccessible = true
        //拿到属性
        return safe {
            return@safe (it.getter.call(obj) as? T)
        }
    }
}
