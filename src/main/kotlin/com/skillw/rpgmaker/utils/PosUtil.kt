package com.skillw.rpgmaker.utils

import net.minestom.server.coordinate.Pos

object PosUtil {
    fun fromString(s: String): Pos {
        val after = s.split(",")
        if (after.size == 3) {
            return Pos(after[0].toDouble(), after[1].toDouble(), after[2].toDouble())
        }
        if (after.size == 5) {
            return Pos(after[0].toDouble(), after[1].toDouble(), after[2].toDouble(), after[3].toFloat(), after[4].toFloat())
        }
        return Pos(0.0, 0.0, 0.0)
    }
}