package com.skillw.rpgmaker.module.world.block.handler.vanilla

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.module.world.block.handler.BlockHandlerRegistry
import net.minestom.server.instance.block.BlockHandler
import net.minestom.server.utils.NamespaceID

@AutoRegistry
object VanillaBedHandler: BlockHandlerRegistry {


    override fun getNamespaceId(): NamespaceID {
        return NamespaceID.from("minecraft:bed")
    }

    override fun onPlace(placement: BlockHandler.Placement) {}

}