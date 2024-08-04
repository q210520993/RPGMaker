package com.skillw.rpgmaker.world.block.handler.placeData

import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerBlockPlaceEvent
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block

class PlaceDataImpl(val event: PlayerBlockPlaceEvent): IPlaceData {

    override fun getBlock(): Block {
        return event.block
    }

    override fun getInstance(): Instance {
        return event.instance
    }

    override fun getPlayer(): Player {
        return event.player
    }

    override fun getPosition(): Point {
        return event.blockPosition
    }

}
