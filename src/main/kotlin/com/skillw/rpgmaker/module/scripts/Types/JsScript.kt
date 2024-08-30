package com.skillw.rpgmaker.module.scripts.Types

import com.skillw.rpgmaker.module.scripts.FileScript
import com.skillw.rpgmaker.module.scripts.FileScriptManager
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import org.graalvm.polyglot.Value
import java.io.File

class JsScript(val fileA: File): FileScript {

    lateinit var resultContext: Context

    override fun getFile(): File {
        return fileA
    }

    override fun getSource(): Source {
        return Source.newBuilder("js", getFile()).build()
    }

    override fun register() {
        FileScriptManager.register(getKey(), this)
    }

    override fun getScriptType(): String {
        return "js"
    }

    override fun getKey(): String {
        val key = resultContext.getBindings("js").getMember("__FILEOPTION_KEY").asString() ?: getFile().path
        return key
    }

    override fun getContext(): Context {
        val context = Context.newBuilder("js")
            .allowAllAccess(true)
            .allowExperimentalOptions(true)
            .option("js.commonjs-require", "true")
            .option("engine.WarnInterpreterOnly", "false")
            .option("js.commonjs-require-cwd", "Scripts")
            .option("js.esm-eval-returns-exports", "true")
            .option("js.foreign-object-prototype", "true")
            .option("js.nashorn-compat", "true")
            .option("js.ecmascript-version", "13")
            .build()
        resultContext = context
        return context
    }

    override fun run(): Value {
        val result = super.run()
        register()
        return result
    }

}