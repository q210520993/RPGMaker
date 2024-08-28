package com.skillw.rpgmaker.module.command

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.kyori.adventure.audience.MessageType
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandExecutor
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.condition.CommandCondition
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player


@AutoRegistry
object GameModeCommand: CommandRegistry, Command("gamemode", "gm") {

    override fun getKey(): Command {
        return this
    }

    init {

        condition = CommandCondition { commandSender, s ->
            commandSender is Player
        }

        //GameMode parameter
        val gamemode = ArgumentType.Enum("gamemode", GameMode::class.java).setFormat(ArgumentEnum.Format.LOWER_CASED)

        defaultExecutor = CommandExecutor { sender: CommandSender, context: CommandContext ->
            val commandName = context.commandName
            sender.sendMessage(Component.text("提示: /${commandName} <gamemode>", NamedTextColor.RED))
        }

        gamemode.setCallback { commandSender, error ->
            commandSender.sendMessage(Component.text("Invalid gamemode ${error.input}"))
        }

        // Add arguments to the command
        addSyntax({ sender, context ->
            val gameMode: GameMode = context.get(gamemode)
            (sender as Player).setGameMode(gameMode)
            sender.sendMessage(Component.text("成功转化成${gameMode}", NamedTextColor.AQUA))
        }, gamemode)
    }

}