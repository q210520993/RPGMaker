package com.skillw.rpgmaker.dependency

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class RuntimeDependency(
    val name: String
)
