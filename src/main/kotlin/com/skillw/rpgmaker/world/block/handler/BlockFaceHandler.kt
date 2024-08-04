package com.skillw.rpgmaker.world.block.handler

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.minestom.server.instance.block.BlockHandler
import net.minestom.server.utils.NamespaceID

@AutoRegistry
object BlockFaceHandler: BlockHandlerRegistry {

    override fun getNamespaceId(): NamespaceID {
        return NamespaceID.from("rpgmaker", "blockface")
    }

    override fun onPlace(placement: BlockHandler.Placement) {
        println(namespaceId)
    }

    override fun onInteract(interaction: BlockHandler.Interaction): Boolean {
        println(interaction.block)
        return true
    }

}