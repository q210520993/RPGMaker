package com.skillw.rpgmaker.module.world.block.handler

import com.skillw.rpgmaker.RPGMakerInstance.reg
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.core.manager.Manager
import com.skillw.rpgmaker.core.map.BaseMap
import net.minestom.server.instance.block.Block
import net.minestom.server.instance.block.BlockHandler

@AutoRegistry
object BlockHandlerManager: BaseMap<Block, BlockHandler>(), Manager {
    private fun readResolve(): Any = BlockHandlerManager

    override fun getKey(): String {
        return "BlockHandlerManager"
    }

    fun setBlock(registry: BlockHandlerRegistry) {
        this[registry.getBlock()!!] = registry
    }

    fun setBlock(block: Block, handler: BlockHandler) {
        this[block] = handler
    }

    override fun register() {
        reg()
    }

}