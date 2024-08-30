package com.skillw.rpgmaker.module.scripts

import com.skillw.rpgmaker.RPGMakerInstance
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.core.manager.Manager
import com.skillw.rpgmaker.core.map.BaseMap
import com.skillw.rpgmaker.module.scripts.Types.JsScript
import com.skillw.rpgmaker.utils.ResourceUtil
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.math.log

@AutoRegistry
object FileScriptManager: Manager, BaseMap<String, FileScript>() {

    private fun readResolve(): Any = FileScriptManager
    val logger = LoggerFactory.getLogger(this::class.java)

    init {
        val f = File("Scripts")
        f.listFiles().forEach {
            if (it.extension == "js") {
                JsScript(it).run()
            }
        }
    }

    override fun onLoad() {
        ResourceUtil.extractResource("Scripts")
        File("Scripts").listFiles()?.forEach {
            when(it.extension) {
                "js", "mjs" -> JsScript(it).run()
                else -> logger.warn("${it.path} 不是一个正经的文件后缀名！")
            }
        }
        println(this)
    }

    override fun register() {
        RPGMakerInstance.rpgMaker.managerData.register(this)
    }
    override fun getKey(): String {
        return "FileScriptManager"
    }
}