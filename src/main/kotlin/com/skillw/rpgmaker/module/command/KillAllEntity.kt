package com.skillw.rpgmaker.module.command

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.entity.LivingEntity

@AutoRegistry
object KillAllEntity: CommandRegistry, Command("KillAllEntity") {

    override fun getKey(): Command {
        return this
    }

    init {


        addSyntax({ commandSender: CommandSender, commandContext: CommandContext ->
            commandSender.sendMessage("11111111111111666666666")
        })
    }
}