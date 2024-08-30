package com.skillw.rpgmaker.module.scripts

import com.skillw.rpgmaker.core.map.component.Registrable
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source
import org.graalvm.polyglot.Value

interface Script: Registrable<String> {

    fun getContext(): Context
    fun getSource(): Source
    fun getScriptType(): String

    fun run(): Value {
        return getContext().eval(getSource())
    }

}