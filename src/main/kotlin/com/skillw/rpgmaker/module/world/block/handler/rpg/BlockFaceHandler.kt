package com.skillw.rpgmaker.module.world.block.handler.rpg

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.module.world.block.handler.BlockHandlerRegistry
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