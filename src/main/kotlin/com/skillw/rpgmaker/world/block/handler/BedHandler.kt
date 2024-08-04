package com.skillw.rpgmaker.world.block.handler

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.world.block.handler.RBlockHandler.RBlockHandlerRegistry
import com.skillw.rpgmaker.world.block.handler.placeData.IPlaceData
import net.minestom.server.coordinate.Point
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.Direction
import net.minestom.server.utils.MathUtils
import net.minestom.server.utils.NamespaceID
import java.util.*

@AutoRegistry
object BedHandler: RBlockHandlerRegistry {
    override fun getNamespaceId(): NamespaceID {
        return NamespaceID.from("rpgmaker", "bed")
    }

    override fun onPlace(placement: IPlaceData) {
        val instance: Instance = placement.getInstance()
        val pos: Point = placement.getPosition()
        val player: Player = placement.getPlayer()

        val itemStack = player.itemInMainHand // TODO: Hand determination

        val bedBlock = itemStack.material().block()

        val playerDirection = MathUtils.getHorizontalDirection(player.position.yaw())

        val bedHeadPosition = pos.add(
            playerDirection.normalX().toDouble(),
            playerDirection.normalY().toDouble(),
            playerDirection.normalZ().toDouble()
        )
        val blockAtPotentialBedHead = instance.getBlock(bedHeadPosition)

        if (isReplaceable(blockAtPotentialBedHead)) {
            placeBed(instance, bedBlock, pos, bedHeadPosition, playerDirection)
        }
    }

    private fun isReplaceable(blockAtPosition: Block): Boolean {
        return blockAtPosition.isAir || blockAtPosition.isLiquid
    }

    private fun placeBed(
        instance: Instance,
        bedBlock: Block,
        footPosition: Point,
        headPosition: Point,
        facing: Direction) {
        val correctFacing = bedBlock.withProperty("facing", facing.name.lowercase(Locale.getDefault()))

        val footBlock = correctFacing.withProperty("part", "foot")
        val headBlock = correctFacing.withProperty("part", "head")
        instance.setBlock(footPosition, footBlock)
        instance.setBlock(headPosition, headBlock)
    }
}