package com.skillw.rpgmaker.utils

import com.skillw.rpgmaker.launcher.LoggerColor
import org.slf4j.Logger

object LoggerUtil {
    //欸
    fun Logger.RError(message: String) {
        this.error("${LoggerColor.RED.code} $message ${LoggerColor.RESET.code}")
    }
}