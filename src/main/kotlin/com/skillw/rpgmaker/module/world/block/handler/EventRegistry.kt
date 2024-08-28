package com.skillw.rpgmaker.module.world.block.handler

import com.skillw.rpgmaker.core.handlers.event.EventPriority
import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import com.skillw.rpgmaker.module.world.block.BlockGroup
import com.skillw.rpgmaker.module.world.block.handler.placeData.PlaceDataImpl
import net.minestom.server.event.player.PlayerBlockPlaceEvent

object EventRegistry {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun Post(event: PlayerBlockPlaceEvent) {
        event.block.handler() ?: return
        if(event.block.handler() !is BlockHandlerRegistry) return
        (event.block.handler() as BlockHandlerRegistry).onPlace(PlaceDataImpl(event))
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun Pre(event: PlayerBlockPlaceEvent) {
        if (BlockHandlerManager.contains(event.block)) {
            event.block.handler()?.let {
                event.block = event.block.withHandler(BlockHandlerManager[event.block])
            }
        }
    }

}