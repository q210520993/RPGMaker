package com.skillw.rpgmaker.module.world.block

import net.minestom.server.instance.block.Block

enum class BlockGroup(val blocks: Set<Block>) {

    BED(
        setOf(Block.GREEN_BED,
        Block.BLUE_BED,
        Block.RED_BED,
        Block.YELLOW_BED,
        Block.BLACK_BED,
        Block.GRAY_BED,
        Block.BROWN_BED,
        Block.ORANGE_BED,
        Block.PURPLE_BED,
        Block.PINK_BED,
        Block.WHITE_BED,
        Block.CYAN_BED,
        Block.LIME_BED,
        Block.MAGENTA_BED,
        Block.LIGHT_BLUE_BED,
        Block.LIGHT_GRAY_BED)
    )

}