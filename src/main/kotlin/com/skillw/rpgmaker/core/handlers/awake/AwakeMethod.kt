package com.skillw.rpgmaker.core.handlers.awake

import com.skillw.rpgmaker.core.handlers.annotations.Awake
import java.lang.reflect.Method

class AwakeMethod(
    val method: Method,
    val handler: AwakeHandler
) {

    val priority: Float = method.getAnnotation(Awake::class.java).priority
    var isExec: Boolean = false
        private set

    fun getAwakeHandler(): AwakeHandler {
        return handler
    }

    fun exec() {
        method.invoke(null).also { isExec = true }
    }

}