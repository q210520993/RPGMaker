package com.skillw.rpgmaker.world.block.handler

import com.skillw.rpgmaker.core.map.component.Registrable
import net.minestom.server.MinecraftServer
import net.minestom.server.instance.block.BlockHandler
import net.minestom.server.utils.NamespaceID

interface BlockHandlerRegistry: Registrable<NamespaceID>, BlockHandler {

    override fun getKey(): NamespaceID {
        return namespaceId
    }

    override fun register() {
        MinecraftServer.getBlockManager().registerHandler(namespaceId) { this }
    }

}