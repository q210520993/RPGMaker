package com.skillw.rpgmaker.command

import com.skillw.rpgmaker.core.map.component.Registrable
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command

interface CommandRegistry: Registrable<Command> {

    override fun register() {
        MinecraftServer.getCommandManager().register(key)
    }
}