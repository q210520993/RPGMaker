package com.skillw.rpgmaker.core.map

/**
 * Enum map
 * 就是键是Enum类中的小元素
 * @author clok_
 * @param K
 * @param V
 * @constructor Create empty Base map
 */
open class EnumMap<K: Enum<K>, V: Any>(
    private val enumType: Class<K>,
    private val defaultValue: V? = null,
    initValues: Map<K, V> = emptyMap()
) : BaseMap<K, V>() {

    init {
        initValues.forEach { (key, value) ->
            put(key, value)
        }
    }

    fun putAll(vararg pairs: Pair<K, V>) {
        for ((key, value) in pairs) {
            put(key, value)
        }
    }

    fun removeAll(vararg keys: K) {
        for (key in keys) {
            remove(key)
        }
    }

    override fun get(key: K): V? {
        return super.get(key) ?: defaultValue
    }

    companion object {

        inline fun <reified K : Enum<K>, V : Any> create(
            defaultValue: V? = null,
            vararg pairs: Pair<K, V>
        ): EnumMap<K, V> {
            return EnumMap(K::class.java, defaultValue, pairs.toMap())
        }

    }

}