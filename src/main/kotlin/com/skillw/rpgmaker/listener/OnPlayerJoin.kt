package com.skillw.rpgmaker.listener

import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import com.skillw.rpgmaker.manager.sub.WorldManagerImpl
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerSpawnEvent

object OnPlayerJoin {
    @SubscribeEvent
    fun onJoin(event: AsyncPlayerConfigurationEvent) {
        event.spawningInstance = WorldManagerImpl.Worlds["FirstWorld"]?.instance
        event.player.gameMode = GameMode.CREATIVE
    }

    @SubscribeEvent
    fun afterJoin(event: PlayerSpawnEvent) {
        event.player.teleport(Pos(777.0, 101.0, -26.0))
    }

}