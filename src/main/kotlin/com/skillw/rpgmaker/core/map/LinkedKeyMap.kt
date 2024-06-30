package com.github.zimablue.devoutserver.api.decouple.map

import com.skillw.rpgmaker.core.map.component.Keyable
import com.skillw.rpgmaker.core.map.LinkedMap

/**
 * Linked key map
 *
 * @param K
 * @param V
 * @constructor Create empty Linked key map
 */
open class LinkedKeyMap<K : Any, V : Keyable<K>> : LinkedMap<K, V>() {
    private fun getKey(value: V): K {
        return value.key
    }

    /**
     * Register
     *
     * @param value
     */
    open fun register(value: V) {
        register(value.key, value)
    }

    /**
     * Remove by value
     *
     * @param value
     */
    fun removeByValue(value: V) {
        val key = getKey(value)
        key.also { remove(it) }
    }
}