package com.skillw.rpgmaker.module.command

import com.skillw.rpgmaker.core.handlers.annotations.AutoRegistry
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.utils.location.RelativeVec

@AutoRegistry
object TeleportPlayer: CommandRegistry, Command("tp") {
    override fun getKey(): Command {
        return this
    }

    init {
        val player = ArgumentType.Word("player")
        val position = ArgumentType.RelativeVec3("pos")

        addSyntax(this::onPlayerTeleport, player)
        addSyntax(this::onPlayerTeleport, position)

    }

    private fun onPlayerTeleport(sender: CommandSender, context: CommandContext) {
        val playerName = context.get<String>("player")
        val pl = MinecraftServer.getConnectionManager().getOnlinePlayerByUsername(playerName) ?: return
        if (sender is Player) {
            sender.teleport(pl.position)
        }
        sender.sendMessage(Component.text("传送到了$playerName"))
    }

    private fun onPositionTeleport(sender: CommandSender, context: CommandContext) {
        val player = sender as Player

        val relativeVec = context.get<RelativeVec>("pos")
        val position = player.position.withCoord(relativeVec.from(player))
        player.teleport(position)
        player.sendMessage(Component.text("传送到了$position"))
    }

}