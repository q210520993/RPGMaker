package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.util.safe
import kotlin.reflect.full.createInstance

val Class<*>.instance: Any?
    get() = try {
        this.getDeclaredField("INSTANCE").get(null)
    } catch (_: NoSuchFieldException) {
        safe {
            this.kotlin.createInstance()
        }
    }