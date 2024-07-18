package com.skillw.rpgmaker.util

val Class<*>.instance: Any?
    get() = try {
        this.getDeclaredField("INSTANCE").get(null)
    } catch (_: NoSuchFieldException) {
        safe {
            this.newInstance()
        }
    }