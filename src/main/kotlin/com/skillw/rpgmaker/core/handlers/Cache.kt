package com.skillw.rpgmaker.core.handlers

import com.skillw.rpgmaker.core.map.KeyMap
import com.skillw.rpgmaker.core.set.WeakHashSet
import java.lang.reflect.Field
import java.lang.reflect.Method


object Cache: KeyMap<String, ClassQuery>() {
    private fun readResolve(): Any = Cache

    val classes = WeakHashSet<Class<*>>()

    val functions =  WeakHashSet<Method>()

    val fields =  WeakHashSet<Field>()

}