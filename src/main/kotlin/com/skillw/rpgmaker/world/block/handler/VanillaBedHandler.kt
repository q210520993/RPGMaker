package com.skillw.rpgmaker.world.block.handler

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.minestom.server.instance.block.BlockHandler
import net.minestom.server.utils.NamespaceID

@AutoRegistry
object VanillaBedHandler: BlockHandlerRegistry {


    override fun getNamespaceId(): NamespaceID {
        return NamespaceID.from("minecraft:bed")
    }

    override fun onPlace(placement: BlockHandler.Placement) {
        println(placement.blockPosition)

    }

}