package com.skillw.rpgmaker.module.world

import net.minestom.server.coordinate.Pos

data class WorldInfo(
    val name: String,
    val unSave: Boolean = false,
    val spawnPosition: Pos
)