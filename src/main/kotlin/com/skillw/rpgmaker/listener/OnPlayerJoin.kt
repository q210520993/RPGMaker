package com.skillw.rpgmaker.listener

import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import com.skillw.rpgmaker.manager.sub.WorldManagerImpl
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityCreature
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.metadata.display.BlockDisplayMeta
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.instance.block.Block

object OnPlayerJoin {
    @SubscribeEvent
    fun onJoin(event: AsyncPlayerConfigurationEvent) {
        event.spawningInstance = WorldManagerImpl["FirstWorld"]?.instance
        event.player.gameMode = GameMode.CREATIVE
    }

    @SubscribeEvent
    fun afterJoin(event: PlayerSpawnEvent) {
        val pos = Pos(777.0, 101.0, -26.0)
        event.player.teleport(pos)
        val entity = Entity(EntityType.BLOCK_DISPLAY)
        (entity.entityMeta as BlockDisplayMeta).setBlockState(Block.BAMBOO_SIGN)
        entity.setInstance(event.player.instance, pos)
    }

}