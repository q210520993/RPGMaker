package com.skillw.rpgmaker.module.scripts

import com.skillw.rpgmaker.RPGMakerInstance
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.core.manager.Manager
import com.skillw.rpgmaker.core.manager.Reloadable
import com.skillw.rpgmaker.core.map.BaseMap
import com.skillw.rpgmaker.module.scripts.types.JsScript
import org.slf4j.LoggerFactory
import java.io.File

@AutoRegistry
object FileScriptManager: Manager, BaseMap<String, FileScript>(), Reloadable {

    private fun readResolve(): Any = FileScriptManager
    val logger = LoggerFactory.getLogger(this::class.java)

    override fun onLoad() {
        onReload()
    }

    override fun onReload() {
        File("Scripts").listFiles()?.forEach {
            when(it.extension) {
                "js", "mjs" -> {
                    val js = JsScript(it)
                    js.run()
                    js.register()
                }
                else -> logger.warn("${it.path} 不是一个正经的文件后缀名！")
            }
        }
    }

    override fun register() {
        RPGMakerInstance.rpgMaker.managerData.register(this)
    }

    override fun getKey(): String {
        return "FileScriptManager"
    }

}