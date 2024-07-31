package com.skillw.rpgmaker.core.handlers.hook

import com.skillw.rpgmaker.RPGMaker

interface ICoreHook {
    fun hook(rpgMaker: RPGMaker)
}