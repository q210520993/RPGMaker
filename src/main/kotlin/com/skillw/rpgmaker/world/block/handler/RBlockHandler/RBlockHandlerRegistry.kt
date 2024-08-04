package com.skillw.rpgmaker.world.block.handler.RBlockHandler

import com.skillw.rpgmaker.world.block.handler.BlockHandlerRegistry
import com.skillw.rpgmaker.world.block.handler.placeData.IPlaceData
import net.minestom.server.instance.block.BlockHandler

interface RBlockHandlerRegistry: BlockHandlerRegistry {

    override fun onPlace(placement: BlockHandler.Placement) {}

    fun onPlace(placement: IPlaceData)

}