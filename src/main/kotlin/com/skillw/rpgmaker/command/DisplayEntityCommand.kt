package com.skillw.rpgmaker.command

import com.skillw.rpgmaker.command.SummonCommand.pos
import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.entity.metadata.display.BlockDisplayMeta
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.location.RelativeVec
import java.util.function.Supplier

@AutoRegistry
object DisplayEntityCommand: CommandRegistry, Command("DisplayBlock") {

    override fun getKey(): Command {
        return this
    }

    init {

        pos = ArgumentType.RelativeVec3("pos").setDefaultValue(Supplier {
            RelativeVec(
                Vec(0.0, 0.0, 0.0),
                RelativeVec.CoordinateType.RELATIVE,
                true, true, true
            )
        })

        addSyntax({ commandSender: CommandSender, commandContext: CommandContext ->
            val entity = Entity(EntityType.BLOCK_DISPLAY)
            val meta = entity.entityMeta as BlockDisplayMeta
            meta.setBlockState(Block.SPRUCE_PLANKS)
            meta.leftRotation = floatArrayOf(0f, 3f, 0f, 1f)
            entity.setInstance(
                (commandSender as Player).instance,
                commandContext.get(pos).fromSender(commandSender)
            )
        }, pos)
    }
}