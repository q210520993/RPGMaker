package com.skillw.rpgmaker.module.world.block.handler

import com.skillw.rpgmaker.core.map.component.Registrable
import com.skillw.rpgmaker.module.world.block.handler.placeData.IPlaceData
import net.minestom.server.MinecraftServer
import net.minestom.server.instance.block.Block
import net.minestom.server.instance.block.BlockHandler
import net.minestom.server.utils.NamespaceID

interface BlockHandlerRegistry: Registrable<NamespaceID>, BlockHandler {

    override fun getKey(): NamespaceID {
        return namespaceId
    }

    fun getBlock(): Block? {
        return null
    }

    override fun register() {
        MinecraftServer.getBlockManager().registerHandler(namespaceId) { this }

    }

    override fun onPlace(placement: BlockHandler.Placement) {}

    fun onPlace(placement: IPlaceData) {}

}