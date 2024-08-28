package com.skillw.rpgmaker.core.handlers.annotations

@RAnnotation
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoRegistry(
    val ignore: Boolean = false,
)
