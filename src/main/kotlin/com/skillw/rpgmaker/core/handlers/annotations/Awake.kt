package com.skillw.rpgmaker.core.handlers.annotations

import com.skillw.rpgmaker.core.handlers.awake.AwakeType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Awake(val type: AwakeType,val priority: Float)
