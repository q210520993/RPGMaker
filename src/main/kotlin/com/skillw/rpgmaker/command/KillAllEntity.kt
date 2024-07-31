package com.skillw.rpgmaker.command

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.entity.LivingEntity

@AutoRegistry
object KillAllEntity: CommandRegistry, Command("KillAllEntity") {
    override val key: Command = this
    init {


        addSyntax({ commandSender: CommandSender, commandContext: CommandContext ->
            SummonCommand.entitys.forEach {
                (it as? LivingEntity)?.kill() ?: return@forEach
            }
        })
    }
}