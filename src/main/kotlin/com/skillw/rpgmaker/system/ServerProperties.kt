package com.skillw.rpgmaker.system

import java.io.*
import java.util.*

class ServerProperties() {

    private var source: File? = null
    private lateinit var properties: Properties

    @Throws(IOException::class)
    constructor(source: File): this() {
        this.properties = Properties()
        loadDefault()
        this.source = source
        if (source.exists()) {
            load()
        } else {
            save() // write defaults to file
        }
    }

    @Throws(IOException::class)
    constructor(source: String?) : this() {
        properties = Properties()
        properties.load(source?.let { StringReader(it) })
        this.source = null
    }

    fun get(key: String): String {
        return properties.getProperty(key)
    }

    fun set(key: String, value: String) {
        properties[key] = value
    }

    @Throws(IOException::class)
    private fun loadDefault() {
        source?.let {
            val reader = FileReader(it)
            reader.use { er->
                properties.load(er)
            }
        }
    }

    @Throws(IOException::class)
    fun save() {
        source?.let {
            FileWriter(it).use { writer ->
                properties.store(writer, "Minestom server properties")
            }
        }
    }

    @Throws(IOException::class)
    fun load() {
        FileReader(source).use { reader ->
            properties.load(reader)
        }
    }

}