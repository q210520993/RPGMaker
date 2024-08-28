package com.skillw.rpgmaker.module.command

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import com.skillw.rpgmaker.module.mobsystem.ZombieEntity
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.entity.Player

@AutoRegistry
object SummonTest : CommandRegistry, Command("test") {

    override fun getKey(): Command {
        return this
    }

    init {


        addSyntax({ commandSender: CommandSender, _: CommandContext ->
            val player = commandSender as Player
            ZombieEntity().setInstance(player.instance, player.position)
        })

    }
}