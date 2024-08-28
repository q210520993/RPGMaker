package com.skillw.rpgmaker.utils

import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File

object ConfigUtil {
    @JvmStatic
    fun loadFile(file: File, concurrent: Boolean = true): Configuration? {

        val su = file.extension
        return when(su) {
            "yml", "yaml" -> Configuration.loadFromFile(file, Type.YAML, concurrent)
            "conf" -> Configuration.loadFromFile(file, Type.HOCON, concurrent)
            "json" -> Configuration.loadFromFile(file, Type.JSON, concurrent)
            else -> null
        }

    }
}