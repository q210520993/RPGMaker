package com.skillw.rpgmaker.core.handlers.awake

import com.skillw.rpgmaker.core.Priority
import com.skillw.rpgmaker.core.handlers.annotations.Awake
import com.skillw.rpgmaker.utils.run
import java.lang.reflect.Method

class AwakeMethod(
    val method: Method,
    val handler: AwakeHandler
): Priority<Float> {

    override val priority: Float = method.getAnnotation(Awake::class.java).priority
    var isExec: Boolean = false
        private set

    fun getAwakeHandler(): AwakeHandler {
        return handler
    }

    fun exec() {
        method.run().also { isExec = true }
    }

}