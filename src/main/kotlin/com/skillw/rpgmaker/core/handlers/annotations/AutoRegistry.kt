package com.skillw.rpgmaker.core.handlers.annotations


@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoRegistry(val unsafe: Boolean = false)
