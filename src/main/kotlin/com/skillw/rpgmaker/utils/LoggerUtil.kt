package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.module.system.terminal.LoggerColor
import org.slf4j.Logger
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.nodes.Node
import org.yaml.snakeyaml.representer.BaseRepresenter
import org.yaml.snakeyaml.representer.Represent
import org.yaml.snakeyaml.representer.Representer

object LoggerUtil {
    //欸
    fun Logger.RError(message: String) {
        this.error("${LoggerColor.RED.code} $message ${LoggerColor.RESET.code}")
    }
}