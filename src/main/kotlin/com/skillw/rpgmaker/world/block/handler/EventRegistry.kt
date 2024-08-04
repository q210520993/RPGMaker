package com.skillw.rpgmaker.world.block.handler

import com.skillw.rpgmaker.core.handlers.event.EventPriority
import com.skillw.rpgmaker.core.handlers.event.SubscribeEvent
import com.skillw.rpgmaker.world.block.handler.RBlockHandler.RBlockHandlerRegistry
import com.skillw.rpgmaker.world.block.handler.placeData.PlaceDataImpl
import net.minestom.server.event.player.PlayerBlockPlaceEvent

object EventRegistry {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun playerPlaceBlock(event: PlayerBlockPlaceEvent) {
        event.block.handler() ?: return
        if(event.block.handler() !is RBlockHandlerRegistry) return
        (event.block.handler() as RBlockHandlerRegistry).onPlace(PlaceDataImpl(event))
    }

    @SubscribeEvent
    fun playerBedBlock(event: PlayerBlockPlaceEvent) {
        event.block = event.block.withHandler(BedHandler)
    }


}