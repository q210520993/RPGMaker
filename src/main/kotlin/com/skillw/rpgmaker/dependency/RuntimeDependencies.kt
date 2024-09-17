package com.skillw.rpgmaker.dependency

import com.skillw.rpgmaker.core.handlers.annotations.RAnnotation

@RAnnotation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RuntimeDependencies(
    val value: Array<RuntimeDependency>,
    val repository: Array<String>
)
