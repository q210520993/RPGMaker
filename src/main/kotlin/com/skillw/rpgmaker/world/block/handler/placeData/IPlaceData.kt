package com.skillw.rpgmaker.world.block.handler.placeData

import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block

interface IPlaceData {

    fun getPosition(): Point
    fun getBlock(): Block
    fun getInstance(): Instance
    fun getPlayer(): Player

}