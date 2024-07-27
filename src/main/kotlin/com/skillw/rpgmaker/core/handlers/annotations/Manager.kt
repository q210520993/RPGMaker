package com.skillw.rpgmaker.core.handlers.annotations

@RAnnotation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Manager(val managerData: String)
