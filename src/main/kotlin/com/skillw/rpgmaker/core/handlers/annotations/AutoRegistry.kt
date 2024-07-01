package com.skillw.rpgmaker.core.handlers.annotations


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AutoRegistry(val unsafe: Boolean = false)
