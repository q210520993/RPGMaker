package com.skillw.rpgmaker.module.player

import com.skillw.rpgmaker.RPGMakerInstance
import com.skillw.rpgmaker.core.handlers.event.EventPriority
import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import com.skillw.rpgmaker.module.world.manager.WorldManagerImpl
import net.minestom.server.entity.GameMode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import java.util.*

@SubscribeEvent(priority = EventPriority.SERVER)
fun onJoin(event: AsyncPlayerConfigurationEvent) {

    val option = RPGMakerInstance.option
    val defaultWorld = option.getString("PlayerDefaultWolrd")
    event.spawningInstance = WorldManagerImpl[defaultWorld]?.instance
    event.player.gameMode = option.getString("PlayerDefaultGameMode")?.let {
        GameMode.valueOf(it.uppercase(Locale.getDefault()))
    } ?: GameMode.SURVIVAL

}

@SubscribeEvent(priority = EventPriority.SERVER)
fun afterJoin(event: PlayerSpawnEvent) {
    val pos = WorldManagerImpl.worldUUIDManager[event.instance.uniqueId]?.worldInfo?.spawnPosition!!
    event.player.teleport(pos)
}
