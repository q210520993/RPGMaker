package com.skillw.rpgmaker.util

import kotlin.reflect.full.createInstance

val Class<*>.instance: Any?
    get() = try {
        this.getDeclaredField("INSTANCE").get(null)
    } catch (_: NoSuchFieldException) {
        safe {
            this.kotlin.createInstance()
        }
    }