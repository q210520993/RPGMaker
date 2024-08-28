package com.skillw.rpgmaker.module.player

import com.skillw.rpgmaker.core.handlers.event.EventPriority
import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import com.skillw.rpgmaker.module.world.manager.WorldManagerImpl
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.GameMode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerSpawnEvent

@SubscribeEvent
fun onJoin(event: AsyncPlayerConfigurationEvent) {
    event.spawningInstance = WorldManagerImpl["FirstWorld"]?.instance
    event.player.gameMode = GameMode.SURVIVAL
}

@SubscribeEvent
fun afterJoin(event: PlayerSpawnEvent) {
    val pos = Pos(777.0, 101.0, -26.0)
    event.player.teleport(pos)
}